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

		Customer customer = cDao.getCustomer(invoice.getPassport_id());

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
		return invoice.getRoom_number();
	}
	@JsonIgnore
	public LocalDate getCheckInDate() {
		return invoice.getCheck_in_date();
	}
	@JsonIgnore
	public LocalDate getCheckOutDate() {
		return invoice.getCheck_out_date();
	}
	@JsonIgnore
	public double getRoomAmount() {
		return invoice.getRoom_amount();
	}
	@JsonIgnore
	public double getServiceAmount() {
		return invoice.getService_amount();
	}
	@JsonIgnore
	public double getTotalAmount() {
		return invoice.getTotal_amount();
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
