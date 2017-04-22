package com.edu.mum.hbs.restapi;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import com.edu.mum.hbs.dao.UserSession;

import com.edu.mum.hbs.entity.*;

public interface IRestAdapter {

	UserSession authenticate(String userName, String password);

	List<Service> loadServices();

	Service getService(String serviceId);

	List<String> loadServicesDesc();

	void updateService(Service service);

	void addService(Service service);

	boolean deleteService(Service service);
	
	//Customer rest apis
	void addCustomer(Customer service);
	Customer getCustomer(String customerId);
	Customer getCustomerFromPassportOrPhone(String customerIdorPhone);
	
	//Invoice rest apis
	void addInvoice(InvoiceRecord invoice);
	public List<InvoiceRecord> getAllInvoiceRecords();
	public List<Revenue> getAllRevenueRecordsFromToDate(String fromDate, String toDate);
	
	//RoomService
	public List<RoomService> getAllRoomServices();
	public List<String> getUsedRooms(String roomStatus);
	public Double getTotalUsingService(String roomStatus);
	public List<RoomService> getAllRoomServicesByRoomNumber(String roomNumber);
	public void addRoomService (RoomService service);
	public void updateRoomService (RoomService service);
	public boolean deleteRoomService(RoomService service);
	public boolean deleteRoomServiceByString(String roomServiceId);
	
	//CustomerAndRoom
	public void deleteCustomerAndRooms (String passport, String roomNumber);
	public void updateCustomerAndRooms (String roomNumber, String status);
	public List<CustomerAndRoom> getAllCustomerRoomByStatus(String roomStatus);

	public List<CustomerAndRoom> getCustomerAndRoom(String passportOrId, String status);

	public List<String> getAllRoomNumbers();
	public List<CustomerAndRoom> getAllCustomerRoom();
	public List<CustRoomDetails> getCustomerRoomFullFromToDate(String fromDate, String toDate);

	// RoomDao Services Start
	public Room getRoom(String roomNumber);
	public List<Room> getAllRooms();
	public List<Room> getAvailableRooms();
	void addRoom(Room room);
	boolean deleteRoom(Room room);
	void updateRoom(Room room);

	// RoomDao Services End

}
