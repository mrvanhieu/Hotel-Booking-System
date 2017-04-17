package com.edu.mum.hbs.restapi;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.edu.mum.hbs.dao.LoginDao;
import com.edu.mum.hbs.dao.UserSession;
import com.edu.mum.hbs.restapi.bean.LoginBean;
import com.google.gson.Gson;

@Path("/query")
public class RestQueryImpl implements RestQueryInterface {
	/** shared gson json to object factory */
	public final static Gson gson = new Gson();
	
	@Override
	public UserSession validateLogin(String input) {
		LoginDao loginDao = new LoginDao();
		LoginBean login = gson.fromJson(input, LoginBean.class);
        UserSession session = loginDao.validateLogin(login.userName, login.password);
        return session;
	}

	@Override
	public Response ping() {
		return Response.status(Response.Status.OK).entity("ready").build();
	}

}
