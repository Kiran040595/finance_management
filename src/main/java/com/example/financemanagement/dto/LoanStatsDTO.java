package com.example.financemanagement.dto;

import lombok.Data;

@Data
public class LoanStatsDTO {

    private int totalLoans;
    private double totalLoanAmountGiven;
    private double totalAmountReceived;
    private double totalOutstandingAmount;
    private int activeLoans;
    private int closedLoans;

    // Custom constructor to set values
    public LoanStatsDTO(long totalLoans, double totalLoanAmountGiven, double totalAmountReceived,
                         double totalOutstandingAmount, int activeLoans, int closedLoans) {
        this.totalLoans = (int) totalLoans;  // Casting if needed (long to int)
        this.totalLoanAmountGiven = totalLoanAmountGiven;
        this.totalAmountReceived = totalAmountReceived;
        this.totalOutstandingAmount = totalOutstandingAmount;
        this.activeLoans = activeLoans;
        this.closedLoans = closedLoans;
    }

	public int getTotalLoans() {
		return totalLoans;
	}

	public void setTotalLoans(int totalLoans) {
		this.totalLoans = totalLoans;
	}

	public double getTotalLoanAmountGiven() {
		return totalLoanAmountGiven;
	}

	public void setTotalLoanAmountGiven(double totalLoanAmountGiven) {
		this.totalLoanAmountGiven = totalLoanAmountGiven;
	}

	public double getTotalAmountReceived() {
		return totalAmountReceived;
	}

	public void setTotalAmountReceived(double totalAmountReceived) {
		this.totalAmountReceived = totalAmountReceived;
	}

	public double getTotalOutstandingAmount() {
		return totalOutstandingAmount;
	}

	public void setTotalOutstandingAmount(double totalOutstandingAmount) {
		this.totalOutstandingAmount = totalOutstandingAmount;
	}

	public int getActiveLoans() {
		return activeLoans;
	}

	public void setActiveLoans(int activeLoans) {
		this.activeLoans = activeLoans;
	}

	public int getClosedLoans() {
		return closedLoans;
	}

	public void setClosedLoans(int closedLoans) {
		this.closedLoans = closedLoans;
	}
    
    
    
}
