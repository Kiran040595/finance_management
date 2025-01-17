package com.example.financemanagement.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.financemanagement.dto.FullLoanDetailsDTO;
import com.example.financemanagement.dto.LoanDTO;
import com.example.financemanagement.dto.LoanRequestDTO;
import com.example.financemanagement.dto.LoanResponseDTO;
import com.example.financemanagement.mapper.LoanMapper;
import com.example.financemanagement.model.Customer;
import com.example.financemanagement.model.Guarantor;
import com.example.financemanagement.model.Loan;
import com.example.financemanagement.model.LoanEmi;
import com.example.financemanagement.model.Vehicle;
import com.example.financemanagement.repository.CustomerRepository;
import com.example.financemanagement.repository.GuarantorRepository;
import com.example.financemanagement.repository.LoanEmiRepository;
import com.example.financemanagement.repository.LoanRepository;
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
    	Loan existingLoan = loanRepository.findById(id)
    	        .orElseThrow(() -> new RuntimeException("Loan not found with ID: " + id));
    	
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
		
		List<Loan> loans = loanRepository.findAll();
		
		
		return loans.stream()
				.map(loan-> {
					Customer customer = customerRepository.findById(loan.getId())
			 				.orElseThrow(() -> new RuntimeException("Customer not found"));
			 		Vehicle vehicle = vehicleRepository.findById(loan.getId())
			 				.orElseThrow(() -> new RuntimeException("Vehicle not found"));
			 
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
	        Loan loan = loanRepository.findLoanWithDetailsById(loanId);
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
		Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found with ID: " + id));
		loanRepository.delete(existingLoan);

	}
}
