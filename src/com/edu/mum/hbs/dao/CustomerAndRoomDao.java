package com.edu.mum.hbs.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.edu.mum.hbs.entity.CustRoomDetails;
import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.entity.RoomDate;
import com.edu.mum.hbs.util.SqliteUtil;
import com.edu.mum.hbs.util.SqliteUtil.FilterCondition;

public class CustomerAndRoomDao extends DaoAbstract {
	private static final String TABLE_NAME = CustomerAndRoom.TABLE_NAME;
	CustomerAndRoomDao() {
	}
	public List<CustRoomDetails> getCustomerRoomFullFromToDate(String fromDate, String toDate){
		List<CustRoomDetails> customerAndRooms = new ArrayList<CustRoomDetails>();
		FilterCondition condition = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.AND);
		condition.addCondition(CustomerAndRoom.CHECK_IN_DATE, SqliteUtil.GREATER_EQUALS, fromDate);
		condition.addCondition(CustomerAndRoom.CHECK_OUT_DATE, SqliteUtil.LESS_EQUALS, toDate);
		List<Map<String, Object>> objects = db.get(TABLE_NAME, null, condition);

		if (objects.size() > 0) {
			for (Map<String, Object> ob : objects ){
				String szPassport = (String) ob.get(CustomerAndRoom.PASSPORT_OR_ID);
				CustomerDao cDao = new CustomerDao();
				Customer customer = cDao.getCustomer(szPassport);
				
				String szRoomNo = (String) ob.get(CustomerAndRoom.ROOM_NUMBER);
				RoomDao rDao = new RoomDao();
				Room room = rDao.getRoom(szRoomNo);

				CustRoomDetails cr = new CustRoomDetails(customer, room);
				cr.setStatus((String) ob.get(CustomerAndRoom.STATUS));
				cr.setCheckInDateByString((String) ob.get(CustomerAndRoom.CHECK_IN_DATE));
				cr.setCheckOutDateByString((String) ob.get(CustomerAndRoom.CHECK_OUT_DATE));
				
				customerAndRooms.add(cr);
			}
		}

