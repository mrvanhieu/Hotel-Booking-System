package com.edu.mum.hbs.restapi;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.edu.mum.hbs.dao.DaoFactory;
import com.edu.mum.hbs.dao.ServiceDao;
import com.edu.mum.hbs.entity.Service;
import com.google.gson.Gson;

@Path("/update")
public class RestUpdateImpl implements RestUpdateInterface {
	ServiceDao serviceDao = (ServiceDao) DaoFactory.getDaoFactory(Service.TABLE_NAME);
	public final static Gson gson = new Gson();
	
	@Override
	public Response updateService(String datapointJson) {
		serviceDao.update(gson.fromJson(datapointJson, Service.class));
		return Response.status(Response.Status.OK).build();
	}
	
	@Override
	public Response addService(String datapointJson) {
		serviceDao.addService(gson.fromJson(datapointJson, Service.class));
		return Response.status(Response.Status.OK).build();
	}
	
	@Override
	public Response deleteService(String datapointJson) {
		boolean result = serviceDao.delete(gson.fromJson(datapointJson, Service.class));
		if (!result) return Response.status(Response.Status.EXPECTATION_FAILED).build();
		return Response.status(Response.Status.OK).build();
	}

}
