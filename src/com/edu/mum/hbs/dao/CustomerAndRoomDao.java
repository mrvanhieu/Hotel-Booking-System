package com.edu.mum.hbs.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
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

public class CustomerAndRoomDao extends DaoAbstract<CustomerAndRoom,String> {
	private static final String TABLE_NAME = CustomerAndRoom.TABLE_NAME;
	CustomerAndRoomDao() {
		super(CustomerAndRoom.class);
	}
	public List<CustRoomDetails> getCustomerRoomFullFromToDate(String fromDate, String toDate){
		List<CustRoomDetails> customerAndRooms = new ArrayList<CustRoomDetails>();
		FilterCondition condition = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.AND);
		condition.addCondition(CustomerAndRoom.CHECK_IN_DATE, SqliteUtil.GREATER_EQUALS, fromDate);
		condition.addCondition(CustomerAndRoom.CHECK_OUT_DATE, SqliteUtil.LESS_EQUALS, toDate);
		List<CustomerAndRoom> objects = getAll(condition);

		if (objects.size() > 0) {
			for (CustomerAndRoom ob : objects ){
				String szPassport = ob.getPassportOrId();
				CustomerDao cDao = new CustomerDao();
				Customer customer = cDao.getCustomer(szPassport);
				
				String szRoomNo = (String) ob.getRoomNumber();
				RoomDao rDao = new RoomDao();
				Room room = rDao.getRoom(szRoomNo);

				CustRoomDetails cr = new CustRoomDetails(customer, room);
				cr.setStatus((String) ob.getStatus());
				cr.setCheckInDateByString(ob.getCheckInDate().toString());
				cr.setCheckOutDateByString(ob.getCheckOutDate().toString());
				
				customerAndRooms.add(cr);
			}
		}

		return customerAndRooms;
	}
	
	public List<CustomerAndRoom> getCustomerAndRoomFromToDate(String fromDate, String toDate){
		
		FilterCondition condition = new SqliteUtil.FilterCondition();
		condition.addCondition(CustomerAndRoom.CHECK_IN_DATE, SqliteUtil.GREATER_EQUALS, fromDate);
		condition.addCondition(CustomerAndRoom.CHECK_OUT_DATE, SqliteUtil.LESS_EQUALS, toDate);
		List<CustomerAndRoom> customerAndRooms = getAll(condition);

		return customerAndRooms;
	}
	
	public List<CustomerAndRoom> getCustomerAndRoom(String passportOrId, String status){
		FilterCondition condition = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.AND);
		condition.addCondition(CustomerAndRoom.PASSPORT_OR_ID, SqliteUtil.EQUALS, passportOrId);
		condition.addCondition(CustomerAndRoom.STATUS, SqliteUtil.EQUALS, status);
		List<CustomerAndRoom> customerAndRooms = getAll(condition);
	
		return customerAndRooms;
	}

	public List<String> getAllRoomNumbers(){
		
		List<String> roomNumbers = new ArrayList<String>();
		List<CustomerAndRoom> objects = getAll();

		if (objects.size() > 0) {
			for (CustomerAndRoom ob : objects ){
				roomNumbers.add((String) ob.getRoomNumber());
			}
		}
		return roomNumbers;
		
	}
	
	public List<CustomerAndRoom> getAllCustomerRoom(){
		
		return getAll();
	}
	
	public List<CustomerAndRoom> getAllCustomerRoom(String roomStatus){		
		FilterCondition condition = new SqliteUtil.FilterCondition();
		condition.addCondition(CustomerAndRoom.STATUS, SqliteUtil.EQUALS, roomStatus);
		List<CustomerAndRoom> allCustomerRooms = getAll(condition);
		
		return allCustomerRooms;
	}
	
	public void addCustomerAndRooms(String passportOrId, List<RoomDate> roomDates, String status){
		for (RoomDate roomdate : roomDates){
			CustomerAndRoom cr = new CustomerAndRoom();
			cr.setPassportOrId(passportOrId);
			cr.setRoomNumber(roomdate.getRoom_number());
			cr.setStatus(status);
			cr.setCheckInDate(roomdate.getCheckInDate());
			cr.setCheckOutDate(roomdate.getCheckOutDate());
			add(cr);
		}
	}

	public void update(String roomNumber, String status){		
		FilterCondition conditions = new FilterCondition();
		conditions.addCondition(CustomerAndRoom.ROOM_NUMBER, SqliteUtil.EQUALS,roomNumber);
		List<CustomerAndRoom> listCr = getAll(conditions);
		Iterator<CustomerAndRoom> it = listCr.iterator();
		
		if (it.hasNext()){
			CustomerAndRoom cr = it.next();
			cr.setStatus(status);
			update(cr);
		}
		
	}

	public  boolean delete(String passport, String roomNumber){
		FilterCondition conditions = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.AND);
		conditions.addCondition(CustomerAndRoom.PASSPORT_OR_ID, SqliteUtil.EQUALS, passport);
		conditions.addCondition(CustomerAndRoom.ROOM_NUMBER, SqliteUtil.EQUALS, roomNumber);		
		return delete(null,conditions);
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
