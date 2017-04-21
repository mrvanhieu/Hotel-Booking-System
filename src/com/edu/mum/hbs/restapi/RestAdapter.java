package com.edu.mum.hbs.restapi;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import com.edu.mum.hbs.dao.UserSession;
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
	}
	
	@Override
	public void addService (Service service){
		WebTarget path = update.path("/addService");
		Response response = path.request().post(Entity.json(service));
	}
	
	@Override
	public boolean deleteService (Service service){
		WebTarget path = update.path("/deleteService");
		Response response = path.request().post(Entity.json(service));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) return true;
		return false; 
	}

	// CustomerAndRoom Services Start
	public List<CustomerAndRoom> getAllCustomerRoomByStatus(String roomStatus){
		WebTarget path = query.path("/getAllCustomerRoomByStatus/" + roomStatus);
		path.request().get().readEntity(new GenericType<List<CustomerAndRoom>>(){});
		List<CustomerAndRoom>  customerAndRooms = path.request().get().
				readEntity(new GenericType<List<CustomerAndRoom>>(){});
		return customerAndRooms;
	}

	public List<CustomerAndRoom> getCustomerAndRoom(String passportOrId, String status){
		WebTarget path = query.path("/getCustomerAndRoom/"+ passportOrId + "/" + status);
		path.request().get().readEntity(new GenericType<List<CustomerAndRoom>>(){});
		List<CustomerAndRoom>  customerAndRooms = path.request().get().
				readEntity(new GenericType<List<CustomerAndRoom>>(){});
		return customerAndRooms;
	}

	public List<String> getAllRoomNumbers(){
		WebTarget path = query.path("/getAllRoomNumbers");
		path.request().get().readEntity(new GenericType<List<String>>(){});
		List<String>  customerAndRooms = path.request().get().
				readEntity(new GenericType<List<String>>(){});
		return customerAndRooms;
	}

	public List<CustomerAndRoom> getAllCustomerRoom(){
		WebTarget path = query.path("/getAllCustomerRoom");
		path.request().get().readEntity(new GenericType<List<CustomerAndRoom>>(){});
		List<CustomerAndRoom>  customerAndRooms = path.request().get().
				readEntity(new GenericType<List<CustomerAndRoom>>(){});
		return customerAndRooms;
	}

	public List<CustRoomDetails> getCustomerRoomFullFromToDate(String fromDate, String toDate){
		WebTarget path = query.path("/getCustomerRoomFullFromToDate/"+ fromDate + "/" + toDate);
		path.request().get().readEntity(new GenericType<List<CustRoomDetails>>(){});
		List<CustRoomDetails>  customerAndRooms = path.request().get().
				readEntity(new GenericType<List<CustRoomDetails>>(){});
		return customerAndRooms;
	}

	public void updateCustomerAndRooms (String roomNumber, String status){
		WebTarget path = update.path("/updateCustomerAndRooms");
		CustomerAndRoomBean bean = new CustomerAndRoomBean();
		bean.setRoomNumber(roomNumber);
		bean.setStatus(status);
		Response response = path.request().post(Entity.json(bean));
		System.out.print("ping: " + response.getStatusInfo().getReasonPhrase() + "\n");
	}

	public void deleteCustomerAndRooms (String passport, String roomNumber){
		WebTarget path = update.path("/deleteCustomerAndRooms");
		CustomerAndRoomBean bean = new CustomerAndRoomBean();
		bean.setPassport(passport);
		bean.setRoomNumber(roomNumber);
		Response response = path.request().post(Entity.json(bean));
		System.out.print("ping: " + response.getStatusInfo().getReasonPhrase() + "\n");
	}

	// CustomerAndRoom Services End

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
