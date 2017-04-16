package com.edu.mum.hbs.common;

public enum RoomType {
	SINGLE("Single Room"),
	DOUBLE("Double Room"),
	FAMILY("Family Room");
	
	private String value;
	private RoomType(String roomType){
		this.value = roomType;
	}
	
	public String getValue(){
		return value;
	}
}
