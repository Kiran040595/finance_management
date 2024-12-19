package com.example.financemanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Guarantor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phoneNumberPrimary;
    private String phoneNumberSecondary;
    private String address;
    private String aadhaarNumber;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumberPrimary() {
		return phoneNumberPrimary;
	}
	public void setPhoneNumberPrimary(String phoneNumberPrimary) {
		this.phoneNumberPrimary = phoneNumberPrimary;
	}
	public String getPhoneNumberSecondary() {
		return phoneNumberSecondary;
	}
	public void setPhoneNumberSecondary(String phoneNumberSecondary) {
		this.phoneNumberSecondary = phoneNumberSecondary;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAadhaarNumber() {
		return aadhaarNumber;
	}
	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}
    
    
    
    // Getters and Setters
}
