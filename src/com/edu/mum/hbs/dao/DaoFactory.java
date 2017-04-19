package com.edu.mum.hbs.dao;

public interface DaoFactory {
	public  DaoAbstract createDao(String tableName);
}
