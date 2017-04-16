package com.edu.mum.hbs.entity;

import java.time.LocalDate;

public class CustomerAndRoom {
	public static final String ROOM_NUMBER = "room_number";
	public static final String PASSPORT_OR_ID = "passport_id";
	public static final String STATUS = "status";
	public static final String CHECK_IN_DATE = "check_in_date";
	public static final String CHECK_OUT_DATE = "check_out_date";
	public static final String TABLE_NAME = "CustomerAndRoom";
	public static final String BOOKING_STATUS = "Booking";
	public static final String CHECKED_STATUS = "Checked";
	private String roomNumber;
	private String passportOrId;
	private String status;
	private LocalDate checkInDate;
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
	public void setCheckInDate(String checkinDate){
		this.checkInDate = LocalDate.parse(checkinDate);
	}
	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public void setCheckOutDate(String checkOutDate){
		this.checkOutDate = LocalDate.parse(checkOutDate);
	}
	
	
}
