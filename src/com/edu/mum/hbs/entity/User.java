package com.edu.mum.hbs.entity;

public class User extends Person implements Entity{
	public static final String TABLE_NAME = "user";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String USER_ID = "user_id";
	@Id(Name=USER_ID)
	private Integer user_id;
	@Column(Name=USERNAME)
	private String username;
	@Column(Name=PASSWORD)
	private String password;
	
	public User(int user_id){
		this.user_id = user_id;
	}
	
	public User(){}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
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

	@Override
	public String tableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}
}
