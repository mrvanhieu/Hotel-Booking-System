package com.edu.mum.hbs.entity;

import java.time.LocalDate;

public class Person {

    public Person() {
    }

    public Person(String fullName, LocalDate dayOfBirth, String address, String phoneNo) {
    	this.fullName = fullName;
    	this.dayOfBirth = dayOfBirth;
    	this.address = address;
        this.phoneNo = phoneNo;
    }

    public String fullName;

    public String address;

    public String phoneNo;
    
    public LocalDate dayOfBirth;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
 
}