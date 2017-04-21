package com.edu.mum.hbs.restapi;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.edu.mum.hbs.dao.UserSession;
import com.edu.mum.hbs.entity.InvoiceRecord;


public interface RestQueryInterface {
	@POST
	@Path("/validateLogin")
	UserSession validateLogin(String input);

	@GET
	@Path("/ping")
	Response ping();
	
	@GET
	@Path("/loadServices")
    @Produces(MediaType.APPLICATION_JSON)
	Response loadServices();

	@GET
	@Path("/getService/{serviceId}")
    @Produces(MediaType.APPLICATION_JSON)
	Response getService(@PathParam("serviceId") String serviceId);

	@GET
	@Path("/loadServicesDesc")
    @Produces(MediaType.APPLICATION_JSON)
	Response loadServicesDesc();
	
	//Customer
	@GET
	@Path("/getCustomer/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
	Response getCustomer(@PathParam("customerId") String customerId);
	
	@GET
	@Path("/getCustomerFromPassportOrPhone/{customerIdorPhone}")
    @Produces(MediaType.APPLICATION_JSON)
	Response getCustomerFromPassportOrPhone(@PathParam("customerIdorPhone") String customerIdorPhone);
	
}
