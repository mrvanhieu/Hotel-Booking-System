package com.edu.mum.hbs.restapi;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.edu.mum.hbs.dao.CustomerDao;
import com.edu.mum.hbs.dao.DaoFactoryImpl;
import com.edu.mum.hbs.dao.LoginDao;
import com.edu.mum.hbs.dao.ServiceDao;
import com.edu.mum.hbs.dao.UserSession;
import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.dao.*;
import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.Service;
import com.edu.mum.hbs.restapi.bean.LoginBean;
import com.google.gson.Gson;

@Path("/query")
public class RestQueryImpl implements RestQueryInterface {
	/** shared gson json to object factory */
	public final static Gson gson = new Gson();
	LoginDao loginDao = new LoginDao();
	CustomerDao customerDao = (CustomerDao) DaoFactoryImpl.getFactory().createDao(Customer.TABLE_NAME);
	ServiceDao serviceDao = (ServiceDao) DaoFactoryImpl.getFactory().createDao(Service.TABLE_NAME);
	CustomerAndRoomDao customerAndRoomDao = (CustomerAndRoomDao) DaoFactoryImpl.getFactory().createDao(CustomerAndRoom.TABLE_NAME);

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

	@Override
	public Response getService(String serviceId) {
		return Response.status(Response.Status.OK).entity(serviceDao.getService(serviceId)).build();
	}
	
	@Override
	public Response getCustomer(String customerId) {
		return Response.status(Response.Status.OK).entity(customerDao.getCustomer(customerId)).build();
	}
	
	@Override
	public Response getCustomerFromPassportOrPhone(String customerIdorPhone) {
		return Response.status(Response.Status.OK).entity(customerDao.getCustomerFromPassportOrPhone(customerIdorPhone)).build();
	}

    // CustomerAndRoom Services Start
	@Override
	public Response getAllCustomerRoomByStatus(String roomStatus) {
		return Response.status(Response.Status.OK)
                .entity((customerAndRoomDao.getAllCustomerRoom(roomStatus))).build();
	}

    @Override
    public Response getCustomerAndRoom(String passportOrId, String status) {
        return Response.status(Response.Status.OK)
                .entity((customerAndRoomDao.getCustomerAndRoom(passportOrId, status))).build();
    }

    @Override
    public Response getAllRoomNumbers() {
        return Response.status(Response.Status.OK)
                .entity((customerAndRoomDao.getAllRoomNumbers())).build();
    }

    @Override
    public Response getAllCustomerRoom() {
        return Response.status(Response.Status.OK)
                .entity((customerAndRoomDao.getAllCustomerRoom())).build();
    }

    @Override
    public Response getCustomerRoomFullFromToDate(String fromDate, String toDate) {
        return Response.status(Response.Status.OK)
                .entity((customerAndRoomDao.getCustomerRoomFullFromToDate(fromDate, toDate))).build();
    }

    // CustomerAndRoom Services End
}
