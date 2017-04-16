package com.edu.mum.hbs.entity;

public class Customer extends Person {
	public static final String TABLE_NAME = "Customer";
	public static final String PASSPORT_OR_ID = "passport_id";
	public static final String FULL_NAME = "fullname";
	public static final String DATE_OF_BIRTH = "dob";
	public static final String PHONE_NUMBER = "phone_no";
	public static final String ADDRESS = "address";
	private String passportOrId;

	public String getPassportOrId() {
		return passportOrId;
	}
	public void setPassportOrId(String passportOrId) {
		this.passportOrId = passportOrId;
	}
	
}
