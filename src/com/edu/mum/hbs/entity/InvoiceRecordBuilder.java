package com.edu.mum.hbs.entity;

import java.time.LocalDate;

public class InvoiceRecordBuilder implements Builder {
	private InvoiceRecord invoiceRecord = new InvoiceRecord();

	@Override
	public void buildPassportOrId(String passportOrId) {
		invoiceRecord.setPassportOrId(passportOrId);
	}

	@Override
	public void buildRoomNumber(String roomNumber) {
		invoiceRecord.setRoomNumber(roomNumber);
	}

	@Override
	public void buildCheckInDate(LocalDate checkInDate) {
		invoiceRecord.setCheckInDate(checkInDate);
	}

	@Override
	public void buildCheckOutDate(LocalDate checkOutDate) {
		invoiceRecord.setCheckOutDate(checkOutDate);
	}

	@Override
	public void buildRoomAmount(double roomAmount) {
		invoiceRecord.setRoomAmount(roomAmount);
	}

	@Override
	public void buildServiceAmount(double serviceAmount) {
		invoiceRecord.setServiceAmount(serviceAmount);
	}

	@Override
	public void buildTotalAmount(double totalAmount) {
		invoiceRecord.setTotalAmount(totalAmount);
	}

	public InvoiceRecord getInvoiceRecord() {
		return invoiceRecord;
	}

}
