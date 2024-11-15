package com.example.financemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.financemanagement.dto.LoanDTO;
import com.example.financemanagement.mapper.LoanMapper;
import com.example.financemanagement.model.Loan;
import com.example.financemanagement.repository.LoanRepository;
import com.example.financemanagement.transformar.LoanTransformaer;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class LoanServiceImpl implements LoanService {

	
	@Autowired
    private LoanRepository loanRepository;
	
	@Autowired
	private LoanTransformaer loanTransformaer;
	
	 @Autowired
	 private LoanMapper loanMapper;
	
	@Override
	public LoanDTO createLoan(LoanDTO loanDTO) {
		
		Loan loan = loanMapper.toEntity(loanDTO);
		loan = loanRepository.save(loan);
		
		return loanMapper.toDto(loan);
		
	}
	
	
	 @Override
	 public List<LoanDTO> getAllLoans() {
	        List<Loan> loans = loanRepository.findAll(); // Fetch all loans from the database
	        return loans.stream() 			// Convert List<Loan> to List<LoanDTO>
	                    .map(loanMapper::toDto)
	                    .collect(Collectors.toList());
	    }
	
}
