package com.edu.mum.hbs.restapi;

import java.util.List;

import com.edu.mum.hbs.dao.UserSession;
import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.Service;

public interface IRestAdapter {

	UserSession authenticate(String userName, String password);

	List<Service> loadServices();

	Service getService(String serviceId);

	List<String> loadServicesDesc();

	void updateService(Service service);

	void addService(Service service);

	boolean deleteService(Service service);
	
	//Customer rest apis
	void addCustomer(Customer service);
	Customer getCustomer(String customerId);
	Customer getCustomerFromPassportOrPhone(String customerIdorPhone);
	
	//Invoice rest apis
	void addInvoice(InvoiceRecord invoice);

}
