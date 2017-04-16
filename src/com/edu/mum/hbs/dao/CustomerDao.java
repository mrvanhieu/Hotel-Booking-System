package com.edu.mum.hbs.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.util.SqliteUtil;

public class CustomerDao extends DaoAbstract {
	//private SqliteUtil db = new SqliteUtil();
	private static final String TABLE_NAME = "Customer";
	
	CustomerDao() {
	}
	public Customer getCustomerFromPassportOrPhone(String passportOrPhone) {
		Customer customer = null;
		SqliteUtil.FilterCondition conditions = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.OR);
		conditions.addCondition(Customer.PASSPORT_OR_ID, SqliteUtil.EQUALS, passportOrPhone);
		conditions.addCondition(Customer.PHONE_NUMBER, SqliteUtil.EQUALS, passportOrPhone);
		List<Map<String, Object>> data = db.get(TABLE_NAME, null, conditions);
		
		customer = getOneCustomer(data);
		
		return customer;
	}
	
	public Customer getCustomer(String passportOrId) {
		Customer customer = null;
		SqliteUtil.FilterCondition condition = new SqliteUtil.FilterCondition();
		condition.addCondition(Customer.PASSPORT_OR_ID, SqliteUtil.LIKE, passportOrId);
		List<Map<String, Object>> rawPassportOrIds = db.get(TABLE_NAME, null, condition);

		customer = getOneCustomer(rawPassportOrIds);

		return customer;
	}

	private Customer getOneCustomer(List<Map<String, Object>> data){
		Customer customer = null;
		if (data.size() > 0) {
			Map<String, Object> rawCustomer = data.get(0);
			customer = new Customer();
			customer.setFullName((String) rawCustomer.get(Customer.FULL_NAME));
			customer.setPassportOrId((String) rawCustomer.get(Customer.PASSPORT_OR_ID));
			customer.setPhoneNo((String) rawCustomer.get(Customer.PHONE_NUMBER));
			customer.setAddress((String) rawCustomer.get(Customer.ADDRESS));
		}

		return customer;
	}
	
	public void addCustomer(Customer customer){
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
    	map.put(Customer.FULL_NAME, customer.getFullName());
    	map.put(Customer.PASSPORT_OR_ID, customer.getPassportOrId());
    	map.put(Customer.PHONE_NUMBER, customer.getPhoneNo());
    	map.put(Customer.ADDRESS, customer.getAddress());
    	db.insertRow(TABLE_NAME, map,false);
	}
	
	public static void main(String[] args){
		Customer customer = new Customer();
    	customer.setPassportOrId("B852007");
    	customer.setFullName("Nguyen Minh Sang");
    	customer.setPhoneNo("01216270039");
    	customer.setAddress("1000 N 4th St, IA, 52557");
    	CustomerDao customerDao = new CustomerDao();
    	//customerDao.addCustomer(customer);
    	customerDao.getCustomer("B852007");
	}
}
