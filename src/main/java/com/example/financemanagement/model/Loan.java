package com.example.financemanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String fileNumber; // Unique Identifier

	private Double loanAmount;
	private Double interestRate;
	private Double emi;
	private Integer tenure;
	private Date loanCreationDate;
	private Integer paidEmiCount = 0; // Track the number of EMIs paid
	private Integer remainingEmiCount;
	
	private Double overdueAmount; // Calculated at loan level
    private Double totalPendingEmiAmount; // Calculated at loan level
    private Long pendingDays;
    private LocalDate lastUpdated;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean status = true;

	
    

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;

	@ManyToOne
	@JoinColumn(name = "guarantor_id")
	private Guarantor guarantor;

	@OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<LoanEmi> emis; // List of EMIs associated with the loan
	
	public Loan() {
        // Default constructor for JPA
    }
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Guarantor getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(Guarantor guarantor) {
		this.guarantor = guarantor;
	}

	public Date getLoanCreationDate() {
		return loanCreationDate;
	}

	public void setLoanCreationDate(Date loanCreationDate) {
		this.loanCreationDate = loanCreationDate;
	}

	
	public Integer getPaidEmiCount() {
		return paidEmiCount;
	}

	public void setPaidEmiCount(Integer paidEmiCount) {
		this.paidEmiCount = paidEmiCount;
	}

	public Integer getRemainingEmiCount() {
		return remainingEmiCount;
	}

	public void setRemainingEmiCount(Integer remainingEmiCount) {
		this.remainingEmiCount = remainingEmiCount;
	}

	public List<LoanEmi> getEmis() {
		return emis;
	}

	public void setEmis(List<LoanEmi> emis) {
		this.emis = emis;
	}


	public Double getOverdueAmount() {
		return overdueAmount;
	}


	public void setOverdueAmount(Double overdueAmount) {
		this.overdueAmount = overdueAmount;
	}


	public Double getTotalPendingEmiAmount() {
		return totalPendingEmiAmount;
	}


	public void setTotalPendingEmiAmount(Double totalPendingEmiAmount) {
		this.totalPendingEmiAmount = totalPendingEmiAmount;
	}


	public Long getPendingDays() {
		return pendingDays;
	}


	public void setPendingDays(Long pendingDays) {
		this.pendingDays = pendingDays;
	}


	public LocalDate getLastUpdated() {
		return lastUpdated;
	}


	public void setLastUpdated(LocalDate lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	
	@PrePersist
    public void setLastUpdatedBeforeSave() {
        if (this.lastUpdated == null) {
            this.lastUpdated = LocalDate.now(); // Set current time if not already set
        }
    }


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	


	
	
	// Getters and Setters
}
