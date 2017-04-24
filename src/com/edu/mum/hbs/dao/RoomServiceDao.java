package com.edu.mum.hbs.dao;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.RoomService;
import com.edu.mum.hbs.entity.Service;
import com.edu.mum.hbs.util.SqliteUtil;

public class RoomServiceDao extends DaoAbstract<RoomService,String>{
	//private SqliteUtil db = new SqliteUtil();
	private static final String TABLE_NAME = RoomService.TABLE_NAME;
	
	RoomServiceDao() {
		// TODO Auto-generated constructor stub
		super(RoomService.class);
	}
	
	public List<String> getUsedRooms(String status) {
		List<String> usedRooms = new ArrayList<>();
		SqliteUtil.FilterCondition condition = new SqliteUtil.FilterCondition();
		condition.addCondition(CustomerAndRoom.STATUS, SqliteUtil.EQUALS, status);
		List<Map<String, Object>> objects = db.get(CustomerAndRoom.TABLE_NAME, null, condition);
		if (objects.size() > 0) {
			for (Map<String, Object> ob : objects ){
				usedRooms.add((String) ob.get(CustomerAndRoom.ROOM_NUMBER));
			}
		}
		return usedRooms;
	}

	public List<RoomService> getAllRoomServices(){
		List<RoomService> roomServices = new ArrayList<RoomService>();
		List<Map<String, Object>> objects = db.get(TABLE_NAME, null, null);

		if (objects.size() > 0) {
			for (Map<String, Object> ob : objects ){
				RoomService roomService = new RoomService();
				roomService.setRoomNumber((String) ob.get(RoomService.ROOM_NUMBER));
				roomService.setServiceDesc((String) ob.get(RoomService.SERVICE_DESC));
				roomService.setQuantity((Integer) ob.get(RoomService.QUANTITY));
				//System.out.println(LocalDate.parse((String)ob.get(RoomService.SERVICE_DATE)));
				roomService.setServiceDateByString((String) ob.get(RoomService.SERVICE_DATE));

				roomServices.add(roomService);
			}
		}
		return roomServices;
	}

	public void addRoomService(RoomService roomService) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put(RoomService.ROOM_NUMBER, roomService.getRoomNumber());
		map.put(RoomService.SERVICE_DESC, roomService.getServiceDesc());
		map.put(RoomService.QUANTITY, roomService.getQuantity());
		map.put(RoomService.SERVICE_DATE, roomService.getServiceDate());
		db.insertRow(TABLE_NAME, map, false);
	}
	
	public double getTotalUsingService(String roomNumber) { 
		List<RoomService> roomServices = getAllRoomService(roomNumber);
		double usedServices = 0.0;
		for (RoomService roomService : roomServices) {
			usedServices += roomService.getServiceAmount();
		}
		return usedServices;
	}
	
	public List<RoomService> getAllRoomService(String roomNumber){
		List<RoomService> roomServices = new ArrayList<>();
		SqliteUtil.FilterCondition condition = new SqliteUtil.FilterCondition();
		condition.addCondition(RoomService.ROOM_NUMBER, SqliteUtil.EQUALS, roomNumber);
		List<Map<String, Object>> objects = db.get(TABLE_NAME, null, condition);
		if (objects.size() > 0){
			for (Map<String, Object> ob : objects){
				RoomService roomService = new RoomService();
				roomService.setRoomNumber((String) ob.get(RoomService.ROOM_NUMBER));
				roomService.setServiceDesc((String) ob.get(RoomService.SERVICE_DESC));
				roomService.setServiceDateByString((String) ob.get(RoomService.SERVICE_DATE));
				roomService.setQuantity((int) ob.get(RoomService.QUANTITY));
				roomServices.add(roomService);
			}
		}
		return roomServices;
	}
	
	public boolean delete(RoomService roomService){
		SqliteUtil.FilterCondition conditions = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.AND);
		conditions.addCondition(RoomService.ROOM_NUMBER, SqliteUtil.EQUALS, roomService.getRoomNumber());
		conditions.addCondition(RoomService.SERVICE_DESC, SqliteUtil.EQUALS, roomService.getServiceDesc());
		conditions.addCondition(RoomService.SERVICE_DATE, SqliteUtil.EQUALS, roomService.getServiceDate());
		
		return db.delete(TABLE_NAME, conditions);
	}
	
	public boolean delete(String roomNumber){
		SqliteUtil.FilterCondition conditions = new SqliteUtil.FilterCondition();
		conditions.addCondition(RoomService.ROOM_NUMBER, SqliteUtil.EQUALS, roomNumber);
		return db.delete(TABLE_NAME, conditions);
	}

	public void update(RoomService roomService){
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put(RoomService.QUANTITY, roomService.getQuantity());
		SqliteUtil.FilterCondition conditions = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.AND);
		conditions.addCondition(RoomService.ROOM_NUMBER, SqliteUtil.EQUALS, roomService.getRoomNumber());
		conditions.addCondition(RoomService.SERVICE_DESC, SqliteUtil.EQUALS, roomService.getServiceDesc());
		conditions.addCondition(RoomService.SERVICE_DATE, SqliteUtil.EQUALS, roomService.getServiceDate());
		db.update(TABLE_NAME, map, conditions);
	}

}
