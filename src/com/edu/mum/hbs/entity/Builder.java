package com.edu.mum.hbs.entity;

import java.time.LocalDate;

public interface Builder {
	public void buildPassportOrId(String passportOrId);

	public void buildRoomNumber(String roomNumber);

	public void buildCheckInDate(LocalDate checkInDate);

	public void buildCheckOutDate(LocalDate checkOutDate);

	public void buildRoomAmount(double roomAmount);

	public void buildServiceAmount(double serviceAmount);

	public void buildTotalAmount(double totalAmount);
}