		return customerAndRooms;
	}
	
	public List<CustomerAndRoom> getCustomerAndRoomFromToDate(String fromDate, String toDate){
		List<CustomerAndRoom> customerAndRooms = new ArrayList<CustomerAndRoom>();
		FilterCondition condition = new SqliteUtil.FilterCondition();
		condition.addCondition(CustomerAndRoom.CHECK_IN_DATE, SqliteUtil.GREATER_EQUALS, fromDate);
		condition.addCondition(CustomerAndRoom.CHECK_OUT_DATE, SqliteUtil.LESS_EQUALS, toDate);
		List<Map<String, Object>> objects = db.get(TABLE_NAME, null, condition);
		
		if (objects.size() > 0) {
			buildListCustomerAndRoom(objects, customerAndRooms);
		}
		
		return customerAndRooms;
	}
	
	public List<CustomerAndRoom> getCustomerAndRoom(String passportOrId, String status){
		List<CustomerAndRoom> customerAndRooms = new ArrayList<CustomerAndRoom>();
		FilterCondition condition = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.AND);
		condition.addCondition(CustomerAndRoom.PASSPORT_OR_ID, SqliteUtil.EQUALS, passportOrId);
		condition.addCondition(CustomerAndRoom.STATUS, SqliteUtil.EQUALS, status);
		List<Map<String, Object>> objects = db.get(TABLE_NAME, null, condition);
		
		if (objects.size() > 0) {
			buildListCustomerAndRoom(objects, customerAndRooms);
		}
		
		return customerAndRooms;
	}

	private void buildListCustomerAndRoom(List<Map<String, Object>> objects, List<CustomerAndRoom> customerAndRooms) {
		for (Map<String, Object> ob : objects ){
			CustomerAndRoom cr = new CustomerAndRoom();
			cr.setPassportOrId((String) ob.get(CustomerAndRoom.PASSPORT_OR_ID));
			cr.setRoomNumber((String) ob.get(CustomerAndRoom.ROOM_NUMBER));
			cr.setStatus((String) ob.get(CustomerAndRoom.STATUS));
			cr.setCheckInDateByString((String) ob.get(CustomerAndRoom.CHECK_IN_DATE));
			cr.setCheckOutDateByString((String) ob.get(CustomerAndRoom.CHECK_OUT_DATE));
			customerAndRooms.add(cr);
		}
	}
	
	public List<String> getAllRoomNumbers(){
		
		List<String> roomNumbers = new ArrayList<String>();
		List<Map<String, Object>> objects = db.get(TABLE_NAME, null, null);

		if (objects.size() > 0) {
			for (Map<String, Object> ob : objects ){
				roomNumbers.add((String) ob.get(CustomerAndRoom.ROOM_NUMBER));
			}
		}
		return roomNumbers;
		
	}
	
	public List<CustomerAndRoom> getAllCustomerRoom(){
		
		List<CustomerAndRoom> allCustomerRooms = new ArrayList<CustomerAndRoom>();
		List<Map<String, Object>> objects = db.get(TABLE_NAME, null, null);
		
		if (objects.size() > 0) {
			buildListCustomerAndRoom(objects, allCustomerRooms);
		}
		
		return allCustomerRooms;
	}
	
	public List<CustomerAndRoom> getAllCustomerRoom(String roomStatus){
		
		List<CustomerAndRoom> allCustomerRooms = new ArrayList<CustomerAndRoom>();
		FilterCondition condition = new SqliteUtil.FilterCondition();
		condition.addCondition(CustomerAndRoom.STATUS, SqliteUtil.EQUALS, roomStatus);
		List<Map<String, Object>> objects = db.get(TABLE_NAME, null, condition);
		
		if (objects.size() > 0) {
			buildListCustomerAndRoom(objects, allCustomerRooms);
		}
		
		return allCustomerRooms;
	}
	
	public void addCustomerAndRooms(String passportOrId, List<RoomDate> roomDates, String status){
		for (RoomDate roomdate : roomDates){
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
	    	map.put(CustomerAndRoom.PASSPORT_OR_ID, passportOrId);
	    	map.put(CustomerAndRoom.ROOM_NUMBER, roomdate.getRoomNumber());
	    	map.put(CustomerAndRoom.STATUS, status);
	    	map.put(CustomerAndRoom.CHECK_IN_DATE, roomdate.getCheckInDate());
	    	map.put(CustomerAndRoom.CHECK_OUT_DATE,roomdate.getCheckOutDate() );
	    	db.insertRow(TABLE_NAME, map,false);
		}
	}

	public void update(String roomNumber, String status){
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put(CustomerAndRoom.STATUS, status);
		FilterCondition conditions = new FilterCondition();
		conditions.addCondition(CustomerAndRoom.ROOM_NUMBER, SqliteUtil.EQUALS,roomNumber);
		db.update(TABLE_NAME,map, conditions);
	}

	public  boolean delete(String passport, String roomNumber){
		FilterCondition conditions = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.AND);
		conditions.addCondition(CustomerAndRoom.PASSPORT_OR_ID, SqliteUtil.EQUALS, passport);
		conditions.addCondition(CustomerAndRoom.ROOM_NUMBER, SqliteUtil.EQUALS, roomNumber);
		return db.delete(TABLE_NAME, conditions);
	}

	public static void main(String[] args){
		List<CustomerAndRoom> rooms = new ArrayList<CustomerAndRoom>();
		String passportOrId = "B87200";
		String status = "Booking";
		rooms.add(new CustomerAndRoom("M123", passportOrId, status, LocalDate.now(), LocalDate.now()));
		rooms.add(new CustomerAndRoom("M323", passportOrId, status, LocalDate.now(), LocalDate.now()));
		rooms.add(new CustomerAndRoom("M423", passportOrId, status, LocalDate.now(), LocalDate.now()));
	}
}
