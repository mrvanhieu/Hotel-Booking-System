package com.edu.mum.hbs.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.RoomService;
import com.edu.mum.hbs.util.SqliteUtil;

public class RoomServiceDao extends DaoAbstract<RoomService,String>{
	//private SqliteUtil db = new SqliteUtil();
	private static final String TABLE_NAME = RoomService.TABLE_NAME;
	private DaoAbstract crDao;
	
	RoomServiceDao() {
		// TODO Auto-generated constructor stub
		super(RoomService.class);
		DaoFactory factory = DaoFactoryImpl.getFactory();
		crDao = factory.createDao(CustomerAndRoom.TABLE_NAME);
	}
	
	public List<String> getUsedRooms(String status) {
		List<String> usedRooms = new ArrayList<>();
		SqliteUtil.FilterCondition condition = new SqliteUtil.FilterCondition();
	
		condition.addCondition(CustomerAndRoom.STATUS, SqliteUtil.EQUALS, status);
		List<CustomerAndRoom> objects = crDao.getAll(condition);
		if (objects.size() > 0) {
			for (CustomerAndRoom ob : objects ){
				usedRooms.add((String) ob.getRoomNumber());
			}
		}
		return usedRooms;
	}

	public List<RoomService> getAllRoomServices(){
		return getAll();
	}

	public void addRoomService(RoomService roomService) {
		add(roomService);
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
		SqliteUtil.FilterCondition condition = new SqliteUtil.FilterCondition();
		condition.addCondition(RoomService.ROOM_NUMBER, SqliteUtil.EQUALS, roomNumber);
		return getAll(condition);
	}
	
	public boolean delete(RoomService roomService){
		SqliteUtil.FilterCondition conditions = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.AND);
		conditions.addCondition(RoomService.ROOM_NUMBER, SqliteUtil.EQUALS, roomService.getRoomNumber());
		conditions.addCondition(RoomService.SERVICE_DESC, SqliteUtil.EQUALS, roomService.getServiceDesc());
		conditions.addCondition(RoomService.SERVICE_DATE, SqliteUtil.EQUALS, roomService.getServiceDate());
		
		return delete(null,conditions);
	}
	
	public boolean delete(String roomNumber){
		SqliteUtil.FilterCondition conditions = new SqliteUtil.FilterCondition();
		conditions.addCondition(RoomService.ROOM_NUMBER, SqliteUtil.EQUALS, roomNumber);
		return delete(null,conditions);
	}

	public void update(RoomService roomService){
		update(roomService);
	}

}
