package com.example.financemanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.financemanagement.dto.FullLoanDetailsDTO;
import com.example.financemanagement.dto.LoanDTO;
import com.example.financemanagement.dto.LoanRequestDTO;
import com.example.financemanagement.model.Customer;
import com.example.financemanagement.model.Guarantor;
import com.example.financemanagement.model.Loan;
import com.example.financemanagement.model.Vehicle;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    Loan toEntity(LoanDTO loanDTO);
   
	Loan toEntity(LoanRequestDTO loanRequest);
	
	LoanRequestDTO toDto(Loan loan);
	
	@Mapping(source = "loanAmount", target = "loanAmount")
    @Mapping(source = "interestRate", target = "interestRate")
    @Mapping(source = "tenure", target = "tenure")
	@Mapping(source="loanCreationDate", target="loanCreationDate")
    Loan toLoanEntity(LoanRequestDTO loanRequest);
	
	
	 @Mapping(source = "customerName", target = "name")
	 @Mapping(source = "customerPhonePrimary", target = "phoneNumberPrimary")
	 @Mapping(source = "customerFullAddress", target = "address")
	 @Mapping(source="customerAadhaarNumber", target="aadhaarNumber")
	 @Mapping(source="customerFatherName", target = "fatherName")
	 @Mapping(source="customerPhoneSecondary", target = "phoneNumberSecondary")
	 Customer toCustomerEntity(LoanRequestDTO loanRequest);
	
	 
	 @Mapping(source = "guarantorName", target = "name")
	 @Mapping(source = "guarantorPhonePrimary", target = "phoneNumberPrimary")
	 @Mapping(source = "guarantorFullAddress", target = "address")
	 @Mapping(source = "guarantorAadhaarNumber", target = "aadhaarNumber")
	 Guarantor toGuarantorEntity(LoanRequestDTO loanRequest);

	 
	 @Mapping(source = "vehicleNumber", target = "vehicleNumber")
	 @Mapping(source = "vehicleModelYear", target = "modelYear")
	 @Mapping(source = "vehicleInsuranceExpiryDate", target = "insuranceExpiryDate")
	 Vehicle toVehicleEntity(LoanRequestDTO loanRequest);
	 
	 
	 
	 public static FullLoanDetailsDTO toFullLoanDetailsDTO(Loan loan) {
	        FullLoanDetailsDTO dto = new FullLoanDetailsDTO();

	        // Loan Details
	        dto.setLoanAmount(loan.getLoanAmount());
	        dto.setInterestRate(loan.getInterestRate());
	        dto.setEmi(loan.getEmi());
	        dto.setTenure(loan.getTenure());
	        dto.setFileNumber(loan.getFileNumber());
	        dto.setLoanCreationDate(loan.getLoanCreationDate());

	        // Customer Details
	        if (loan.getCustomer() != null) {
	            dto.setCustomerName(loan.getCustomer().getName());
	            dto.setCustomerPhonePrimary(loan.getCustomer().getPhoneNumberPrimary());
	            dto.setCustomerPhoneSecondary(loan.getCustomer().getPhoneNumberSecondary());
	            dto.setCustomerFullAddress(loan.getCustomer().getAddress());
	            dto.setCustomerAadhaarNumber(loan.getCustomer().getAadhaarNumber());
	            dto.setCustomerFatherName(loan.getCustomer().getFatherName());
	        }

	        // Vehicle Details
	        if (loan.getVehicle() != null) {
	            dto.setVehicleNumber(loan.getVehicle().getVehicleNumber());
	            dto.setVehicleModelYear(loan.getVehicle().getModelYear());
	            dto.setVehicleInsuranceExpiryDate(loan.getVehicle().getInsuranceExpiryDate());
	        }

	        // Guarantor Details
	        if (loan.getGuarantor() != null) {
	            dto.setGuarantorName(loan.getGuarantor().getName());
	            dto.setGuarantorPhonePrimary(loan.getGuarantor().getPhoneNumberPrimary());
	            dto.setGuarantorPhoneSecondary(loan.getGuarantor().getPhoneNumberSecondary());
	            dto.setGuarantorAadhaarNumber(loan.getGuarantor().getAadhaarNumber());
	            dto.setGuarantorFullAddress(loan.getGuarantor().getAddress());
	        }

	        return dto;
	    } 
	 
}
