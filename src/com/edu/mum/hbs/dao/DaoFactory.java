package com.edu.mum.hbs.dao;

import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.entity.RoomService;
import com.edu.mum.hbs.entity.Service;

public class DaoFactory {
	
	public static DaoAbstract getDaoFactory(String tableName){
		if (tableName.equals(Room.TABLE_NAME)){
			return new RoomDao();
		}
		else if (tableName.equals(RoomService.TABLE_NAME)){
			return new RoomServiceDao();
		}
		else if (tableName.equals(Service.TABLE_NAME)){
			return new ServiceDao();
		}
		else if (tableName.equals("user")){
			return new LoginDao();
		}
		else if (tableName.equals(InvoiceRecord.TABLE_NAME)){
			return new InvoiceRecordDao();
		}
		else if (tableName.equals(CustomerAndRoom.TABLE_NAME)){
			return new CustomerAndRoomDao();
		}
		else if (tableName.equals(Customer.TABLE_NAME)){
			return new CustomerDao();
		}
		return null;
	}
}
