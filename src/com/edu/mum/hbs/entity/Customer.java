package com.edu.mum.hbs.entity;

public class Customer extends Person implements Entity {
	public static final String TABLE_NAME = "Customer";
	public static final String PASSPORT_OR_ID = "passport_id";
	public static final String FULL_NAME = "fullname";
	public static final String DATE_OF_BIRTH = "dob";
	public static final String PHONE_NUMBER = "phone_no";
	public static final String ADDRESS = "address";
	@Id
	private String passport_id;
	
	@Column
	private String fullname;
	
	@Column
	private String phone_no;
	@Column
	private String address;
	@Column
	private String dob;

	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getPassport_id() {
		return passport_id;
	}
	public void setPassport_id(String passportOrId) {
		this.passport_id = passportOrId;
	}
	@Override
	public String tableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
