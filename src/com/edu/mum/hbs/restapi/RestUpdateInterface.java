package com.edu.mum.hbs.restapi;

import com.edu.mum.hbs.entity.Room;

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


	// CustomerAndRoom Services Begin

	@POST
	@Path("/addCustomerAndRooms")
	Response addCustomerAndRooms(String datapointJson);

	@POST
	@Path("/updateCustomerAndRooms")
	Response updateCustomerAndRooms(String datapointJson);

	@POST
	@Path("/deleteCustomerAndRooms")
	Response deleteCustomerAndRooms(String datapointJson);
	// CustomerAndRoom Services End
	
	//Room Service
	@POST
	@Path("/addRoomService")
	Response addRoomService(String datapointJson);
	
	@POST
	@Path("/updateRoomService")
	Response updateRoomService(String datapointJson);
	
	@POST
	@Path("/deleteRoomService")
	Response deleteRoomService(String datapointJson);
	
	@POST
	@Path("/deleteRoomServiceByString")
	Response deleteRoomServiceByString(String datapointJson);

	// RoomDao Services Start
	@POST
	@Path("/addRoom")
	Response addRoom(String datapointJson);


	@POST
	@Path("/deleteRoom")
	Response deleteRoom(String datapointJson);

	@POST
	@Path("/updateRoom")
	Response updateRoom(String datapointJson);

	// RoomDao Services End
}
