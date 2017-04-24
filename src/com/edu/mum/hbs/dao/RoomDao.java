package com.edu.mum.hbs.dao;

import java.util.List;

import com.edu.mum.hbs.entity.Room;

public class RoomDao extends DaoAbstract<Room,String> {
	//private SqliteUtil db = new SqliteUtil();
	private static final String TABLE_NAME = "Room";

	RoomDao(){
		super(Room.class);
	}

	public Room getRoom(String roomNumber) {
		return this.getFromId(roomNumber);
	}
	
	public List<Room> getAllRooms(){
		return getAll();
	}
	
	public List<Room> getAvailableRooms() {
		CustomerAndRoomDao cusRoomDao = new CustomerAndRoomDao();
		//List<String> roomNumbers = cusRoomDao.getAllRoomNumbers();
		List<String> roomNumbers = cusRoomDao.getAllRoomNumbers();
		String value = "";
		for (String str : roomNumbers) {
			value +=str + ",";
		}

		value = value.substring(0, value.length() - 1);
		List<Room> rooms = getAllRooms();
		for (int i = 0; i < rooms.size();){
			if (value.contains(rooms.get(i).getRoom_number())){
				rooms.remove(i);
			}else{
				i++;
			}
		}
		return rooms;
	}
	
	public void addRoom(Room room) {
		add(room);
	}

	public void update(Room room){
		update(room);

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
