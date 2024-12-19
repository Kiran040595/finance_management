package com.example.financemanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

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
import com.example.financemanagement.model.Vehicle;
import com.example.financemanagement.repository.CustomerRepository;
import com.example.financemanagement.repository.GuarantorRepository;
import com.example.financemanagement.repository.LoanRepository;
import com.example.financemanagement.repository.VehicleRepository;

@Service
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
        
        loanRepository.save(loan);
       
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
}
