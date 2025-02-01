package com.example.financemanagement.dto;

import java.time.LocalDate;

public class LoanResponseDTO {
	
	private Long id;
	
    private String customerName;
    
    private Double loanAmount; 
    
    private Long fileNumber; 
    
    private Double emi;
    
    private String phoneNumberPrimary;
    
    private String vehicleNumber;
    
    private LocalDate insuranceExpiryDate;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Long getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(Long fileNumber) {
		this.fileNumber = fileNumber;
	}

	

	public Double getEmi() {
		return emi;
	}

	public void setEmi(Double emi) {
		this.emi = emi;
	}

	public String getPhoneNumberPrimary() {
		return phoneNumberPrimary;
	}

	public void setPhoneNumberPrimary(String phoneNumberPrimary) {
		this.phoneNumberPrimary = phoneNumberPrimary;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public LocalDate getInsuranceExpiryDate() {
		return insuranceExpiryDate;
	}

	public void setInsuranceExpiryDate(LocalDate insuranceExpiryDate) {
		this.insuranceExpiryDate = insuranceExpiryDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
    
    
    
    

}
