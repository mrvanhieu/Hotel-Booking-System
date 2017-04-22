package com.edu.mum.hbs.entity;

import java.util.List;

public class StandardStrategy implements Strategy {

	@Override
	public double getRoomServiceAmount(List<RoomService> roomServices) {
		double amount = 0;
		for (RoomService roomService : roomServices) {
			amount += roomService.getServiceAmount();
		}
		return amount;
	}

}