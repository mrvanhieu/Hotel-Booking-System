package com.edu.mum.hbs.entity;

import java.time.Period;

public class OccupancyVisitor implements Visitor {
	private int occupancyDays;
	@Override
	public void visit(Revenue profit) {
		// TODO Auto-generated method stub
		Period period = Period.between(profit.getCheckInDate(), profit.getCheckOutDate());
		occupancyDays += period.getDays();
	}
	
	public int getOccupancyDays() {
		return occupancyDays;
	}

}
