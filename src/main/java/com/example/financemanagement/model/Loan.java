package com.example.financemanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String fileNumber;
    private String customerName;
    private Double loanAmount;
    private Integer tenure;
    private Double interestRate;
    private String vehicleNumber;
    private String insuranceValidity;
    private Double emi;
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
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Integer getTenure() {
		return tenure;
	}
	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}
	public Double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getInsuranceValidity() {
		return insuranceValidity;
	}
	public void setInsuranceValidity(String insuranceValidity) {
		this.insuranceValidity = insuranceValidity;
	}
	public Double getEmi() {
		return emi;
	}
	public void setEmi(Double emi) {
		this.emi = emi;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    
    
    
    

}
