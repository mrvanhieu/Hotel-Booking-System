package com.edu.mum.hbs.entity;

import java.time.LocalDate;

import com.edu.mum.hbs.dao.DaoFactoryImpl;
import com.edu.mum.hbs.dao.CustomerDao;

public class Revenue {
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

	public String getRoomNumber() {
		return invoice.getRoomNumber();
	}

	public LocalDate getCheckInDate() {
		return invoice.getCheckInDate();
	}

	public LocalDate getCheckOutDate() {
		return invoice.getCheckOutDate();
	}

	public double getRoomAmount() {
		return invoice.getRoomAmount();
	}

	public double getServiceAmount() {
		return invoice.getServiceAmount();
	}

	public double getTotalAmount() {
		return invoice.getTotalAmount();
	}

	public String getRoomAmountDollar() {
		return invoice.getRoomAmountDollar();
	}

	public String getServiceAmountDollar() {
		return invoice.getServiceAmountDollar();
	}

	public String getTotalAmountDollar() {
		return invoice.getTotalAmountDollar();
	}
}
