package com.edu.mum.hbs.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class CustomerAndRoom implements Entity{
	public static final String ROOM_NUMBER = "room_number";
	public static final String PASSPORT_OR_ID = "passport_id";
	public static final String STATUS = "status";
	public static final String CHECK_IN_DATE = "check_in_date";
	public static final String CHECK_OUT_DATE = "check_out_date";
	public static final String TABLE_NAME = "CustomerAndRoom";
	public static final String BOOKING_STATUS = "Booking";
	public static final String CHECKED_STATUS = "Checked";
	@Id(Name=ROOM_NUMBER)
	private String roomNumber;
	
	@Id(Name=PASSPORT_OR_ID)
	private String passportOrId;
	
	@Column(Name=STATUS)
	private String status;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@Column(Name=CHECK_IN_DATE)
	private LocalDate checkInDate;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@Column(Name=CHECK_OUT_DATE)
	private LocalDate checkOutDate;
	
	public CustomerAndRoom(){}
	/**
	 * @param roomNumber
	 * @param passportOrId
	 * @param status
	 * @param checkInDate
	 * @param checkOutDate
	 */
	public CustomerAndRoom(String roomNumber, String passportOrId, String status, LocalDate checkInDate,
			LocalDate checkOutDate) {
		super();
		this.roomNumber = roomNumber;
		this.passportOrId = passportOrId;
		this.status = status;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}
	
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getPassportOrId() {
		return passportOrId;
	}
	public void setPassportOrId(String passportOrId) {
		this.passportOrId = passportOrId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}
	public void setCheckInDateByString(String checkinDate){
		this.checkInDate = LocalDate.parse(checkinDate);
	}
	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public void setCheckOutDateByString(String checkOutDate){
		this.checkOutDate = LocalDate.parse(checkOutDate);
	}
	@Override
	public String tableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}
	
	
}
