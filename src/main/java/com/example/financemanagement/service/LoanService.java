package com.example.financemanagement.service;

import com.example.financemanagement.dto.LoanDTO;
import com.example.financemanagement.dto.LoanRequestDTO;
import com.example.financemanagement.model.Loan;
import com.example.financemanagement.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LoanService {
	
	
	List<LoanDTO> getAllLoans();
	
	LoanDTO getLoanById(Long id);
	public void createLoan(LoanRequestDTO loanRequest);
   
    
    
    
    

   
}
