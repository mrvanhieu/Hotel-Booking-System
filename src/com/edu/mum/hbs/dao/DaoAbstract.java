package com.edu.mum.hbs.dao;

import com.edu.mum.hbs.util.SqliteUtil;

public abstract class DaoAbstract {
	// Singleton design pattern
	//protected SqliteUtil db = new SqliteUtil();
	protected SqliteUtil db = SqliteUtil.getInstance();
}

