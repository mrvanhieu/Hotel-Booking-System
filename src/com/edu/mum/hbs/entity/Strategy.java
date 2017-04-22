package com.edu.mum.hbs.entity;

import java.util.List;

public interface Strategy {
	public double getRoomServiceAmount(List<RoomService> roomServices);
}