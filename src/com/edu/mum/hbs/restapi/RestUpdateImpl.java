package com.edu.mum.hbs.restapi;

import java.time.LocalDate;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.entity.RoomService;
import com.edu.mum.hbs.entity.Service;
import com.edu.mum.hbs.facade.HotelBookingSystemMaintenanceFacade;
import com.edu.mum.hbs.restapi.bean.CustomerAndRoomBean;
import com.edu.mum.hbs.restapi.util.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

@Path("/update")
public class RestUpdateImpl implements RestUpdateInterface {
	HotelBookingSystemMaintenanceFacade hbsMaintenanceFacade = HotelBookingSystemMaintenanceFacade.getInstance();
	private final static Gson gson = new Gson();
	private final static Gson gsonLocalDate = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();


	@Override
	public Response updateService(String datapointJson) {
		hbsMaintenanceFacade.updateService(gson.fromJson(datapointJson, Service.class));
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response addService(String datapointJson) {
		hbsMaintenanceFacade.addService(gson.fromJson(datapointJson, Service.class));
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response deleteService(String datapointJson) {
		boolean result = hbsMaintenanceFacade.deleteService(gson.fromJson(datapointJson, Service.class));
		if (!result)
			return Response.status(Response.Status.EXPECTATION_FAILED).build();
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response addCustomer(String datapointJson) {
		hbsMaintenanceFacade.addCustomer(gsonLocalDate.fromJson(datapointJson, Customer.class));
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response addInvoice(String datapointJson) {
		try {
			Gson gson1 = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
			InvoiceRecord record = gson1.fromJson(datapointJson, InvoiceRecord.class);
			hbsMaintenanceFacade.addInvoice(record);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(datapointJson);
		}
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response addCustomerAndRooms(String datapointJson) {
		CustomerAndRoomBean customerAndRoomBean = gsonLocalDate.fromJson(datapointJson, CustomerAndRoomBean.class);
		hbsMaintenanceFacade.addCustomerAndRooms(customerAndRoomBean.getPassport(),
				customerAndRoomBean.getRoomDates(), customerAndRoomBean.getStatus());
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response updateCustomerAndRooms(String datapointJson) {
		CustomerAndRoomBean customerAndRoomBean = gson.fromJson(datapointJson, CustomerAndRoomBean.class);
		hbsMaintenanceFacade.updateCustomerAndRooms(customerAndRoomBean.getRoomNumber(), customerAndRoomBean.getStatus());
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response deleteCustomerAndRooms(String datapointJson) {
		CustomerAndRoomBean customerAndRoomBean = gson.fromJson(datapointJson, CustomerAndRoomBean.class);
		hbsMaintenanceFacade.deleteCustomerAndRooms(customerAndRoomBean.getPassport(), customerAndRoomBean.getRoomNumber());
		return Response.status(Response.Status.OK).build();
  }

	// RoomDao Services Start
	@Override
	public Response addRoom(String datapointJson) {
		hbsMaintenanceFacade.addRoom(gson.fromJson(datapointJson, Room.class));
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response deleteRoom(String datapointJson) {
		boolean result = hbsMaintenanceFacade.deleteRoom(gson.fromJson(datapointJson, Room.class));
		if (!result) return Response.status(Response.Status.EXPECTATION_FAILED).build();
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response updateRoom(String datapointJson) {
		hbsMaintenanceFacade.updateRoom(gson.fromJson(datapointJson, Room.class));
		return Response.status(Response.Status.OK).build();
	}
	// RoomDao Services End

	// Room Servie
	@Override
	public Response addRoomService(String datapointJson) {
		try {
			RoomService record = gsonLocalDate.fromJson(datapointJson, RoomService.class);
			hbsMaintenanceFacade.addRoomService(record);
		} catch (JsonSyntaxException e) {
			System.out.println(datapointJson);
		}
		return Response.status(Response.Status.OK).build();
	}
	
	@Override
	public Response updateRoomService(String datapointJson) {
		try {
			RoomService record = gsonLocalDate.fromJson(datapointJson, RoomService.class);
			hbsMaintenanceFacade.updateRoomService(record);
		} catch (JsonSyntaxException e) {
			System.out.println(datapointJson);
		}
		return Response.status(Response.Status.OK).build();
	}
	
	@Override
	public Response deleteRoomService(String datapointJson) {
		boolean result = hbsMaintenanceFacade.deleteRoomService(gson.fromJson(datapointJson, RoomService.class));
		if (!result)
			return Response.status(Response.Status.EXPECTATION_FAILED).build();
		return Response.status(Response.Status.OK).build();
	}
	
	@Override
	public Response deleteRoomServiceByString(String datapointJson) {
		boolean result = hbsMaintenanceFacade.deleteRoomServiceByString(gson.fromJson(datapointJson, String.class));
		if (!result)
			return Response.status(Response.Status.EXPECTATION_FAILED).build();
		return Response.status(Response.Status.OK).build();
	}

}