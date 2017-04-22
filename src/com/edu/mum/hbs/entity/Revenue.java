package com.edu.mum.hbs.entity;

import java.time.LocalDate;

import org.junit.Ignore;

import com.edu.mum.hbs.dao.CustomerDao;
import com.edu.mum.hbs.dao.DaoFactoryImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Revenue implements Item {
	public InvoiceRecord getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceRecord invoice) {
		this.invoice = invoice;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	private String fullName;
	private String phoneNo;
	private InvoiceRecord invoice;

	public Revenue(InvoiceRecord invoice) {
		this.invoice = invoice;

		CustomerDao cDao = (CustomerDao) DaoFactoryImpl.getFactory().createDao(Customer.TABLE_NAME);

		Customer customer = cDao.getCustomer(invoice.getPassportOrId());

		fullName = customer.getFullName();
		phoneNo = customer.getPhoneNo();
	}

	public String getFullName() {
		return fullName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}
	@JsonIgnore
	public String getRoomNumber() {
		return invoice.getRoomNumber();
	}
	@JsonIgnore
	public LocalDate getCheckInDate() {
		return invoice.getCheckInDate();
	}
	@JsonIgnore
	public LocalDate getCheckOutDate() {
		return invoice.getCheckOutDate();
	}
	@JsonIgnore
	public double getRoomAmount() {
		return invoice.getRoomAmount();
	}
	@JsonIgnore
	public double getServiceAmount() {
		return invoice.getServiceAmount();
	}
	@JsonIgnore
	public double getTotalAmount() {
		return invoice.getTotalAmount();
	}

	@JsonIgnore
	public String getRoomAmountDollar() {
		return invoice.getRoomAmountDollar();
	}
	@JsonIgnore
	public String getServiceAmountDollar() {
		return invoice.getServiceAmountDollar();
	}

	public Revenue() {
		super();
	}

	@JsonIgnore
	public String getTotalAmountDollar() {
		return invoice.getTotalAmountDollar();
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}
}
