package com.example.financemanagement.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.financemanagement.dto.PaymentDTO;
import com.example.financemanagement.mapper.PaymentMapper;
import com.example.financemanagement.model.Loan;
import com.example.financemanagement.model.LoanEmi;
import com.example.financemanagement.repository.LoanEmiRepository;
import com.example.financemanagement.repository.LoanRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private PaymentMapper paymentMapper;

	@Autowired
	private LoanEmiRepository loanEmiRepository;

	@Autowired
	private LoanService loanService;
	
	private boolean update = false;
	@Override
	public Page<PaymentDTO> getAllPayments(String searchQuery, Pageable pageable) {
		// Step 1: Fetch loans with pagination and filtering
		Page<Loan> loans = loanRepository.findBySearchQuery(searchQuery, pageable);

		
		return loans.map(loan -> {
			// Check if the loan has already been updated today
			Set<String> phoneNumbers = new HashSet<>(Arrays.asList());
			collectPhoneNumbers(loan, phoneNumbers);
			updateTotalamounts(loan,update);
			PaymentDTO paymentDTO = paymentMapper.loanToPaymentDTO(loan);
			paymentDTO.setPhoneNumbers(phoneNumbers);
			return paymentDTO;
		});
	}

	@Override
	public PaymentDTO getPaymentDetails(Long loanId) {
		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));
		return paymentMapper.loanToPaymentDTO(loan);
	}

	@Override
	public PaymentDTO getLoanDetailsByFileNumber(String fileNumber) {
		Loan loan = loanRepository.findByFileNumber(fileNumber)
				.orElseThrow(() -> new RuntimeException("Loan not found for file number: " + fileNumber));

		PaymentDTO paymentDTO = paymentMapper.loanToPaymentDTO(loan);

		if (loan.getEmis().isEmpty()) {
			List<LoanEmi> emiList = loanService.generateEmiSchedule(loan);
			loanEmiRepository.saveAll(emiList);

		}

		List<LoanEmi> sortedEmis = loan.getEmis().stream().sorted(Comparator.comparing(LoanEmi::getEmiNumber)) // Sort
																												// by
																												// emiNumber
																												// in
																												// ascending
																												// order
				.collect(Collectors.toList());

		paymentDTO.setEmiDetails(sortedEmis);
		return paymentDTO;
	}

	@Override
	public LoanEmi payEmi(Long loanId, Integer emiNumber, Double paymentAmount) {
		// Find the LoanEMI record for the given loanId and emiNumber

		Optional<Loan> loan = loanRepository.findByFileNumber(loanId.toString());
		Long id = loan.get().getId();
		updateTotalamounts(loan.get(),true);
		LoanEmi loanEmi = loanEmiRepository.findByLoanIdAndEmiNumber(id, emiNumber);

		if (loanEmi == null) {
			throw new RuntimeException("EMI not found");
		}

		// Update the payment details
		loanEmi.updatePayment(paymentAmount);
		if (loanEmi.getPaidAmount() >= loanEmi.getEmiAmount() - 30) {
			if (null == loan.get().getPaidEmiCount()) {
				loan.get().setPaidEmiCount(0); // Initialize to zero if not set
			}
			loan.get().setPaidEmiCount(loan.get().getPaidEmiCount() + 1);

			if (null == loan.get().getRemainingEmiCount()) {
				loan.get().setRemainingEmiCount(loan.get().getTenure() - 1);
			} else {
				loan.get().setRemainingEmiCount(loan.get().getRemainingEmiCount() - 1);
			}
		}

		// Save the updated LoanEMI record
		return loanEmiRepository.save(loanEmi);
	}

	private Double calculateLoanOD(Loan loan) {
		double totalOD = 0.0;

		for (LoanEmi emi : loan.getEmis()) {
			totalOD += calculateEmiOD(emi);

		}
		return totalOD;
	}

	private Double calculateEmiOD(LoanEmi emi) {
		if (emi != null && emi.getStatus() != null && emi.getEmiDate() != null) {
			if (emi.getStatus().equals("Pending") && emi.getEmiDate().isBefore(LocalDate.now())) {
				long overdueDays = ChronoUnit.DAYS.between(emi.getEmiDate(), LocalDate.now());
				return emi.getEmiAmount() * 0.002 * overdueDays; // Assuming 2% late fee per day
			}
		}
		return 0.0;
	}

	private Double calculateTotalPendingEmiAmount(Loan loan) {
		double totalPendingAmount = 0.0;

		// Iterate through each EMI and calculate its pending amount
		for (LoanEmi emi : loan.getEmis()) {
			totalPendingAmount += calculateEmiPendingAmount(emi);
		}
		return totalPendingAmount;
	}

	// Calculate pending amount for each EMI (Emi Amount - Paid Amount), only if the
	// EMI date has passed
	private Double calculateEmiPendingAmount(LoanEmi emi) {
		if (emi != null && emi.getStatus() != null && emi.getEmiDate() != null) {
			if (emi.getStatus().equals("Pending") && emi.getEmiDate().isBefore(LocalDate.now())) {
				Double paidAmount = emi.getPaidAmount() != null ? emi.getPaidAmount() : 0.0;
				return emi.getEmiAmount() - paidAmount;
			}
		}
		return 0.0;
	}

	// Method to calculate the number of days passed since the last unpaid EMI
	public long calculateDaysSinceLastUnpaidEmi(Long loanId) {
		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));

		// Find the first unpaid EMI (Pending status)
		LoanEmi firstUnpaidEmi = loan.getEmis().stream().filter(emi -> "Pending".equals(emi.getStatus())) // Filter by
																											// "Pending"
																											// status
				.findFirst() // Get the first pending EMI
				.orElse(null); // If no pending EMI found, return null

		if (firstUnpaidEmi != null) {
			// Calculate the days since the EMI date (from the EMI's emiDate to today)
			LocalDate today = LocalDate.now();
			return ChronoUnit.DAYS.between(firstUnpaidEmi.getEmiDate(), today);
		}

		return 0; // No unpaid EMIs found
	}

	// Helper method to collect phone numbers from customer and guarantor
	private void collectPhoneNumbers(Loan loan, Set<String> phoneNumbers) {
		if (loan.getCustomer().getPhoneNumberPrimary() != null) {
			phoneNumbers.add(loan.getCustomer().getPhoneNumberPrimary());
		}
		if (loan.getCustomer().getPhoneNumberSecondary() != null) {
			phoneNumbers.add(loan.getCustomer().getPhoneNumberSecondary());
		}
		if (loan.getGuarantor().getPhoneNumberPrimary() != null) {
			phoneNumbers.add(loan.getGuarantor().getPhoneNumberPrimary());
		}
		if (loan.getGuarantor().getPhoneNumberSecondary() != null) {
			phoneNumbers.add(loan.getGuarantor().getPhoneNumberSecondary());
		}
	}

	public void updateTotalamounts(Loan loan , boolean update) {
		if (loan.getLastUpdated() == null || !loan.getLastUpdated().isEqual(LocalDate.now())|| update) {
			// If not updated today, calculate OD (Overdue), Total Pending EMI Amount, and
			// Pending Days

			Double loanOD = (double) Math.round(calculateLoanOD(loan));
			Double totalPendingEmiAmount = Math.ceil(calculateTotalPendingEmiAmount(loan));
			long pendingDays = calculateDaysSinceLastUnpaidEmi(loan.getId());

			// Update the loan entity with new values and set the lastUpdated timestamp
			loan.setOverdueAmount(loanOD);
			loan.setTotalPendingEmiAmount(totalPendingEmiAmount);
			loan.setPendingDays(pendingDays);
			loan.setLastUpdated(LocalDate.now());

			// Save the updated loan entity back to the repository
			loanRepository.save(loan);
		}

	}
}
