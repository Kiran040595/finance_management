package com.example.financemanagement.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanRequestDTO {
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String customerAdharCardNumber;
    private String vehicleNumber;
    private String vehicleModel;
    private LocalDate vehicleInsuranceValidity;
    private BigDecimal loanAmount;
    private int emiTenure;
    private BigDecimal monthlyEmi;
    private LocalDate emiDate;

    // Getters and setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerAdharCardNumber() {
        return customerAdharCardNumber;
    }

    public void setCustomerAdharCardNumber(String customerAdharCardNumber) {
        this.customerAdharCardNumber = customerAdharCardNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public LocalDate getVehicleInsuranceValidity() {
        return vehicleInsuranceValidity;
    }

    public void setVehicleInsuranceValidity(LocalDate vehicleInsuranceValidity) {
        this.vehicleInsuranceValidity = vehicleInsuranceValidity;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getEmiTenure() {
        return emiTenure;
    }

    public void setEmiTenure(int emiTenure) {
        this.emiTenure = emiTenure;
    }

    public BigDecimal getMonthlyEmi() {
        return monthlyEmi;
    }

    public void setMonthlyEmi(BigDecimal monthlyEmi) {
        this.monthlyEmi = monthlyEmi;
    }

    public LocalDate getEmiDate() {
        return emiDate;
    }

    public void setEmiDate(LocalDate emiDate) {
        this.emiDate = emiDate;
    }
}

