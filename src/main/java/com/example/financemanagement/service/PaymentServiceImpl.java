package com.example.financemanagement.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import com.example.financemanagement.dto.PaymentResponse;
import com.example.financemanagement.mapper.PaymentMapper;
import com.example.financemanagement.model.Loan;
import com.example.financemanagement.model.LoanEmi;
import com.example.financemanagement.model.PaymentsTracking;
import com.example.financemanagement.repository.LoanEmiRepository;
import com.example.financemanagement.repository.LoanRepository;
import com.example.financemanagement.repository.PaymentsTrackingRepository;

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
	
	 
	 
	 @Autowired
	 private PaymentsTrackingRepository paymentsTrackingRepository;
	
	private boolean update = false;
	@Override
	public PaymentResponse  getAllPayments(String searchQuery, Pageable pageable) {
		// Step 1: Fetch loans with pagination and filtering
		Page<Loan> loans = loanRepository.findBySearchQuery(searchQuery, pageable);
		
		// Initialize totals for pending EMI count and amount
	    long totalLoans = loans.getTotalElements();  // This gives the total loan count
	    long pendingEmiCount = loanEmiRepository.fetchTotalPendingEmiCount();
	    double pendingEmiAmount = loanEmiRepository.fetchTotaPendingEmiAmount()!=null?loanEmiRepository.fetchTotaPendingEmiAmount():0.0 ;
	    long pendingCustomerCount = loanEmiRepository.countPendingCustomers(); 
		
	    Page<PaymentDTO> paymentDTOPage= loans.map(loan -> {
			Set<String> phoneNumbers = new HashSet<>(Arrays.asList());
			collectPhoneNumbers(loan, phoneNumbers);
			updateTotalamounts(loan,update);
			PaymentDTO paymentDTO = paymentMapper.loanToPaymentDTO(loan);
			paymentDTO.setPhoneNumbers(phoneNumbers);
			return paymentDTO;
		});
	    
	    return new PaymentResponse(paymentDTOPage, totalLoans, pendingEmiCount, pendingEmiAmount,pendingCustomerCount);
	}

	@Override
	public PaymentDTO getPaymentDetails(Long loanId) {
		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));
		return paymentMapper.loanToPaymentDTO(loan);
	}

	@Override
	public PaymentDTO getLoanDetailsByFileNumber(Long fileNumber) {
		Loan loan = loanRepository.findByFileNumber(fileNumber)
				.orElseThrow(() -> new RuntimeException("Loan not found for file number: " + fileNumber));

		PaymentDTO paymentDTO = paymentMapper.loanToPaymentDTO(loan);

		if (loan.getEmis().isEmpty()) {
			List<LoanEmi> emiList = loanService.generateEmiSchedule(loan);
			loanEmiRepository.saveAll(emiList);

		}

		List<LoanEmi> sortedEmis = loan.getEmis().stream().sorted(Comparator.comparing(LoanEmi::getEmiNumber)) 
				.collect(Collectors.toList());
		
		sortedEmis.forEach(emi -> {
	        Double overdueAmount = calculateEmiOD(emi,null);
	        // If remainingAmount is null, consider emiAmount as the overdue amount
	        if (emi.getRemainingAmount() == null) {
	            emi.setRemainingAmount(emi.getEmiAmount() + overdueAmount); // Set remaining amount to EMI amount + overdue amount
	        } else if(emi.getPaidAmount()<emi.getEmiAmount()) {
	            emi.setRemainingAmount(emi.getEmiAmount()-emi.getPaidAmount() + overdueAmount); // Add overdue amount to existing remainingAmount
	        }
	    });

		paymentDTO.setEmiDetails(sortedEmis);
		return paymentDTO;
	}

	@Override
	public LoanEmi payEmi(Long loanId, Integer emiNumber, Double paymentAmount,LocalDate paidDate) {
		// Find the LoanEMI record for the given loanId and emiNumber

		Optional<Loan> loan = loanRepository.findByFileNumber(loanId);
		Long id = loan.get().getId();
		
		LoanEmi loanEmi = loanEmiRepository.findByLoanIdAndEmiNumber(id, emiNumber);

		if (loanEmi == null) {
			throw new RuntimeException("EMI not found");
		}

		// Update the payment details
		loanEmi.updatePayment(paymentAmount,calculateEmiOD(loanEmi,paidDate),paidDate);
		if (loanEmi.getPaidAmount() >= loanEmi.getEmiAmount() - 30) {
			if (null == loan.get().getPaidEmiCount()) {
				loan.get().setPaidEmiCount(0); // Initialize to zero if not set
			}
			if(null== loanEmi.getPaymentDate() || loanEmi.getPaymentDate().equals(paidDate!=null? paidDate: LocalDate.now())) {
			loan.get().setPaidEmiCount(loan.get().getPaidEmiCount() + 1);
			}
			if (null == loan.get().getRemainingEmiCount()) {
				loan.get().setRemainingEmiCount(loan.get().getTenure() - 1);
			} else if (null==loanEmi.getPaymentDate() ||  loanEmi.getPaymentDate().equals(paidDate)){
				loan.get().setRemainingEmiCount(loan.get().getRemainingEmiCount() - 1);
			}
		}
		
		updateTotalamounts(loan.get(),true);
		calculateDaysSinceLastUnpaidEmi(id,true);
		// Save the updated LoanEMI record
		
		generatePaymentTrackingBill(loanEmi, paymentAmount, "EMI Paid", paidDate != null ? paidDate : LocalDate.now());
		
//		 // Fetch last EMI bill number from only EMI transactions
//	    String lastEmiBillNumber = paymentsTrackingRepository
//	            .findLastEmiBillNumber()
//	            .orElse("E-000000");  // Default if no previous records
//	    
//	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMM"); // Format: YYYYMMDD
//        String formattedDate = paidDate.format(formatter);
//
//	    // Extract last sequence number and increment it
//	    
//	    
//	    String[] parts = lastEmiBillNumber.split("-"); // Splitting by '-'
//	    String lastSequenceStr = parts[parts.length - 1]; // Get last part
//	    int lastEmiSequence = Integer.parseInt(lastSequenceStr);
//
//	    
//	    
//	    String newEmiBillNumber = "E-" + formattedDate+"-" + String.format("%06d", lastEmiSequence + 1);
//
//	    PaymentsTracking paymentsTracking = new PaymentsTracking();
//	    paymentsTracking.setTransactionType("EMI Paid");
//	    paymentsTracking.setBillNumber(newEmiBillNumber);
//	    paymentsTracking.setTransactionDate(null!=loanEmi.getPaymentDate()? loanEmi.getPaymentDate():LocalDate.now());
//	    paymentsTracking.setAmount(paymentAmount);
//	    paymentsTracking.setLoan(loanEmi.getLoan());
//	    paymentsTracking.setLoanEmi(loanEmi);
//	    paymentsTracking.setCustomer(loanEmi.getLoan().getCustomer());
//
//	    paymentsTrackingRepository.save(paymentsTracking);
//		
	return loanEmiRepository.save(loanEmi);
	}

	private Double calculateLoanOD(Loan loan) {
		double totalOD = 0.0;

		for (LoanEmi emi : loan.getEmis()) {
			totalOD += calculateEmiOD(emi,null);

		}
		return totalOD;
	}

	private Double calculateEmiOD(LoanEmi emi, LocalDate paidDate) {
	    if (emi == null || emi.getStatus() == null || emi.getEmiDate() == null) {
	        return 0.0;
	    }

	    long overdueDays = 0;
	    
	    LocalDate date = paidDate!=null? paidDate:LocalDate.now();

	    // Check if the EMI is "Pending" and the EMI due date has passed
	    if (emi.getEmiDate().isBefore(date)) {
	        if (!emi.getStatus().equals("Pending") && emi.getPaidAmount() >= emi.getEmiAmount() && emi.getPaymentDate() != null) {
	            // If the EMI is paid, calculate overdue days using the payment date
	            overdueDays = ChronoUnit.DAYS.between(emi.getEmiDate(), emi.getPaymentDate());
	        } else if (emi.getStatus().equals("Pending")) {
	            // If the EMI is pending, calculate overdue days using the current date
	            overdueDays = ChronoUnit.DAYS.between(emi.getEmiDate(), date);
	        }

	        // Return overdue amount based on the overdue days
	        if (overdueDays > 0) {
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
	public long calculateDaysSinceLastUnpaidEmi(Long loanId, Boolean update) {
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
			long pendingDays = calculateDaysSinceLastUnpaidEmi(loan.getId(),true);

			// Update the loan entity with new values and set the lastUpdated timestamp
			loan.setOverdueAmount(loanOD);
			loan.setTotalPendingEmiAmount(totalPendingEmiAmount);
			loan.setPendingDays(pendingDays);
			loan.setLastUpdated(LocalDate.now());

			// Save the updated loan entity back to the repository
			loanRepository.save(loan);
		}

	}
	
	
	
	@Override
	public LoanEmi updateEmi(Long loanId, Integer emiNumber, String emiDate, LocalDate paymentDate, Double paidAmount, Double remainingAmount) {
	    // Find the LoanEMI record for the given loanId and emiNumber
	    Optional<Loan> loan = loanRepository.findByFileNumber(loanId);
	    if (!loan.isPresent()) {
	        throw new RuntimeException("Loan not found");
	    }
	    Long id = loan.get().getId();

	    LoanEmi loanEmi = loanEmiRepository.findByLoanIdAndEmiNumber(id, emiNumber);
	    if (loanEmi == null) {
	        throw new RuntimeException("EMI not found");
	    }

	    // Update the EMI details with provided values
	    if (emiDate != null) {
	        loanEmi.setEmiDate(LocalDate.parse(emiDate));
	    }
	    if (paymentDate != null) {
	        loanEmi.setPaymentDate(paymentDate);
	    }
	    if (paidAmount != null) {
	        loanEmi.setPaidAmount(paidAmount);
	    }
	    if (remainingAmount != null) {
	        loanEmi.setRemainingAmount(remainingAmount);
	    } else {
	        // Recalculate remainingAmount if not provided
	        double overdueAmount = calculateEmiOD(loanEmi, paymentDate != null ? paymentDate : loanEmi.getPaymentDate());
	        loanEmi.setRemainingAmount(loanEmi.getEmiAmount() + overdueAmount - (paidAmount != null ? paidAmount : loanEmi.getPaidAmount()));
	    }

	    // Update status based on paidAmount and emiAmount
	    if (loanEmi.getPaidAmount() >= loanEmi.getEmiAmount() - 30) {
	        loanEmi.setStatus("Paid");
	        if (null == loan.get().getPaidEmiCount()) {
	            loan.get().setPaidEmiCount(0);
	        }
	        if (loanEmi.getPaymentDate() == null || !loanEmi.getPaymentDate().equals(paymentDate)) {
	            loan.get().setPaidEmiCount(loan.get().getPaidEmiCount() + 1);
	        }
	        if (null == loan.get().getRemainingEmiCount()) {
	            loan.get().setRemainingEmiCount(loan.get().getTenure() - 1);
	        } else if (loanEmi.getPaymentDate() == null || !loanEmi.getPaymentDate().equals(paymentDate)) {
	            loan.get().setRemainingEmiCount(loan.get().getRemainingEmiCount() - 1);
	        }
	    } else if (loanEmi.getRemainingAmount() > 0) {
	        loanEmi.setStatus("Pending");
	        if (loanEmi.getPaymentDate() != null && loan.get().getPaidEmiCount() > 0) {
	            loan.get().setPaidEmiCount(loan.get().getPaidEmiCount() - 1);
	            loan.get().setRemainingEmiCount(loan.get().getRemainingEmiCount() + 1);
	        }
	    } else {
	        loanEmi.setStatus("Overpaid");
	    }
	    
	 // Update PaymentsTracking amount (no new bill generation)
	    PaymentsTracking existingPayment = paymentsTrackingRepository.findTopByLoanEmiOrderByBillNumberDesc(loanEmi);
	    if (existingPayment != null && paidAmount != null) {
	        existingPayment.setAmount(paidAmount);
	        paymentsTrackingRepository.save(existingPayment);
	    } else if (existingPayment == null) {
	    	generatePaymentTrackingBill(loanEmi, paidAmount, "EMI Updated", paymentDate != null ? paymentDate : LocalDate.now());
	    }

	    // Update loan totals without generating a new bill
	    updateTotalamounts(loan.get(), false);
	    calculateDaysSinceLastUnpaidEmi(id, false);
	    

	    // Save the updated LoanEMI record without bill generation
	    return loanEmiRepository.save(loanEmi);
	}
	
	private PaymentsTracking generatePaymentTrackingBill(LoanEmi loanEmi, Double amount, String transactionType, LocalDate transactionDate) {
	    // Fetch last EMI bill number from only EMI transactions
	    String lastEmiBillNumber = paymentsTrackingRepository.findLastEmiBillNumber().orElse("E-000000");
	    
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMM");
	    String formattedDate = transactionDate.format(formatter);

	    // Extract last sequence number and increment it
	    String[] parts = lastEmiBillNumber.split("-");
	    String lastSequenceStr = parts[parts.length - 1];
	    int lastEmiSequence = Integer.parseInt(lastSequenceStr);

	    String newEmiBillNumber = "E-" + formattedDate + "-" + String.format("%06d", lastEmiSequence + 1);

	    // Create PaymentsTracking record
	    PaymentsTracking paymentsTracking = new PaymentsTracking();
	    paymentsTracking.setTransactionType(transactionType);
	    paymentsTracking.setBillNumber(newEmiBillNumber);
	    paymentsTracking.setTransactionDate(transactionDate);
	    paymentsTracking.setAmount(amount);
	    paymentsTracking.setLoan(loanEmi.getLoan());
	    paymentsTracking.setLoanEmi(loanEmi);
	    paymentsTracking.setCustomer(loanEmi.getLoan().getCustomer());

	    return paymentsTrackingRepository.save(paymentsTracking);
	}
	
	
	
}
