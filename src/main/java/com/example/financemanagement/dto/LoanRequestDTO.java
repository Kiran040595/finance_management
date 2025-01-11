package com.example.financemanagement.dto;

import java.time.LocalDate;
import java.util.Date;

public class LoanRequestDTO {

    // Loan Details
    private Double loanAmount;
    private Double interestRate;
    private Double  emi; // Monthly EMI (calculated elsewhere, if needed)
    private Integer tenure; // Loan tenure in months
    private String fileNumber; // Unique Identifier for Loan
    private Date loanCreationDate;

    // Customer Details
    private String customerName;
    private String customerPhonePrimary;
    private String customerPhoneSecondary;
    private String customerFullAddress;
    private String customerAadhaarNumber;
    private String customerFatherName;

    // Vehicle Details
    private String vehicleNumber;
    private Integer vehicleModelYear;
    private LocalDate vehicleInsuranceExpiryDate;

    // Guarantor Details
    private String guarantorName;
    private String guarantorPhonePrimary;
    private String guarantorPhoneSecondary;
    private String guarantorAadhaarNumber;
    private String guarantorFullAddress;
    // Getters and Setters

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

   

    public Double getEmi() {
		return emi;
	}

	public void setEmi(Double emi) {
		this.emi = emi;
	}

	public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhonePrimary() {
        return customerPhonePrimary;
    }

    public void setCustomerPhonePrimary(String customerPhonePrimary) {
        this.customerPhonePrimary = customerPhonePrimary;
    }

    public String getCustomerPhoneSecondary() {
        return customerPhoneSecondary;
    }

    public void setCustomerPhoneSecondary(String customerPhoneSecondary) {
        this.customerPhoneSecondary = customerPhoneSecondary;
    }

   

    public String getCustomerFullAddress() {
		return customerFullAddress;
	}

	public void setCustomerFullAddress(String customerFullAddress) {
		this.customerFullAddress = customerFullAddress;
	}

	public String getCustomerAadhaarNumber() {
        return customerAadhaarNumber;
    }

    public void setCustomerAadhaarNumber(String customerAadhaarNumber) {
        this.customerAadhaarNumber = customerAadhaarNumber;
    }

    public String getCustomerFatherName() {
        return customerFatherName;
    }

    public void setCustomerFatherName(String customerFatherName) {
        this.customerFatherName = customerFatherName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Integer getVehicleModelYear() {
        return vehicleModelYear;
    }

    public void setVehicleModelYear(Integer vehicleModelYear) {
        this.vehicleModelYear = vehicleModelYear;
    }

    public LocalDate getVehicleInsuranceExpiryDate() {
        return vehicleInsuranceExpiryDate;
    }

    public void setVehicleInsuranceExpiryDate(LocalDate vehicleInsuranceExpiryDate) {
        this.vehicleInsuranceExpiryDate = vehicleInsuranceExpiryDate;
    }

    public String getGuarantorName() {
        return guarantorName;
    }

    public void setGuarantorName(String guarantorName) {
        this.guarantorName = guarantorName;
    }

    public String getGuarantorPhonePrimary() {
        return guarantorPhonePrimary;
    }

    public void setGuarantorPhonePrimary(String guarantorPhonePrimary) {
        this.guarantorPhonePrimary = guarantorPhonePrimary;
    }

    public String getGuarantorPhoneSecondary() {
        return guarantorPhoneSecondary;
    }

    public void setGuarantorPhoneSecondary(String guarantorPhoneSecondary) {
        this.guarantorPhoneSecondary = guarantorPhoneSecondary;
    }

  

   

	public Date getLoanCreationDate() {
		return loanCreationDate;
	}

	public void setLoanCreationDate(Date loanCreationDate) {
		this.loanCreationDate = loanCreationDate;
	}

	public String getGuarantorAadhaarNumber() {
        return guarantorAadhaarNumber;
    }

    public void setGuarantorAadhaarNumber(String guarantorAadhaarNumber) {
        this.guarantorAadhaarNumber = guarantorAadhaarNumber;
    }

	public String getGuarantorFullAddress() {
		return guarantorFullAddress;
	}

	public void setGuarantorFullAddress(String guarantorFullAddress) {
		this.guarantorFullAddress = guarantorFullAddress;
	}
    
}
