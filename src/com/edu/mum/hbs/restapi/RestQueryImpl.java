package com.edu.mum.hbs.restapi;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.edu.mum.hbs.dao.DaoFactoryImpl;
import com.edu.mum.hbs.dao.LoginDao;
import com.edu.mum.hbs.dao.ServiceDao;
import com.edu.mum.hbs.dao.UserSession;
import com.edu.mum.hbs.entity.Service;
import com.edu.mum.hbs.restapi.bean.LoginBean;
import com.google.gson.Gson;

@Path("/query")
public class RestQueryImpl implements RestQueryInterface {
	/** shared gson json to object factory */
	public final static Gson gson = new Gson();
	LoginDao loginDao = new LoginDao();
	ServiceDao serviceDao = (ServiceDao) DaoFactoryImpl.getFactory().createDao(Service.TABLE_NAME);

	@Override
	public UserSession validateLogin(String input) {

		LoginBean login = gson.fromJson(input, LoginBean.class);
		UserSession session = loginDao.validateLogin(login.userName, login.password);
		return session;
	}

	@Override
	public Response ping() {
		return Response.status(Response.Status.OK).entity("ready").build();
	}

	@Override
	public Response loadServices() {
		return Response.status(Response.Status.OK).entity(serviceDao.loadServices()).build();
	}

	@Override
	public Response loadServicesDesc() {
		return Response.status(Response.Status.OK).entity(serviceDao.loadServicesDesc()).build();
	}

}
