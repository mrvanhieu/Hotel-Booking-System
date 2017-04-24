package com.edu.mum.hbs.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.util.SqliteUtil;

public class CustomerDao extends DaoAbstract<Customer, String> {
	//private SqliteUtil db = new SqliteUtil();
	private static final String TABLE_NAME = "Customer";
	
	CustomerDao() {
		super(Customer.class);
	}
	public Customer getCustomerFromPassportOrPhone(String passportOrPhone) {
		Customer customer = null;
		SqliteUtil.FilterCondition conditions = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.OR);
		conditions.addCondition(Customer.PASSPORT_OR_ID, SqliteUtil.EQUALS, passportOrPhone);
		conditions.addCondition(Customer.PHONE_NUMBER, SqliteUtil.EQUALS, passportOrPhone);
		List<Customer> customers = getAll(conditions);
		
		if (customers.size() > 0)
			customer = customers.get(0);
		
		return customer;
	}
	
	public Customer getCustomer(String passportOrId) {
		return this.getFromId(passportOrId);
	}


	
	public void addCustomer(Customer customer){
		add(customer);
	}
	
	public static void main(String[] args){
		Customer customer = new Customer();
    	customer.setPassport_id("B852007");
    	customer.setFullName("Nguyen Minh Sang");
    	customer.setPhoneNo("01216270039");
    	customer.setAddress("1000 N 4th St, IA, 52557");
    	CustomerDao customerDao = new CustomerDao();
    	//customerDao.addCustomer(customer);
    	customerDao.getCustomer("B852007");
	}
}
