package com.example.financemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.financemanagement.dto.LoanDTO;
import com.example.financemanagement.dto.LoanRequestDTO;
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
	public List<LoanDTO> getAllLoans() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoanDTO getLoanById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
