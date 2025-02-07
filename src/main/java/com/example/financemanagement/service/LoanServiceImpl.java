package com.example.financemanagement.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.financemanagement.dto.FullLoanDetailsDTO;
import com.example.financemanagement.dto.LoanDTO;
import com.example.financemanagement.dto.LoanRequestDTO;
import com.example.financemanagement.dto.LoanResponseDTO;
import com.example.financemanagement.dto.LoanStatsDTO;
import com.example.financemanagement.mapper.LoanMapper;
import com.example.financemanagement.model.Customer;
import com.example.financemanagement.model.Guarantor;
import com.example.financemanagement.model.Loan;
import com.example.financemanagement.model.LoanEmi;
import com.example.financemanagement.model.PaymentsTracking;
import com.example.financemanagement.model.Vehicle;
import com.example.financemanagement.repository.CustomerRepository;
import com.example.financemanagement.repository.GuarantorRepository;
import com.example.financemanagement.repository.LoanEmiRepository;
import com.example.financemanagement.repository.LoanRepository;
import com.example.financemanagement.repository.PaymentsTrackingRepository;
import com.example.financemanagement.repository.VehicleRepository;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private GuarantorRepository guarantorRepository;
    @Autowired
    private LoanMapper loanMapper;
    
    @Autowired
    private LoanEmiRepository loanEmiRepository;
    
    
    
    @Autowired
    private PaymentsTrackingRepository paymentsTrackingRepository;

    @Override
    public void createLoan(LoanRequestDTO loanRequestDTO) {
    	Customer customer = loanMapper.toCustomerEntity(loanRequestDTO);
        Guarantor guarantor = loanMapper.toGuarantorEntity(loanRequestDTO);
        Vehicle vehicle = loanMapper.toVehicleEntity(loanRequestDTO);
        Loan loan = loanMapper.toLoanEntity(loanRequestDTO);
        
        
        
       
        
          
     // Save Entities in Database
        customer = customerRepository.save(customer);
        guarantor = guarantorRepository.save(guarantor);
        vehicle = vehicleRepository.save(vehicle);
        
        loan.setCustomer(customer);
        loan.setGuarantor(guarantor);
        loan.setVehicle(vehicle);
        
        Loan savedLoan = loanRepository.save(loan);
        
        List<LoanEmi> emiList = generateEmiSchedule(savedLoan);
        loanEmiRepository.saveAll(emiList);
        
     // Generate a sequential Loan Bill Number
        String lastBillNumber = paymentsTrackingRepository.findLastLoanBillNumber()
                .orElse("L-000000");  // Default if no previous records

        //int lastSequence = Integer.parseInt(lastBillNumber.substring(2)); 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMM"); // Format: YYYYMMDD
        String formattedDate = loan.getLoanCreationDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(formatter);

        String newBillNumber = "L-" + formattedDate + "-" +(savedLoan.getFileNumber());


        PaymentsTracking paymentsTracking = new PaymentsTracking();
        paymentsTracking.setTransactionType("Loan Given");
        paymentsTracking.setBillNumber(newBillNumber);
        paymentsTracking.setTransactionDate(LocalDate.now());
        paymentsTracking.setAmount(savedLoan.getLoanAmount());
        paymentsTracking.setLoan(savedLoan);
        paymentsTracking.setCustomer(savedLoan.getCustomer());
       

        // Save PaymentTracking entry
        paymentsTrackingRepository.save(paymentsTracking);

        
        
       
    }
    
    @Override
    public List<LoanEmi> generateEmiSchedule(Loan loan){
        List<LoanEmi> emiList = new ArrayList<>();
        LocalDate startDate = loan.getLoanCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	Integer tenure  = loan.getTenure();
        double emiAmount = loan.getEmi();

        for (int i=0; i<tenure ; i++) {
        	LoanEmi emi = new LoanEmi();
        	emi.setEmiNumber(i+1);
        	emi.setEmiAmount(emiAmount);
        	emi.setLoan(loan);
        	emi.setPaidAmount(0.0);
        	emi.setEmiDate(startDate.plusMonths(i));
        	emi.setRemainingAmount(emiAmount);
        	emiList.add(emi);	
        	
        }
        return emiList;	
    }
     
    @Override
    public void  updateLoan(Long id,LoanRequestDTO loanRequestDTO) {
    	Loan existingLoan = loanRepository.findByFileNumber(id).get();
    	
    	// Update Loan fields
    	existingLoan.setFileNumber(loanRequestDTO.getFileNumber());
    	existingLoan.setLoanAmount(loanRequestDTO.getLoanAmount());
    	existingLoan.setInterestRate(loanRequestDTO.getInterestRate());
    	existingLoan.setEmi(loanRequestDTO.getEmi());
    	existingLoan.setTenure(loanRequestDTO.getTenure());
    	existingLoan.setLoanCreationDate(loanRequestDTO.getLoanCreationDate());

    	Customer existingCustomer = existingLoan.getCustomer();
        existingCustomer.setName(loanRequestDTO.getCustomerName());
        existingCustomer.setPhoneNumberPrimary(loanRequestDTO.getCustomerPhonePrimary());
        existingCustomer.setPhoneNumberSecondary(loanRequestDTO.getCustomerPhoneSecondary());
        existingCustomer.setAddress(loanRequestDTO.getCustomerAadhaarNumber());
        existingCustomer.setAadhaarNumber(loanRequestDTO.getCustomerAadhaarNumber());
        existingCustomer.setFatherName(loanRequestDTO.getCustomerFatherName());

        customerRepository.save(existingCustomer);

        // Update Guarantor
        Guarantor existingGuarantor = existingLoan.getGuarantor();
        existingGuarantor.setName(loanRequestDTO.getGuarantorName());
        existingGuarantor.setPhoneNumberPrimary(loanRequestDTO.getGuarantorPhonePrimary());
        existingGuarantor.setPhoneNumberSecondary(loanRequestDTO.getGuarantorPhonePrimary());
        existingGuarantor.setAddress(loanRequestDTO.getGuarantorFullAddress());
        existingGuarantor.setAadhaarNumber(loanRequestDTO.getGuarantorAadhaarNumber());

        guarantorRepository.save(existingGuarantor);

        // Update Vehicle
        Vehicle existingVehicle = existingLoan.getVehicle();
        existingVehicle.setVehicleNumber(loanRequestDTO.getVehicleNumber());
        existingVehicle.setModelYear(loanRequestDTO.getVehicleModelYear());
        existingVehicle.setInsuranceExpiryDate(loanRequestDTO.getVehicleInsuranceExpiryDate());

        vehicleRepository.save(existingVehicle);

        // Save the updated Loan
        loanRepository.save(existingLoan);

    	
    }
    
    
    

	

	@Override
	public List<LoanResponseDTO> getAllLoans() {
		
		List<Loan> loans = loanRepository.findAll(Sort.by(Sort.Direction.ASC, "fileNumber"));

		
		
		return loans.stream()
				.map(loan-> {
					Customer customer = loan.getCustomer();                               //customerRepository.findById(loan.getId())
			 				//.orElseThrow(() -> new RuntimeException("Customer not found"));
			 		Vehicle vehicle = loan.getVehicle();//vehicleRepository.findById(loan.getId());
			 				//.orElseThrow(() -> new RuntimeException("Vehicle not found"));
			 
			 return toLoanViewDTO(loan, customer, vehicle);
		})
		.collect(Collectors.toList());
		
	}

	private LoanResponseDTO toLoanViewDTO(Loan loan, Customer customer, Vehicle vehicle) {
		// TODO Auto-generated method stub
		
		LoanResponseDTO dto = new LoanResponseDTO();
		dto.setId(loan.getId());
		 dto.setCustomerName(customer.getName());
	        dto.setLoanAmount(loan.getLoanAmount());
	        dto.setFileNumber(loan.getFileNumber());
	        dto.setEmi(loan.getEmi());
	        dto.setPhoneNumberPrimary(customer.getPhoneNumberPrimary());
	        dto.setVehicleNumber(vehicle.getVehicleNumber());
	        dto.setInsuranceExpiryDate(vehicle.getInsuranceExpiryDate());
	        return dto;
		
	}
	
	
	
	 @Override
	    public FullLoanDetailsDTO getFullLoanDetailsById(Long loanId) {
	        Loan loan = loanRepository.findByFileNumber(loanId).get();
	         
	        if (loan == null) {
	            throw new EntityNotFoundException("Loan not found with ID: " + loanId);
	        }
	        return LoanMapper.toFullLoanDetailsDTO(loan);
	    }
	 
	





	@Override
	public LoanDTO getLoanById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void deleteLoan(Long id) {
		Loan existingLoan = loanRepository.findByFileNumber(id).get();
		loanRepository.delete(existingLoan);

	}
	
	@Override
	public LoanStatsDTO getLoanStatistics() {
	    long totalLoans = loanRepository.count(); // Get the total number of loans
	    double totalLoanAmountGiven = loanRepository.getTotalLoanAmount(); // Get the total loan amount
	    double totalAmountReceived = loanRepository.getTotalReceivedAmount(); // Get the total amount received
	    double totalOutstandingAmount = loanRepository.getTotalOutstandingAmount(); // Get the total outstanding amount
	    int activeLoans = loanRepository.getActiveLoanCount(); // Get the count of active loans
	    int closedLoans = loanRepository.getClosedLoanCount(); // Get the count of closed loans

	    // Create and return a LoanStatsDTO with all the gathered statistics
	    return new LoanStatsDTO(totalLoans, totalLoanAmountGiven, totalAmountReceived, totalOutstandingAmount, activeLoans, closedLoans);
	}

	
	
}
