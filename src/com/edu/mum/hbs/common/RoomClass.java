package com.edu.mum.hbs.common;

public enum RoomClass {
	STANDARD("Standard"),
	VIP("VIP");
	
	private String value;
	
	private RoomClass(String roomClass){
		this.value = roomClass;
	}
	
	public String getValue(){
		return this.value;
	}
}
