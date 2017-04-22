package com.edu.mum.hbs.restapi;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import com.edu.mum.hbs.dao.UserSession;
import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.Revenue;
import com.edu.mum.hbs.entity.RoomService;
import com.edu.mum.hbs.entity.CustRoomDetails;
import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.Service;
import com.edu.mum.hbs.restapi.bean.CustomerAndRoomBean;
import com.edu.mum.hbs.restapi.bean.LoginBean;
import com.edu.mum.hbs.util.Constants;
import com.edu.mum.hbs.util.GeneralUtil;
import com.google.gson.Gson;

public class RestAdapter implements IRestAdapter{

	/**  end point for read queries. */
	private WebTarget query;
	public final static Gson gson = new Gson();
	private static RestAdapter adapter = new RestAdapter();

	/**  end point to supply updates. */
	private WebTarget update;

	private RestAdapter() {
		Client client = GeneralUtil.getConfiguredClient();
		query = client.target(Constants.BASE_URL + "/query");
		update = client.target(Constants.BASE_URL + "/update");
	}

	public static IRestAdapter getInstance() {
		if (adapter == null) {
			adapter = new RestAdapter();
		}
		return adapter;
	}
	
	/**
	 * Ping collect.
	 */
	@Override
	public UserSession authenticate(String userName, String password) {
		WebTarget path = query.path("/validateLogin");
		LoginBean bean = new LoginBean();
		bean.setPassword(password);
		bean.setUserName(userName);
		Response response = path.request().post(Entity.json(bean));
		System.out.print("ping: " + response.getStatusInfo().getReasonPhrase() + "\n");
		return response.readEntity(UserSession.class);

	}

	@Override
	public List<Service> loadServices(){
		WebTarget path = query.path("/loadServices");
		List<Service> services = path.request().get().readEntity(new GenericType<List<Service>>(){});
		return services;
	}
	
	@Override
	public Service getService(String serviceId){
		WebTarget path = query.path("/getService/" + serviceId);
		Service service = path.request().get().readEntity(new GenericType<Service>(){});
		return service;
	}

	@Override
	public List<String> loadServicesDesc(){
		WebTarget path = query.path("/loadServicesDesc");
		List<String> servicesDesc = path.request().get().readEntity(new GenericType<List<String>>(){});
		return servicesDesc;
	}

	@Override
	public void updateService (Service service){
		WebTarget path = update.path("/updateService");
		Response response = path.request().post(Entity.json(service));
		response.close();
	}

	@Override
	public void addService (Service service){
		WebTarget path = update.path("/addService");
		Response response = path.request().post(Entity.json(service));
		response.close();
	}

	//Invoice
	@Override
	public void addInvoice(InvoiceRecord invoice) {
		WebTarget path = update.path("/addInvoice");
		Response response = path.request().post(Entity.json(invoice));
		response.close();
		System.out.println("Test:" + response.getStatusInfo().getReasonPhrase());
	}

	//Customer 
	@Override
	public void addCustomer(Customer customer) {
		WebTarget path = update.path("/addCustomer");
		Response response = path.request().post(Entity.json(customer));
		response.close();
	}
	
	@Override
	public Customer getCustomer(String customerId){
		WebTarget path = query.path("/getCustomer/" + customerId);
		Customer customer = path.request().get().readEntity(new GenericType<Customer>(){});
		return customer;
	}
	
