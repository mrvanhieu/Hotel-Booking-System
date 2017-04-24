package com.edu.mum.hbs.entity;

import java.time.LocalDate;

import com.edu.mum.hbs.restapi.util.LocalDateWithStringsDeserializer;
import com.edu.mum.hbs.restapi.util.LocalDateWithStringsSerializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class InvoiceRecord implements Entity {
	public static final String TABLE_NAME = "InvoiceRecord";
	public static final String PASSPORT_OR_ID = "passport_id";
	public static final String ROOM_NUMBER = "room_number";
	public static final String CHECK_IN_DATE = "check_in_date";
	public static final String CHECK_OUT_DATE = "check_out_date";
	public static final String ROOM_AMOUNT = "room_amount";
	public static final String SERVICE_AMOUNT = "service_amount";
	public static final String TOTAL_AMOUNT = "total_amount";
	private String passport_id;
	private String room_number;
	@JsonSerialize(using = LocalDateWithStringsSerializable.class)
	@JsonDeserialize(using = LocalDateWithStringsDeserializer.class)
	@Column
	private LocalDate check_in_date;
	
	@JsonSerialize(using = LocalDateWithStringsSerializable.class)
	@JsonDeserialize(using = LocalDateWithStringsDeserializer.class)
	@Column
	private LocalDate check_out_date;
	@Column
	private double room_amount;
	@Column
	private double service_amount;
	@Column
	private double total_amount;

	public String getPassport_id() {
		return passport_id;
	}

	public void setPassport_id(String passportOrId) {
		this.passport_id = passportOrId;
	}

	public String getRoom_number() {
		return room_number;
	}

	public void setRoom_number(String roomNumber) {
		this.room_number = roomNumber;
	}

	public LocalDate getCheck_in_date() {
		return check_in_date;
	}

	public void setCheck_in_date(LocalDate checkInDate) {
		this.check_in_date = checkInDate;
	}

	public void setCheckInDateByString(String checkInDate) {
		this.check_in_date = LocalDate.parse(checkInDate);
	}
	
	public LocalDate getCheck_out_date() {
		return check_out_date;
	}

	public void setCheck_out_date(LocalDate checkOutDate) {
		this.check_out_date = checkOutDate;
	}

	public void setCheckOutDateByString(String checkOutDate) {
		this.check_out_date = LocalDate.parse(checkOutDate);
	}
	
	public double getRoom_amount() {
		return room_amount;
	}

	@JsonIgnore
	public String getRoomAmountDollar() {
		return "$" + room_amount;
	}
	
	public void setRoom_amount(double roomAmount) {
		this.room_amount = roomAmount;
	}

	public void setRoomAmountByString(String roomAmount) {
		this.room_amount = Double.parseDouble(roomAmount);
	}
	
	public double getService_amount() {
		return service_amount;
	}

	@JsonIgnore
	public String getServiceAmountDollar() {
		return "$" + service_amount;
	}
	
	public void setService_amount(double serviceAmount) {
		this.service_amount = serviceAmount;
	}

	public void setServiceAmountByString(String serviceAmount) {
		this.service_amount = Double.parseDouble(serviceAmount);
	}
	
	public double getTotal_amount() {
		return total_amount;
	}
	
	@JsonIgnore
	public String getTotalAmountDollar() {
		return "$" + total_amount;
	}

	public void setTotal_amount(double totalAmount) {
		this.total_amount = totalAmount;
	}
	
	public void setTotalAmountByString(String totalAmount) {
		this.total_amount = Double.parseDouble(totalAmount);
	}

	@Override
	public String tableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}

}
