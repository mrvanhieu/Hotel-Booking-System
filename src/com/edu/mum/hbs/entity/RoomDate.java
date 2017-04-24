package com.edu.mum.hbs.entity;

import com.edu.mum.hbs.restapi.util.LocalDateWithStringsDeserializer;
import com.edu.mum.hbs.restapi.util.LocalDateWithStringsSerializable;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

public class RoomDate extends Room implements Prototype {
	@JsonSerialize(using = LocalDateWithStringsSerializable.class)
	@JsonDeserialize(using = LocalDateWithStringsDeserializer.class)
	private LocalDate checkInDate;
	@JsonSerialize(using = LocalDateWithStringsSerializable.class)
	@JsonDeserialize(using = LocalDateWithStringsDeserializer.class)
	private LocalDate checkOutDate;
	private Room room;

	public RoomDate(Room room){
		this(room, null, null);
	}

	public RoomDate(Room room, LocalDate checkInDate, LocalDate checkOutDate){
		this.room = room;
		this.room_number = this.room.getRoom_number();
		this.room_type = this.room.getRoom_type();
		this.room_class = this.room.getRoom_class();
		this.price = this.room.getPrice();
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

	@Override
	public Prototype doClone() {
		Room cloneRoom = (Room) this.room.doClone();
		RoomDate cloneRoomDate=new RoomDate(cloneRoom, checkInDate, checkOutDate);
		return cloneRoomDate;
	}
}