	@Override
	public Customer getCustomerFromPassportOrPhone(String customerIdorPhone) {
		WebTarget path = query.path("/getCustomerFromPassportOrPhone/" + customerIdorPhone);
		Customer customer = path.request().get().readEntity(new GenericType<Customer>(){});
		return customer;
	}

	
	@Override
	public boolean deleteService (Service service){
		WebTarget path = update.path("/deleteService");
		Response response = path.request().post(Entity.json(service));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) return true;
		return false; 
	}

	// CustomerAndRoom Services Start
	@Override
	public List<CustomerAndRoom> getAllCustomerRoomByStatus(String roomStatus){
		WebTarget path = query.path("/getAllCustomerRoomByStatus/" + roomStatus);
		List<CustomerAndRoom>  customerAndRooms = path.request().get().
				readEntity(new GenericType<List<CustomerAndRoom>>(){});
		return customerAndRooms;
	}

	@Override
	public List<CustomerAndRoom> getCustomerAndRoom(String passportOrId, String status){
		WebTarget path = query.path("/getCustomerAndRoom/"+ passportOrId + "/" + status);
		List<CustomerAndRoom>  customerAndRooms = path.request().get().
				readEntity(new GenericType<List<CustomerAndRoom>>(){});
		return customerAndRooms;
	}

	@Override
	public List<String> getAllRoomNumbers(){
		WebTarget path = query.path("/getAllRoomNumbers");
		List<String>  customerAndRooms = path.request().get().
				readEntity(new GenericType<List<String>>(){});
		return customerAndRooms;
	}

	@Override
	public List<CustomerAndRoom> getAllCustomerRoom(){
		WebTarget path = query.path("/getAllCustomerRoom");
		List<CustomerAndRoom>  customerAndRooms = path.request().get().
				readEntity(new GenericType<List<CustomerAndRoom>>(){});
		return customerAndRooms;
	}

	@Override
	public List<CustRoomDetails> getCustomerRoomFullFromToDate(String fromDate, String toDate){
		WebTarget path = query.path("/getCustomerRoomFullFromToDate/"+ fromDate + "/" + toDate);
		List<CustRoomDetails>  customerAndRooms = path.request().get().
				readEntity(new GenericType<List<CustRoomDetails>>(){});
		return customerAndRooms;
	}

	@Override
	public void updateCustomerAndRooms (String roomNumber, String status){
		WebTarget path = update.path("/updateCustomerAndRooms");
		CustomerAndRoomBean bean = new CustomerAndRoomBean();
		bean.setRoomNumber(roomNumber);
		bean.setStatus(status);
		Response response = path.request().post(Entity.json(bean));
		response.close();
		System.out.print("ping: " + response.getStatusInfo().getReasonPhrase() + "\n");
	}

	@Override
	public void deleteCustomerAndRooms (String passport, String roomNumber){
		WebTarget path = update.path("/deleteCustomerAndRooms");
		CustomerAndRoomBean bean = new CustomerAndRoomBean();
		bean.setPassport(passport);
		bean.setRoomNumber(roomNumber);
		Response response = path.request().post(Entity.json(bean));
		response.close();
		System.out.print("ping: " + response.getStatusInfo().getReasonPhrase() + "\n");
	}

	// CustomerAndRoom Services End
	
	//InvoiceRecords
	@Override
	public List<InvoiceRecord> getAllInvoiceRecords(){
		WebTarget path = query.path("/getAllInvoiceRecords");
		List<InvoiceRecord>  invoiceRecords = path.request().get().
				readEntity(new GenericType<List<InvoiceRecord>>(){});
		return invoiceRecords;
	}

	@Override
	public List<Revenue> getAllRevenueRecordsFromToDate(String fromDate, String toDate){
		WebTarget path = query.path("/getAllRevenueRecordsFromToDate/" + fromDate+"/" + toDate);
		Response response  = path.request().get();
		List<Revenue>  invoiceRecords = response.
				readEntity(new GenericType<List<Revenue>>(){});
		return invoiceRecords;
	}
	
	//RoomService
	@Override
	public List<RoomService> getAllRoomServices(){
		WebTarget path = query.path("/getAllRoomServices");
		List<RoomService>  roomServices = path.request().get().
				readEntity(new GenericType<List<RoomService>>(){});
		return roomServices;
	}
	
	@Override
	public List<RoomService> getAllRoomServicesByRoomNumber(String roomNumber){
		WebTarget path = query.path("/getAllRoomServicesByRoomNumber/" + roomNumber);
		List<RoomService>  roomServices = path.request().get().
				readEntity(new GenericType<List<RoomService>>(){});
		return roomServices;
	}
	
	@Override
	public List<String> getUsedRooms(String roomStatus){
		WebTarget path = query.path("/getUsedRooms/" + roomStatus);
		List<String>  roomServices = path.request().get().
				readEntity(new GenericType<List<String>>(){});
		return roomServices;
	}
	
	@Override
	public Double getTotalUsingService(String roomStatus){
		WebTarget path = query.path("/getTotalUsingService/" + roomStatus);
		Double  total = path.request().get().
				readEntity(new GenericType<Double>(){});
		return total;
	}
	
	@Override
	public void addRoomService (RoomService service){
		WebTarget path = update.path("/addRoomService");
		Response response = path.request().post(Entity.json(service));
		response.close();
	}
	
	@Override
	public void updateRoomService (RoomService service){
		WebTarget path = update.path("/updateRoomService");
		Response response = path.request().post(Entity.json(service));
		response.close();
	}
	
	@Override
	public boolean deleteRoomService(RoomService service){
		WebTarget path = update.path("/deleteRoomService");
		Response response = path.request().post(Entity.json(service));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			response.close();
			return true;
		}
		response.close();
		return false; 
	}
	
	@Override
	public boolean deleteRoomServiceByString(String roomServiceId){
		WebTarget path = update.path("/deleteRoomServiceByString");
		Response response = path.request().post(Entity.json(roomServiceId));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			response.close();
			return true;
		}
		response.close();
		return false; 
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			RestAdapter wc = new RestAdapter();
//			wc.authenticate();

			System.out.print("complete");
		} catch (Exception e) {
			System.exit(0);
		}
		System.exit(0);
	}

}
