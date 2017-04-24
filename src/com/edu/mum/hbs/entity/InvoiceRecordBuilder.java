package com.edu.mum.hbs.entity;

import java.time.LocalDate;

public class InvoiceRecordBuilder implements Builder {
	private InvoiceRecord invoiceRecord = new InvoiceRecord();

	@Override
	public void buildPassportOrId(String passportOrId) {
		invoiceRecord.setPassport_id(passportOrId);
	}

	@Override
	public void buildRoomNumber(String roomNumber) {
		invoiceRecord.setRoom_number(roomNumber);
	}

	@Override
	public void buildCheckInDate(LocalDate checkInDate) {
		invoiceRecord.setCheck_in_date(checkInDate);
	}

	@Override
	public void buildCheckOutDate(LocalDate checkOutDate) {
		invoiceRecord.setCheck_out_date(checkOutDate);
	}

	@Override
	public void buildRoomAmount(double roomAmount) {
		invoiceRecord.setRoom_amount(roomAmount);
	}

	@Override
	public void buildServiceAmount(double serviceAmount) {
		invoiceRecord.setService_amount(serviceAmount);
	}

	@Override
	public void buildTotalAmount(double totalAmount) {
		invoiceRecord.setTotal_amount(totalAmount);
	}

	public InvoiceRecord getInvoiceRecord() {
		return invoiceRecord;
	}

}
