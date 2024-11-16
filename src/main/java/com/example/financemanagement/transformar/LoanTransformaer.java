package com.example.financemanagement.transformar;

import org.springframework.stereotype.Component;

import com.example.financemanagement.dto.LoanDTO;
import com.example.financemanagement.model.Loan;

@Component
public class LoanTransformaer {
	
	
	public Loan toEntity(LoanDTO loanDTO) {
	    Loan loan = new Loan();
	    loan.setFileNumber(loanDTO.getFileNumber());
	    loan.setLoanAmount(loanDTO.getLoanAmount());  // Note: Check if loanAmount is nullable
	    loan.setTenure(loanDTO.getTenure());
	    loan.setInterestRate(loanDTO.getInterestRate());
	    return loan;
	}
	
	public LoanDTO toDto(Loan loan) {
		LoanDTO loanDTO = new LoanDTO();
		loanDTO.setFileNumber(loan.getFileNumber());
		loanDTO.setLoanAmount(loan.getLoanAmount());  // Note: Check if loanAmount is nullable
		loanDTO.setTenure(loan.getTenure());
		loanDTO.setInterestRate(loan.getInterestRate());
	    loanDTO.setEmi(loan.getEmi());
	    return loanDTO;
		
	}


}
