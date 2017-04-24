package com.edu.mum.hbs.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.edu.mum.hbs.entity.Column;
import com.edu.mum.hbs.entity.Entity;
import com.edu.mum.hbs.entity.Id;
import com.edu.mum.hbs.util.SqliteUtil;
import com.edu.mum.hbs.util.SqliteUtil.FilterCondition;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;

public abstract class DaoAbstract<T, K> {
	// Singleton design pattern
	//protected SqliteUtil db = new SqliteUtil();
	private Class<T> domainClass;
	final private String getPrefix = "get";
	final private String setPrefix = "set";
	StatementVisitor visitor;
	protected SqliteUtil db = SqliteUtil.getInstance();
	public DaoAbstract(Class<T> domainClass){
		this.domainClass = domainClass;
	}
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
	
	public T getFromId(K key){
		T ret = null;
		T t = null;
		try {
			t = domainClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String idColumnName = getIdColumnName(t);
		String tableName = ((Entity)t).tableName();		
		FilterCondition condition = new SqliteUtil.FilterCondition();
		condition.addCondition(idColumnName, SqliteUtil.EQUALS, key);
		List<Map<String, Object>> rawRooms = db.get(tableName, null, condition);
		
		if (rawRooms.size() > 0) {
			Map<String, Object> rawRoom = rawRooms.get(0);
			
			if (getColumnsData(t,rawRoom))
				ret = t;
		}
		
		return ret;		
	}
	
	public List<T> getAll(){
		
		List<T> entities = new ArrayList<T>();
		T t = null;
		try {
			t = domainClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String tableName = ((Entity)t).tableName();
		List<Map<String, Object>> objects = db.get(tableName, null, null);

		if (objects.size() > 0) {
			for (Map<String, Object> ob : objects ){
				T entity = null;
				try {
					entity = domainClass.newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (getColumnsData(entity,ob))
					entities.add(entity);
			}			
		}
		return entities;
	}
	
	public boolean delete(T t){
		FilterCondition cond = new FilterCondition();
		Entity item = (Entity)t;
		String tableName = item.tableName();
		if (bindColumns(null,t,cond))
			return db.delete(tableName, cond);
		return false;
		
	}
	
	public void update(T t){
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		FilterCondition cond = new FilterCondition();
		Entity item = (Entity)t;
		String tableName = item.tableName();
		if (bindColumns(map,t,cond))
			db.update(tableName, map, cond);
	}
	
	public void add(T t){
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		Entity item = (Entity)t;
		String tableName = item.tableName();
		if (bindColumns(map,t,null))
			db.insertRow(tableName, map, false);
	    
	}
	
	private String getMethodName(String fieldName){
		return getPrefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
	
	private String setMethodName(String fieldName){
		return setPrefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
	
	private boolean bindColumns(LinkedHashMap<String, Object> map,T t, FilterCondition cond){
		boolean success = true;
	    for (Field field : t.getClass().getDeclaredFields()) {
	    	if (map != null){
		    	if (field.isAnnotationPresent(Column.class)){
		    		Method method = null;
					try {
						method = t.getClass().getMethod(getMethodName(field.getName()));						
					} catch (NoSuchMethodException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						success = false;
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						success = false;
					}
		    		try {
						map.put(field.getName(),method.invoke(t));
						
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						success = false;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						success = false;
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						success = false;
					}
		    	}
	    	}
			if (cond != null)
			{
				if (field.isAnnotationPresent(Id.class)){
					Method method = null;
					try {
						method = t.getClass().getMethod(getMethodName(field.getName()));
					} catch (NoSuchMethodException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						success = false;
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						success = false;
					}
		    		try {
		    			cond.addCondition(field.getName(), SqliteUtil.EQUALS,method.invoke(t));
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						success = false;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						success = false;
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						success = false;
					}	    		
				}
			}
		}
	    return success;
	}
	
	private String getIdColumnName(T t){
		for (Field field : t.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)){
				Method method = null;
				try {
					method = t.getClass().getMethod(getMethodName(field.getName()));
				} catch (NoSuchMethodException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();					
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();					
				}
	    		try {
	    			return (String)method.invoke(t);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
			}
		}
		return null;
	}
	
	private boolean getColumnsData(T t,Map<String,Object> map){
		boolean success = true;
		for (Field field : t.getClass().getDeclaredFields()) {
	    	if (map != null){
		    	if (field.isAnnotationPresent(Column.class)||field.isAnnotationPresent(Id.class) ){
		    		Method method = null;
					try {
						method = t.getClass().getMethod(setMethodName(field.getName()), field.getType());						
					} catch (NoSuchMethodException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						success = false;
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						success = false;
					}
		    		try {
		    			Object ob = map.get(field.getName());	    		
						method.invoke(t, field.getType().cast(ob) );						
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						success = false;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						success = false;
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						success = false;
					}
		    	}
	    	}
		}
		return success;
	}
}

