package com.edu.mum.hbs.restapi;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.edu.mum.hbs.dao.UserSession;


public interface RestQueryInterface {
	@POST
	@Path("/validateLogin")
	UserSession validateLogin(String input);

	@GET
	@Path("/ping")
	Response ping();
}
