package com.edu.mum.hbs.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class InvoiceRecord {
	public static final String TABLE_NAME = "InvoiceRecord";
	public static final String PASSPORT_OR_ID = "passport_id";
	public static final String ROOM_NUMBER = "room_number";
	public static final String CHECK_IN_DATE = "check_in_date";
	public static final String CHECK_OUT_DATE = "check_out_date";
	public static final String ROOM_AMOUNT = "room_amount";
	public static final String SERVICE_AMOUNT = "service_amount";
	public static final String TOTAL_AMOUNT = "total_amount";
	private String passportOrId;
	private String roomNumber;
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate checkInDate;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate checkOutDate;
	private double roomAmount;
	private double serviceAmount;
	private double totalAmount;

	public String getPassportOrId() {
		return passportOrId;
	}

	public void setPassportOrId(String passportOrId) {
		this.passportOrId = passportOrId;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public void setCheckInDateByString(String checkInDate) {
		this.checkInDate = LocalDate.parse(checkInDate);
	}
	
	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = LocalDate.parse(checkOutDate);
	}
	
	public double getRoomAmount() {
		return roomAmount;
	}

	public String getRoomAmountDollar() {
		return "$" + roomAmount;
	}
	
	public void setRoomAmount(double roomAmount) {
		this.roomAmount = roomAmount;
	}

	public void setRoomAmount(String roomAmount) {
		this.roomAmount = Double.parseDouble(roomAmount);
	}
	
	public double getServiceAmount() {
		return serviceAmount;
	}

	public String getServiceAmountDollar() {
		return "$" + serviceAmount;
	}
	
	public void setServiceAmount(double serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public void setServiceAmount(String serviceAmount) {
		this.serviceAmount = Double.parseDouble(serviceAmount);
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}
	
	public String getTotalAmountDollar() {
		return "$" + totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = Double.parseDouble(totalAmount);
	}

}
