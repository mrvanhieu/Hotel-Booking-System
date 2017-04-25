package com.edu.mum.hbs.entity;

public class Person {

    public Person() {
    }

    public Person(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    public String phoneNo;
    
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
 
}