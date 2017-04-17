package com.edu.mum.hbs.restapi;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.edu.mum.hbs.dao.UserSession;
import com.edu.mum.hbs.restapi.bean.LoginBean;
import com.edu.mum.hbs.util.Constants;
import com.edu.mum.hbs.util.GeneralUtil;

public class RestAdapter {

	/**  end point for read queries. */
	private WebTarget query;

	/**  end point to supply updates. */
	private WebTarget collect;

	public RestAdapter() {
		Client client = GeneralUtil.getConfiguredClient();
		query = client.target(Constants.BASE_URL + "/query");
		collect = client.target(Constants.BASE_URL + "/collect");
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
