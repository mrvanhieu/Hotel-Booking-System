package com.edu.mum.hbs.entity;

import java.util.List;

public class VIPStrategy implements Strategy{

	@Override
	public double getRoomServiceAmount(List<RoomService> roomServices) {
		double amount = 0;
		for (RoomService roomService : roomServices) {
			// for VIP, Service price charge to 200% for better quality
			amount += 2 * roomService.getServiceAmount();
		}
		return amount;
	}

}
