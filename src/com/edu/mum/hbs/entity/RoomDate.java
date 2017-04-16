package com.edu.mum.hbs.entity;

import java.time.LocalDate;

public class RoomDate extends Room {
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	private Room room;

	public RoomDate(Room room){
		this(room, null, null);
	}

	public RoomDate(Room room, LocalDate checkInDate, LocalDate checkOutDate){
		this.room = room;
		this.roomNumber = this.room.getRoomNumber();
		this.roomType = this.room.getRoomType();
		this.roomClass = this.room.getRoomClass();
		this.roomPrice = this.room.getRoomPrice();
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}
	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Room getRoom(){
		return this.room;
	}
}
