package com.edu.mum.hbs.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.restapi.RestAdapter;
import com.edu.mum.hbs.util.SqliteUtil;
import com.edu.mum.hbs.util.SqliteUtil.FilterCondition;

public class RoomDao extends DaoAbstract {
	//private SqliteUtil db = new SqliteUtil();
	private RestAdapter adapter = (RestAdapter) RestAdapter.getInstance();
	private static final String TABLE_NAME = "Room";

	RoomDao(){}

	public Room getRoom(String roomNumber) {
		Room room = null;
		FilterCondition condition = new SqliteUtil.FilterCondition();
		condition.addCondition("room_number", SqliteUtil.EQUALS, roomNumber);
		List<Map<String, Object>> rawRooms = db.get(TABLE_NAME, null, condition);

		if (rawRooms.size() > 0) {
			Map<String, Object> rawRoom = rawRooms.get(0);

			room = new Room();
			room.setRoomNumber(roomNumber);
			room.setRoomType((String) rawRoom.get("room_type"));
			room.setRoomClass((String) rawRoom.get("room_class"));
			room.setRoomPrice((double) rawRoom.get("price"));
		}

		return room;
	}
	
	public List<Room> getAllRooms(){
		List<Room> rooms = new ArrayList<Room>();
		List<Map<String, Object>> objects = db.get(TABLE_NAME, null, null);

		if (objects.size() > 0) {
			for (Map<String, Object> ob : objects ){
				Room room = new Room();
				room.setRoomNumber((String) ob.get("room_number"));
				room.setRoomType((String) ob.get("room_type"));
				room.setRoomClass((String) ob.get("room_class"));
				room.setRoomPrice((double) ob.get("price"));
				rooms.add(room);
			}
		}
		return rooms;
	}
	
	public List<Room> getAvailableRooms() {
		CustomerAndRoomDao cusRoomDao = new CustomerAndRoomDao();
		//List<String> roomNumbers = cusRoomDao.getAllRoomNumbers();
		List<String> roomNumbers = adapter.getAllRoomNumbers();
		String value = "";
		for (String str : roomNumbers) {
			value +=str + ",";
		}

		value = value.substring(0, value.length() - 1);
		List<Room> rooms = getAllRooms();
		for (int i = 0; i < rooms.size();){
			if (value.contains(rooms.get(i).getRoomNumber())){
				rooms.remove(i);
			}else{
				i++;
			}
		}
		return rooms;
	}
	
	public void addRoom(Room room) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("room_number", room.getRoomNumber());
		map.put("room_type", room.getRoomType());
		map.put("room_class", room.getRoomClass());
		map.put("price", room.getRoomPrice());
		db.insertRow(TABLE_NAME, map, false);
	}

	public boolean delete(Room room){
		FilterCondition condition = new SqliteUtil.FilterCondition();
		condition.addCondition("room_number", SqliteUtil.EQUALS, room.getRoomNumber());
		return db.delete(TABLE_NAME, condition);
	}

	public void update(Room room){
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("room_type", room.getRoomType());
		map.put("room_class", room.getRoomClass());
		map.put("price", room.getRoomPrice());
		FilterCondition conditions = new FilterCondition();
		conditions.addCondition("room_number", SqliteUtil.EQUALS,room.getRoomNumber());
		db.update(TABLE_NAME,map, conditions);

	}
	public static void main(String[] args) {
		/*
		 * Room room = new Room(); room.setRoomPrice(10.0);
		 * room.setRoomClass("Standard"); room.setRoomNumber("M106");
		 * room.setRoomType("Double");
		 */
		RoomDao roomDao = new RoomDao();
		// roomDao.addRoom(room);
		roomDao.getAvailableRooms();

	}
}
