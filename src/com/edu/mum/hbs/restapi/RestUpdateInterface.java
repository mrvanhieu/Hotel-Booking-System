package com.edu.mum.hbs.restapi;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public interface RestUpdateInterface {
	@POST
	@Path("/updateService")
	Response updateService(String datapointJson);
}