package com.edu.mum.hbs.dao;

import java.util.ArrayList;
import java.util.List;

import com.edu.mum.hbs.entity.Service;

public class ServiceDao extends DaoAbstract<Service,String>{
	//private SqliteUtil db = new SqliteUtil();
//	private static final String TABLE_NAME = Service.TABLE_NAME;

	ServiceDao() {
		super(Service.class);
	}
	
	public List<Service> loadServices() {
		return getAll();
	}

	public Service getService(String serviceDesc) {
		return this.getFromId(serviceDesc);
	}

	//Load all services' description. 
	//Noted: Service Desc is primary key of Service table
	public List<String> loadServicesDesc() {
		List<String> serviceDesc = new ArrayList<>();
		List<Service> objects = getAll();
		if (objects.size() > 0) {
			for (Service ob : objects ){
				serviceDesc.add((String) ob.getService_desc());
			}
		}
		return serviceDesc;
	}

	public void addService(Service service) {
		add(service);
	}
	
}
