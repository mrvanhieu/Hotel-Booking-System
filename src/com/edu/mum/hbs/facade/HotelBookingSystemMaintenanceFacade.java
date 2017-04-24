package com.edu.mum.hbs.facade;

import com.edu.mum.hbs.dao.CustomerAndRoomDao;
import com.edu.mum.hbs.dao.CustomerDao;
import com.edu.mum.hbs.dao.DaoFactoryImpl;
import com.edu.mum.hbs.dao.InvoiceRecordDao;
import com.edu.mum.hbs.dao.RoomDao;
import com.edu.mum.hbs.dao.RoomServiceDao;
import com.edu.mum.hbs.dao.ServiceDao;
import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.entity.RoomService;
import com.edu.mum.hbs.entity.Service;

public class HotelBookingSystemMaintenanceFacade {
	private CustomerDao customerDao = (CustomerDao) DaoFactoryImpl.getFactory().createDao(Customer.TABLE_NAME);
	private ServiceDao serviceDao = (ServiceDao) DaoFactoryImpl.getFactory().createDao(Service.TABLE_NAME);
	private CustomerAndRoomDao customerAndRoomDao = (CustomerAndRoomDao) DaoFactoryImpl.getFactory()
			.createDao(CustomerAndRoom.TABLE_NAME);

	private RoomDao roomDao = (RoomDao) DaoFactoryImpl.getFactory().createDao(Room.TABLE_NAME);
	private InvoiceRecordDao invoiceDao = (InvoiceRecordDao) DaoFactoryImpl.getFactory()
			.createDao(InvoiceRecord.TABLE_NAME);
	private RoomServiceDao roomServiceDao = (RoomServiceDao) DaoFactoryImpl.getFactory()
			.createDao(RoomService.TABLE_NAME);

	private static HotelBookingSystemMaintenanceFacade facade = new HotelBookingSystemMaintenanceFacade();

	public static HotelBookingSystemMaintenanceFacade getInstance() {
		if (facade == null)
			facade = new HotelBookingSystemMaintenanceFacade();
		return facade;
	}

	public void updateService(Service service) {
		serviceDao.update(service);
	}

	public void addService(Service service) {
		serviceDao.addService(service);
	}

	// Invoice

	public void addInvoice(InvoiceRecord invoice) {
		invoiceDao.addInvoice(invoice);
	}

	// Customer

	public void addCustomer(Customer customer) {
		customerDao.addCustomer(customer);
	}

	public boolean deleteService(Service service) {
		return serviceDao.delete(service);
	}

	public void updateCustomerAndRooms(String roomNumber, String status) {
		customerAndRoomDao.update(roomNumber, status);
	}

	public void deleteCustomerAndRooms(String passport, String roomNumber) {
		customerAndRoomDao.delete(passport, roomNumber);
	}
	// CustomerAndRoom Services End

	public void addRoom(Room room) {
		roomDao.addRoom(room);
	}

	public boolean deleteRoom(Room room) {
		return roomDao.delete(room);
	}

	public void updateRoom(Room room) {
		roomDao.update(room);
	}

	// RoomDao Services End

	public void addRoomService(RoomService service) {
		roomServiceDao.addRoomService(service);
	}

	public void updateRoomService(RoomService service) {
		roomServiceDao.update(service);
	}

	public boolean deleteRoomService(RoomService service) {
		return roomServiceDao.delete(service);
	}

	public boolean deleteRoomServiceByString(String roomServiceId) {
		return roomServiceDao.delete(roomServiceId);
	}
}
