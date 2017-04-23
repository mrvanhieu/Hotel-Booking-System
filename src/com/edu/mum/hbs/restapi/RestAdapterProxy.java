package com.edu.mum.hbs.restapi;

import java.util.List;

import com.edu.mum.hbs.dao.UserSession;
import com.edu.mum.hbs.entity.CustRoomDetails;
import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.Revenue;
import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.entity.RoomService;
import com.edu.mum.hbs.entity.Service;

public class RestAdapterProxy implements IRestAdapter{
	private final IRestAdapter adapter = RestAdapter.getInstance();
	private static IRestAdapter restProxy = new RestAdapterProxy();
	
	private IRestAdapter getAdapter() {
		if (adapter == null) return RestAdapter.getInstance();
		return adapter;
	}
	
	public static IRestAdapter getRestProxy() {
		if (restProxy == null) {
			restProxy = new RestAdapterProxy();
		}
		return restProxy;
	}
	
	@Override
	public UserSession authenticate(String userName, String password) {
		return getAdapter().authenticate(userName, password);
	}

	@Override
	public List<Service> loadServices() {
		return getAdapter().loadServices();
	}

	@Override
	public Service getService(String serviceId) {
		return getAdapter().getService(serviceId);
	}

	@Override
	public List<String> loadServicesDesc() {
		return getAdapter().loadServicesDesc();
	}

	@Override
	public void updateService(Service service) {
		getAdapter().updateService(service);
	}

	@Override
	public void addService(Service service) {
		getAdapter().addService(service);
	}

	@Override
	public boolean deleteService(Service service) {
		return getAdapter().deleteService(service);
	}

	@Override
	public void addCustomer(Customer service) {
		getAdapter().addCustomer(service);
	}

	@Override
	public Customer getCustomer(String customerId) {
		return getAdapter().getCustomer(customerId);
	}

	@Override
	public Customer getCustomerFromPassportOrPhone(String customerIdorPhone) {
		return getAdapter().getCustomerFromPassportOrPhone(customerIdorPhone);
	}

	@Override
	public void addInvoice(InvoiceRecord invoice) {
		getAdapter().addInvoice(invoice);
	}

	@Override
	public List<InvoiceRecord> getAllInvoiceRecords() {
		return getAdapter().getAllInvoiceRecords();
	}

	@Override
	public List<Revenue> getAllRevenueRecordsFromToDate(String fromDate, String toDate) {
		return getAdapter().getAllRevenueRecordsFromToDate(fromDate, toDate);
	}

	@Override
	public List<RoomService> getAllRoomServices() {
		return getAdapter().getAllRoomServices();
	}

	@Override
	public List<String> getUsedRooms(String roomStatus) {
		return getAdapter().getUsedRooms(roomStatus);
	}

	@Override
	public Double getTotalUsingService(String roomStatus) {
		return getAdapter().getTotalUsingService(roomStatus);
	}

	@Override
	public List<RoomService> getAllRoomServicesByRoomNumber(String roomNumber) {
		return getAdapter().getAllRoomServicesByRoomNumber(roomNumber);
	}

	@Override
	public void addRoomService(RoomService service) {
		getAdapter().addRoomService(service);
	}

	@Override
	public void updateRoomService(RoomService service) {
		getAdapter().updateRoomService(service);
	}

	@Override
	public boolean deleteRoomService(RoomService service) {
		return getAdapter().deleteRoomService(service);
	}

	@Override
	public boolean deleteRoomServiceByString(String roomServiceId) {
		return getAdapter().deleteRoomServiceByString(roomServiceId);
	}

	@Override
	public void deleteCustomerAndRooms(String passport, String roomNumber) {
		getAdapter().deleteCustomerAndRooms(passport, roomNumber);
	}

	@Override
	public void updateCustomerAndRooms(String roomNumber, String status) {
		getAdapter().updateCustomerAndRooms(roomNumber, status);
	}

	@Override
	public List<CustomerAndRoom> getAllCustomerRoomByStatus(String roomStatus) {
		return getAdapter().getAllCustomerRoomByStatus(roomStatus);
	}

	@Override
	public List<CustomerAndRoom> getCustomerAndRoom(String passportOrId, String status) {
		return getAdapter().getCustomerAndRoom(passportOrId, status);
	}

	@Override
	public List<String> getAllRoomNumbers() {
		return getAdapter().getAllRoomNumbers();
	}

	@Override
	public List<CustomerAndRoom> getAllCustomerRoom() {
		return getAdapter().getAllCustomerRoom();
	}

	@Override
	public List<CustRoomDetails> getCustomerRoomFullFromToDate(String fromDate, String toDate) {
		return getAdapter().getCustomerRoomFullFromToDate(fromDate, toDate);
	}

	@Override
	public Room getRoom(String roomNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Room> getAllRooms() {
		return getAdapter().getAllRooms();
	}

	@Override
	public List<Room> getAvailableRooms() {
		return getAdapter().getAvailableRooms();
	}

	@Override
	public void addRoom(Room room) {
		getAdapter().addRoom(room);
	}

	@Override
	public boolean deleteRoom(Room room) {
		return getAdapter().deleteRoom(room);
	}

	@Override
	public void updateRoom(Room room) {
		getAdapter().updateRoom(room);
	}

}
