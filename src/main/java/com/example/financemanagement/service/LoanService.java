package com.example.financemanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.financemanagement.dto.LoanRequestDTO;
import com.example.financemanagement.model.Customer;
import com.example.financemanagement.model.Loan;
import com.example.financemanagement.model.Vehicle;
import com.example.financemanagement.repository.CustomerRepository;
import com.example.financemanagement.repository.LoanRepository;
import com.example.financemanagement.repository.VehicleRepository;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;
    
    
    @Transactional
    public Loan createLoan(LoanRequestDTO loanRequestDTO) {
        // Create customer
        Customer customer = new Customer();
        customer.setName(loanRequestDTO.getCustomerName());
        customer.setPhone(loanRequestDTO.getCustomerPhone());
        customer.setAddress(loanRequestDTO.getCustomerAddress());
        customer.setAdharCardNumber(loanRequestDTO.getCustomerAdharCardNumber());
        customerRepository.save(customer);

        // Create vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(loanRequestDTO.getVehicleNumber());
        vehicle.setVehicleModel(loanRequestDTO.getVehicleModel());
        vehicle.setInsuranceValidity(loanRequestDTO.getVehicleInsuranceValidity());
        vehicle.setCustomer(customer);
        vehicleRepository.save(vehicle);

        // Create loan
        Loan loan = new Loan();
        loan.setLoanAmount(loanRequestDTO.getLoanAmount());
        loan.setEmiTenure(loanRequestDTO.getEmiTenure());
        loan.setMonthlyEmi(loanRequestDTO.getMonthlyEmi());
        loan.setEmiDate(loanRequestDTO.getEmiDate());
        loan.setVehicle(vehicle);
        return loanRepository.save(loan);
    }
    
    
    
    
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}
