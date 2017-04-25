package com.edu.mum.hbs.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.edu.mum.hbs.entity.Column;
import com.edu.mum.hbs.entity.Entity;
import com.edu.mum.hbs.entity.Id;
import com.edu.mum.hbs.util.SqliteUtil;
import com.edu.mum.hbs.util.SqliteUtil.FilterCondition;
import com.edu.mum.hbs.util.SqliteUtil.LogicalOperator;

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
	
	public List<T> getAll(FilterCondition... params){
		assert params.length <= 1;
		FilterCondition cond = null;
		if (params.length == 1){
			assert FilterCondition.class.isInstance(params[0]);
			cond = params[0];
		}
					
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
		List<Map<String, Object>> objects = db.get(tableName, null, cond);

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
	
	public List<T> getAllWithConditions(FilterCondition cond){
		
		return null;
		
	}
	
	public boolean delete(T t, FilterCondition... params){
		FilterCondition cond = null;
		assert params.length >= 1;
		assert params.length <= 2;
		
		if (params.length == 2){
			assert FilterCondition.class.isInstance(params[1]);
			cond = params[1];
		}
		else{
			cond = new FilterCondition(SqliteUtil.LogicalOperator.AND);
		}
		
		Entity item = (Entity)t;
		String tableName = item.tableName();
		if (bindColumns(null,t,cond))
			return db.delete(tableName, cond);
		return false;
		
	}
	
	public void update(T t){
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		FilterCondition cond = new FilterCondition(SqliteUtil.LogicalOperator.AND);
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
		if (t != null){	
		    for (Field field : t.getClass().getDeclaredFields()) {
		    	if (map != null){
			    	if (field.isAnnotationPresent(Column.class)||field.isAnnotationPresent(Id.class)){
			    		Method method = null;
			    		String colName = null;
			    		
			    		colName = getColName(field);
			    		
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
			    				map.put(colName,method.invoke(t));
							
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
			    		String colName = getColName(field);   		
			    		
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
			    			Object item = method.invoke(t);
			    			if (cond.getConditions().size() > 0 && cond.getOp() == null){
			    				cond.setOp(LogicalOperator.AND);
			    			}

			    			if (field.getType() == LocalDate.class)
			    				cond.addCondition(colName, SqliteUtil.EQUALS,item.toString());
			    			else
			    				cond.addCondition(colName, SqliteUtil.EQUALS,item);
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
		}
	    return success;
	}
	
	private String getColName(Field field){
		String colName = null;
		
		if (field.isAnnotationPresent(Column.class)){
			Column col = field.getAnnotation(Column.class);
    		if (!col.Name().isEmpty())
    			colName = col.Name();
    		else
    			colName = field.getName();
		}
		
		if (field.isAnnotationPresent(Id.class)){
			Id col = field.getAnnotation(Id.class);
    		if (!col.Name().isEmpty())
    			colName = col.Name();
    		else
    			colName = field.getName();
		}
		return colName;		
	}
	private String getIdColumnName(T t){
		for (Field field : t.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)){
	    		Id col = field.getAnnotation(Id.class);	    		
	    		String colName;
	    		if (!col.Name().isEmpty())
	    			colName = col.Name();
	    		else
	    			colName = field.getName();
				
	    		return colName;
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
		    		String colName = null;
		    		
		    		colName = getColName(field);

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
		    			Object ob = map.get(colName);
		    			Object param = null;
		    			if (ob != null){
		    				if (field.getType() == LocalDate.class){
			    				param = LocalDate.parse(ob.toString());
			    			}
			    			else{
			    				param = field.getType().cast(ob);
			    			}
		    			}   			
		    			method.invoke(t, param);						
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

