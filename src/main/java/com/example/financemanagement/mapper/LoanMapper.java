package com.example.financemanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
    Loan toLoanEntity(LoanRequestDTO loanRequest);
	
	
	 @Mapping(source = "customerName", target = "name")
	 @Mapping(source = "customerPhonePrimary", target = "phoneNumberPrimary")
	 @Mapping(source = "customerAddress", target = "address")
	 Customer toCustomerEntity(LoanRequestDTO loanRequest);
	
	 
	 @Mapping(source = "guarantorName", target = "name")
	 @Mapping(source = "guarantorPhonePrimary", target = "phoneNumberPrimary")
	 @Mapping(source = "guarantorAddress", target = "address")
	 Guarantor toGuarantorEntity(LoanRequestDTO loanRequest);

	 
	 @Mapping(source = "vehicleNumber", target = "vehicleNumber")
	 @Mapping(source = "vehicleModelYear", target = "modelYear")
	 @Mapping(source = "vehicleInsuranceExpiryDate", target = "insuranceExpiryDate")
	 Vehicle toVehicleEntity(LoanRequestDTO loanRequest);
}
