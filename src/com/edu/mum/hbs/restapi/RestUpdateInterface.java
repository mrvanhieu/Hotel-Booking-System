package com.edu.mum.hbs.restapi;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public interface RestUpdateInterface {
	
	///Service rest apis
	@POST
	@Path("/updateService")
	Response updateService(String datapointJson);
	
	@POST
	@Path("/addService")
	Response addService(String datapointJson);
	
	@POST
	@Path("/deleteService")
	Response deleteService(String datapointJson);
	
	//Customer rest api
	@POST
	@Path("/addCustomer")
	Response addCustomer(String datapointJson);
	
	//Invoice
	@POST
	@Path("/addInvoice")
	Response addInvoice(String datapointJson);
}
