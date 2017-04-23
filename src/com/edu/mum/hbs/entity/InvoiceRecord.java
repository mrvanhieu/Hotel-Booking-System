package com.edu.mum.hbs.entity;

import java.time.LocalDate;

import com.edu.mum.hbs.restapi.util.LocalDateWithStringsDeserializer;
import com.edu.mum.hbs.restapi.util.LocalDateWithStringsSerializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
	@JsonSerialize(using = LocalDateWithStringsSerializable.class)
	@JsonDeserialize(using = LocalDateWithStringsDeserializer.class)
	private LocalDate checkInDate;
	
	@JsonSerialize(using = LocalDateWithStringsSerializable.class)
	@JsonDeserialize(using = LocalDateWithStringsDeserializer.class)
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

	public void setCheckOutDateByString(String checkOutDate) {
		this.checkOutDate = LocalDate.parse(checkOutDate);
	}
	
	public double getRoomAmount() {
		return roomAmount;
	}

	@JsonIgnore
	public String getRoomAmountDollar() {
		return "$" + roomAmount;
	}
	
	public void setRoomAmount(double roomAmount) {
		this.roomAmount = roomAmount;
	}

	public void setRoomAmountByString(String roomAmount) {
		this.roomAmount = Double.parseDouble(roomAmount);
	}
	
	public double getServiceAmount() {
		return serviceAmount;
	}

	@JsonIgnore
	public String getServiceAmountDollar() {
		return "$" + serviceAmount;
	}
	
	public void setServiceAmount(double serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public void setServiceAmountByString(String serviceAmount) {
		this.serviceAmount = Double.parseDouble(serviceAmount);
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}
	
	@JsonIgnore
	public String getTotalAmountDollar() {
		return "$" + totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public void setTotalAmountByString(String totalAmount) {
		this.totalAmount = Double.parseDouble(totalAmount);
	}

}
