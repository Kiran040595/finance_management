package com.example.financemanagement.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.financemanagement.model.LoanEmi;
import com.fasterxml.jackson.annotation.JsonFormat;

public class PaymentDTO {
    private Long loanId;
    private String fileNumber;
    private String customerName;
    private Double loanAmount;
    private String vehicleNumber;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date loanCreationDate;
    private Double emi;
    private Integer paidEmiCount;
    private Integer tenure;
    private Integer remainingEmi;
    private List<LoanEmi> emiDetails; // Add this field to hold EMI details
    
    private Double overdueAmount;
    
    private Double totalPendingEmiAmount;
    
    private long pendingDays;
    
    Set<String> phoneNumbers = new HashSet<>(Arrays.asList());


    // Getters and Setters
    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
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

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

   

    public Date getLoanCreationDate() {
		return loanCreationDate;
	}

	public void setLoanCreationDate(Date loanCreationDate) {
		this.loanCreationDate = loanCreationDate;
	}

	public Double getEmi() {
        return emi;
    }

    public void setEmi(Double emi) {
        this.emi = emi;
    }

    public Integer getPaidEmiCount() {
        return paidEmiCount;
    }

    public void setPaidEmiCount(Integer paidEmiCount) {
        this.paidEmiCount = paidEmiCount;
    }

    public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public Integer getRemainingEmi() {
        return remainingEmi;
    }

    public void setRemainingEmi(Integer remainingEmi) {
        this.remainingEmi = remainingEmi;
    }

    public List<LoanEmi> getEmiDetails() {
        return emiDetails;
    }

    public void setEmiDetails(List<LoanEmi> emiDetails) {
        this.emiDetails = emiDetails;
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

	public long getPendingDays() {
		return pendingDays;
	}

	public void setPendingDays(long pendingDays) {
		this.pendingDays = pendingDays;
	}

	public Set<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(Set<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	

	
    
}
