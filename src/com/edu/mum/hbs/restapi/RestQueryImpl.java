package com.edu.mum.hbs.restapi;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.edu.mum.hbs.dao.UserSession;
import com.edu.mum.hbs.facade.HotelBookingSystemQueryFacade;
import com.edu.mum.hbs.restapi.bean.LoginBean;
import com.google.gson.Gson;

@Path("/query")
public class RestQueryImpl implements RestQueryInterface {
	/** shared gson json to object factory */
	public final static Gson gson = new Gson();
	HotelBookingSystemQueryFacade hbsFacade = HotelBookingSystemQueryFacade.getInstance();
	
	@Override
	public UserSession validateLogin(String input) {
		LoginBean login = gson.fromJson(input, LoginBean.class);
		UserSession session = hbsFacade.authenticate(login.userName, login.password);
		return session;
	}

	@Override
	public Response ping() {
		return Response.status(Response.Status.OK).entity("ready").build();
	}

	@Override
	public Response loadServices() {
		return Response.status(Response.Status.OK).entity(hbsFacade.loadServices()).build();
	}


	@Override
	public Response loadServicesDesc() {
		return Response.status(Response.Status.OK).entity(hbsFacade.loadServicesDesc()).build();
	}

	@Override
	public Response getService(String serviceId) {
		return Response.status(Response.Status.OK).entity(hbsFacade.getService(serviceId)).build();
	}
	
	@Override
	public Response getCustomer(String customerId) {
		return Response.status(Response.Status.OK).entity(hbsFacade.getCustomer(customerId)).build();
	}
	
	@Override
	public Response getCustomerFromPassportOrPhone(String customerIdorPhone) {
		return Response.status(Response.Status.OK).entity(hbsFacade.getCustomerFromPassportOrPhone(customerIdorPhone)).build();
	}

    // CustomerAndRoom Services Start
	@Override
	public Response getAllCustomerRoomByStatus(String roomStatus) {
		return Response.status(Response.Status.OK)
                .entity((hbsFacade.getAllCustomerRoomByStatus(roomStatus))).build();
	}

    @Override
    public Response getCustomerAndRoom(String passportOrId, String status) {
        return Response.status(Response.Status.OK)
                .entity((hbsFacade.getCustomerAndRoom(passportOrId, status))).build();
    }

    @Override
    public Response getAllRoomNumbers() {
        return Response.status(Response.Status.OK)
                .entity((hbsFacade.getAllRoomNumbers())).build();
    }
    
    @Override
    public Response getAllCustomerRoom() {
        return Response.status(Response.Status.OK)
                .entity((hbsFacade.getAllCustomerRoom())).build();
    }

    @Override
    public Response getCustomerRoomFullFromToDate(String fromDate, String toDate) {
        return Response.status(Response.Status.OK)
                .entity((hbsFacade.getCustomerRoomFullFromToDate(fromDate, toDate))).build();
    }

	// CustomerAndRoom Services End

	// RoomDao Services Start
	@Override
	public Response getRoom(String roomNumber) {
		return Response.status(Response.Status.OK)
				.entity((hbsFacade.getRoom(roomNumber))).build();

	}

	@Override
	public Response getAllRooms() {
		return Response.status(Response.Status.OK).entity(hbsFacade.getAllRooms()).build();
	}

	@Override
	public Response getAvailableRooms() {
		return Response.status(Response.Status.OK).entity(hbsFacade.getAvailableRooms()).build();
	}

	// RoomDao Services End
    // CustomerAndRoom Services End
    
    //Invoice
    @Override
    public Response getAllInvoiceRecords() {
        return Response.status(Response.Status.OK)
                .entity((hbsFacade.getAllInvoiceRecords())).build();
    }

	@Override
	public Response getAllRevenueRecordsFromToDate(String fromDate, String toDate) {
        return Response.status(Response.Status.OK)
                .entity((hbsFacade.getAllRevenueRecordsFromToDate(fromDate, toDate))).build();
	}
	
	//RoomService
	@Override
    public Response getAllRoomServices() {
        return Response.status(Response.Status.OK)
                .entity((hbsFacade.getAllRoomServices())).build();
    }
	
	public Response getAllRoomServicesByRoomNumber(String roomNumber) {
        return Response.status(Response.Status.OK)
                .entity((hbsFacade.getAllRoomServicesByRoomNumber(roomNumber))).build();
    }
	
	@Override
	public Response getUsedRooms(String roomStatus) {
		return Response.status(Response.Status.OK)
                .entity((hbsFacade.getUsedRooms(roomStatus))).build();
	}
	
	@Override
	public Response getTotalUsingService(String roomNumber) {
		return Response.status(Response.Status.OK)
                .entity(hbsFacade.getTotalUsingService(roomNumber)).build();
	}

}
