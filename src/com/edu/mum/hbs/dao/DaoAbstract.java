package com.edu.mum.hbs.dao;

import java.util.List;

import com.edu.mum.hbs.util.SqliteUtil;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;

public abstract class DaoAbstract {
	// Singleton design pattern
	//protected SqliteUtil db = new SqliteUtil();
	StatementVisitor visitor;
	protected SqliteUtil db = SqliteUtil.getInstance();
	public void setQuery(String query){
		try {
			Statement stmt = CCJSqlParserUtil.parse(query);
			visitor = new StatementVisitorImpl();
			stmt.accept(visitor);
		} catch (JSQLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setParams(List<Object> params){
		
	}
}

