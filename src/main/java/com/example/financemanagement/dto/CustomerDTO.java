package com.example.financemanagement.dto;

public class CustomerDTO {
	
	 	private String name;
	    private String phoneNumberPrimary;
	    private String phoneNumberSecondary;
	    private String address;
	    private String aadhaarNumber;
	    private String fatherName;
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
		public String getFatherName() {
			return fatherName;
		}
		public void setFatherName(String fatherName) {
			this.fatherName = fatherName;
		}

}
