package com.edu.mum.hbs.dao;

import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.entity.RoomService;
import com.edu.mum.hbs.entity.Service;

public class DaoFactoryImpl implements DaoFactory{
	private static DaoFactory factory = new DaoFactoryImpl();
	
	private DaoFactoryImpl(){		
	}
	
	public static DaoFactory getFactory(){
		return factory;
	}
	@Override
	public DaoAbstract createDao(String tableName) {
		// TODO Auto-generated method stub
		DaoAbstract result = null;
		if (tableName.equals(Room.TABLE_NAME)){
			result = new RoomDao();
		}
		else if (tableName.equals(RoomService.TABLE_NAME)){
			result = new RoomServiceDao();
		}
		else if (tableName.equals(Service.TABLE_NAME)){
			result = new ServiceDao();
		}
		else if (tableName.equals("user")){
			result = new LoginDao();
		}
		else if (tableName.equals(InvoiceRecord.TABLE_NAME)){
			result = new InvoiceRecordDao();
		}
		else if (tableName.equals(CustomerAndRoom.TABLE_NAME)){
			result = new CustomerAndRoomDao();
		}
		else if (tableName.equals(Customer.TABLE_NAME)){
			result = new CustomerDao();
		}
		return result;
	}
}
