package com.edu.mum.hbs.facade;

import java.util.List;

import com.edu.mum.hbs.dao.CustomerAndRoomDao;
import com.edu.mum.hbs.dao.CustomerDao;
import com.edu.mum.hbs.dao.DaoFactoryImpl;
import com.edu.mum.hbs.dao.InvoiceRecordDao;
import com.edu.mum.hbs.dao.LoginDao;
import com.edu.mum.hbs.dao.RoomDao;
import com.edu.mum.hbs.dao.RoomServiceDao;
import com.edu.mum.hbs.dao.ServiceDao;
import com.edu.mum.hbs.dao.UserSession;
import com.edu.mum.hbs.entity.CustRoomDetails;
import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.Revenue;
import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.entity.RoomService;
import com.edu.mum.hbs.entity.Service;

public class HotelBookingSystemQueryFacade {
	private LoginDao loginDao = new LoginDao();
	private CustomerDao customerDao = (CustomerDao) DaoFactoryImpl.getFactory().createDao(Customer.TABLE_NAME);
	private ServiceDao serviceDao = (ServiceDao) DaoFactoryImpl.getFactory().createDao(Service.TABLE_NAME);
	private CustomerAndRoomDao customerAndRoomDao = (CustomerAndRoomDao) DaoFactoryImpl.getFactory()
			.createDao(CustomerAndRoom.TABLE_NAME);

	private RoomDao roomDao = (RoomDao) DaoFactoryImpl.getFactory().createDao(Room.TABLE_NAME);
	private InvoiceRecordDao invoiceDao = (InvoiceRecordDao) DaoFactoryImpl.getFactory()
			.createDao(InvoiceRecord.TABLE_NAME);
	private RoomServiceDao roomServiceDao = (RoomServiceDao) DaoFactoryImpl.getFactory()
			.createDao(RoomService.TABLE_NAME);

	private static HotelBookingSystemQueryFacade facade = new HotelBookingSystemQueryFacade();

	public static HotelBookingSystemQueryFacade getInstance() {
		if (facade == null)
			facade = new HotelBookingSystemQueryFacade();
		return facade;
	}

	public UserSession authenticate(String userName, String password) {
		return loginDao.validateLogin(userName, password);
	}

	public List<Service> loadServices() {
		return serviceDao.loadServices();
	}

	public Service getService(String serviceId) {
		return serviceDao.getService(serviceId);
	}

	public List<String> loadServicesDesc() {
		return serviceDao.loadServicesDesc();
	}

	public Customer getCustomer(String customerId) {
		return customerDao.getCustomer(customerId);
	}

	public Customer getCustomerFromPassportOrPhone(String customerIdorPhone) {
		return customerDao.getCustomerFromPassportOrPhone(customerIdorPhone);
	}

	// CustomerAndRoom Services Start

	public List<CustomerAndRoom> getAllCustomerRoomByStatus(String roomStatus) {
		return customerAndRoomDao.getAllCustomerRoom(roomStatus);
	}

	public List<CustomerAndRoom> getCustomerAndRoom(String passportOrId, String status) {
		return customerAndRoomDao.getCustomerAndRoom(passportOrId, status);
	}

	public List<String> getAllRoomNumbers() {
		return customerAndRoomDao.getAllRoomNumbers();
	}

	public List<CustomerAndRoom> getAllCustomerRoom() {
		return customerAndRoomDao.getAllCustomerRoom();
	}

	public List<CustRoomDetails> getCustomerRoomFullFromToDate(String fromDate, String toDate) {
		return customerAndRoomDao.getCustomerRoomFullFromToDate(fromDate, toDate);
	}

	// InvoiceRecords

	public List<InvoiceRecord> getAllInvoiceRecords() {
		return invoiceDao.getAllInvoiceRecords();
	}

	// RoomDao Services Start

	public Room getRoom(String roomNumber) {
		return roomDao.getRoom(roomNumber);
	}

	public List<Room> getAllRooms() {
		return roomDao.getAllRooms();
	}

	public List<Room> getAvailableRooms() {
		return roomDao.getAvailableRooms();
	}

	// RoomDao Services End

	public List<Revenue> getAllRevenueRecordsFromToDate(String fromDate, String toDate) {
		return invoiceDao.getAllRevenueRecordsFromToDate(fromDate, toDate);
	}

	// RoomService
	public List<RoomService> getAllRoomServices() {
		return roomServiceDao.getAllRoomServices();
	}

	public List<RoomService> getAllRoomServicesByRoomNumber(String roomNumber) {
		return roomServiceDao.getAllRoomService(roomNumber);
	}

	public List<String> getUsedRooms(String roomStatus) {
		return roomServiceDao.getUsedRooms(roomStatus);
	}

	public Double getTotalUsingService(String roomStatus) {
		return roomServiceDao.getTotalUsingService(roomStatus);
	}
}
