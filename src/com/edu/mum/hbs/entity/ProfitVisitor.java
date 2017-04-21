package com.edu.mum.hbs.entity;

public class ProfitVisitor implements Visitor{
	private double totalProfit = 0;
	@Override
	public void visit(Revenue profit) {
		// TODO Auto-generated method stub
		totalProfit += profit.getTotalAmount();
	}
	public double getTotalProfit() {
		return totalProfit;
	}

}
