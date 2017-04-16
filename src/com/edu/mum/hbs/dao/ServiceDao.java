package com.edu.mum.hbs.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.edu.mum.hbs.entity.Service;
import com.edu.mum.hbs.util.SqliteUtil;

public class ServiceDao extends DaoAbstract{
	//private SqliteUtil db = new SqliteUtil();
	private static final String TABLE_NAME = Service.TABLE_NAME;

	ServiceDao() {
	}
	
	public List<Service> loadServices() {
		List<Service> services = new ArrayList<Service>();
		List<Map<String, Object>> objects = db.get(TABLE_NAME, null, null);

		if (objects.size() > 0) {
			for (Map<String, Object> ob : objects ){
				Service service = new Service();
				service.setServiceDesc((String) ob.get("service_desc"));
				service.setServicePrice((double) ob.get("service_price"));
				services.add(service);
			}
		}
		return services;
	}

	public  boolean delete(Service service){
		SqliteUtil.FilterCondition condition = new SqliteUtil.FilterCondition();
		condition.addCondition(Service.SERVICE_DESC, SqliteUtil.EQUALS, service.getServiceDesc());
		return db.delete(TABLE_NAME, condition);
	}

	public Service getService(String serviceDesc) {
		Service service = null;
		SqliteUtil.FilterCondition condition = new SqliteUtil.FilterCondition();
		condition.addCondition(Service.SERVICE_DESC, SqliteUtil.EQUALS, serviceDesc);
		List<Map<String, Object>> rawServices = db.get(TABLE_NAME, null, condition);

		if (rawServices.size() > 0) {
			Map<String, Object> rawService = rawServices.get(0);

			service = new Service();
			service.setServiceDesc(serviceDesc);
			service.setServicePrice((Double) rawService.get(Service.SERVICE_PRICE));
		}

		return service;
	}

	public void update(Service service){
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put(Service.SERVICE_PRICE, service.getServicePrice());
		SqliteUtil.FilterCondition conditions = new SqliteUtil.FilterCondition();
		conditions.addCondition(Service.SERVICE_DESC, SqliteUtil.EQUALS,service.getServicePrice());
		db.update(TABLE_NAME,map, conditions);

	}
	//Load all services' description. 
	//Noted: Service Desc is primary key of Service table
	public List<String> loadServicesDesc() {
		List<String> serviceDesc = new ArrayList<>();
		List<Map<String, Object>> objects = db.get(TABLE_NAME, null, null);
		if (objects.size() > 0) {
			for (Map<String, Object> ob : objects ){
				serviceDesc.add((String) ob.get(Service.SERVICE_DESC));
			}
		}
		return serviceDesc;
	}

	public void addService(Service service) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put(Service.SERVICE_DESC, service.getServiceDesc());
		map.put(Service.SERVICE_PRICE, service.getServicePrice());
		db.insertRow(TABLE_NAME, map, false);
	}
	
}
