package com.edu.mum.hbs.restapi;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import com.edu.mum.hbs.dao.UserSession;
import com.edu.mum.hbs.entity.Service;
import com.edu.mum.hbs.restapi.bean.LoginBean;
import com.edu.mum.hbs.util.Constants;
import com.edu.mum.hbs.util.GeneralUtil;
import com.google.gson.Gson;

public class RestAdapter {

	/**  end point for read queries. */
	private WebTarget query;
	public final static Gson gson = new Gson();
	private static RestAdapter adapter = new RestAdapter();

	/**  end point to supply updates. */
	private WebTarget update;

	public RestAdapter() {
		Client client = GeneralUtil.getConfiguredClient();
		query = client.target(Constants.BASE_URL + "/query");
		update = client.target(Constants.BASE_URL + "/update");
	}

	public static RestAdapter getInstance() {
		if (adapter == null) {
			adapter = new RestAdapter();
		}
		return adapter;
	}
	
	/**
	 * Ping collect.
	 */
	public UserSession authenticate(String userName, String password) {
		WebTarget path = query.path("/validateLogin");
		LoginBean bean = new LoginBean();
		bean.setPassword(password);
		bean.setUserName(userName);
		Response response = path.request().post(Entity.json(bean));
		System.out.print("ping: " + response.getStatusInfo().getReasonPhrase() + "\n");
		return response.readEntity(UserSession.class);

	}

	public List<Service> loadServices(){
		WebTarget path = query.path("/loadServices");
		List<Service> services = path.request().get().readEntity(new GenericType<List<Service>>(){});
		return services;
	}
	
	public Service getService(String serviceId){
		WebTarget path = query.path("/getService/" + serviceId);
		Service service = path.request().get().readEntity(new GenericType<Service>(){});
		return service;
	}
	
	public List<String> loadServicesDesc(){
		WebTarget path = query.path("/loadServicesDesc");
		List<String> servicesDesc = path.request().get().readEntity(new GenericType<List<String>>(){});
		return servicesDesc;
	}
	
	public void updateService (Service service){
		WebTarget path = update.path("/updateService");
		Response response = path.request().post(Entity.json(service));
	}
	
	public void addService (Service service){
		WebTarget path = update.path("/addService");
		Response response = path.request().post(Entity.json(service));
	}
	
	public boolean deleteService (Service service){
		WebTarget path = update.path("/deleteService");
		Response response = path.request().post(Entity.json(service));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) return true;
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
