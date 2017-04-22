package com.edu.mum.hbs.entity;

import java.util.List;

public class StrategyContext {
	private Strategy strategy;

	public StrategyContext(Strategy strategy) {
		this.strategy = strategy;
	}

	public double getRoomServiceAmount(List<RoomService> roomServices) {
		return strategy.getRoomServiceAmount(roomServices);
	}

}