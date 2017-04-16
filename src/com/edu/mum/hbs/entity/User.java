package com.edu.mum.hbs.entity;

public class User extends Person{
	public static final String TABLE_NAME = "user";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String USER_ID = "user_id";
	private int user_id;
	private String username;
	private String password;
	
	public User(int user_id){
		this.user_id = user_id;
	}
	
	public User(){}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
