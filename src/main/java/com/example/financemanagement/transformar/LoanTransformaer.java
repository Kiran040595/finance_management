package com.example.financemanagement.transformar;

import org.springframework.stereotype.Component;

import com.example.financemanagement.dto.LoanDTO;
import com.example.financemanagement.model.Loan;

@Component
public class LoanTransformaer {
	
	
	public Loan toEntity(LoanDTO loanDTO) {
	    Loan loan = new Loan();
	    loan.setFileNumber(loanDTO.getFileNumber());
	    loan.setCustomerName(loanDTO.getCustomerName());
	    loan.setLoanAmount(loanDTO.getLoanAmount());  // Note: Check if loanAmount is nullable
	    loan.setTenure(loanDTO.getTenure());
	    loan.setInterestRate(loanDTO.getInterestRate());
	    loan.setVehicleNumber(loanDTO.getVehicleNumber());
	    loan.setInsuranceValidity(loanDTO.getInsuranceValidity());
	    loan.setEmi(loanDTO.getEmi());
	    return loan;
	}
	
	public LoanDTO toDto(Loan loan) {
		LoanDTO loanDTO = new LoanDTO();
		loanDTO.setFileNumber(loan.getFileNumber());
		loanDTO.setCustomerName(loan.getCustomerName());
		loanDTO.setLoanAmount(loan.getLoanAmount());  // Note: Check if loanAmount is nullable
		loanDTO.setTenure(loan.getTenure());
		loanDTO.setInterestRate(loan.getInterestRate());
		loanDTO.setVehicleNumber(loan.getVehicleNumber());
	    loanDTO.setInsuranceValidity(loan.getInsuranceValidity());
	    loanDTO.setEmi(loan.getEmi());
	    return loanDTO;
		
	}


}
