package com.edu.mum.hbs.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.edu.mum.hbs.dao.CustomerAndRoomDao;
import com.edu.mum.hbs.dao.DaoFactory;
import com.edu.mum.hbs.dao.RoomDao;
import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.Room;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReportRoomFormController extends ControllerBase {
	@FXML	private Label lblAvailableRooms;
	@FXML	private Label lblBookingRooms;
	@FXML	private Label lblCheckedInRooms;
	
	@FXML	private TableView<Room> availableTable;
	@FXML	private TableColumn<Room, String> aRoomNoColumn;
	@FXML	private TableColumn<Room, String> aTypeColumn;
	@FXML	private TableColumn<Room, String> aClassColumn;
	@FXML	private TableColumn<Room, String> aPriceColumn;
	
	@FXML	private TableView<CustomerAndRoom> bookingTable;
	@FXML	private TableColumn<CustomerAndRoom, String> bRoomNoColumn;
	@FXML	private TableColumn<CustomerAndRoom, String> bCheckInColumn;
	@FXML	private TableColumn<CustomerAndRoom, String> bCheckOutColumn;

	@FXML	private TableView<CustomerAndRoom> checkedInTable;
	@FXML	private TableColumn<CustomerAndRoom, String> cRoomNoColumn;
	@FXML	private TableColumn<CustomerAndRoom, String> cCheckInColumn;
	@FXML	private TableColumn<CustomerAndRoom, String> cCheckOutColumn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		RoomDao rDao = (RoomDao) DaoFactory.getDaoFactory(Room.TABLE_NAME);
		List<Room> availableRooms = rDao.getAvailableRooms();
		
		if (availableRooms != null){
			aRoomNoColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("roomNumber"));
			aTypeColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("roomType"));
			aClassColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("roomClass"));
			aPriceColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("roomPriceDollar"));
	
			reloadTableView(availableTable, availableRooms);
			lblAvailableRooms.setText("Available Rooms (" + availableRooms.size() + ")");
		}

		CustomerAndRoomDao crDao = (CustomerAndRoomDao) DaoFactory.getDaoFactory(CustomerAndRoom.TABLE_NAME);

		List<CustomerAndRoom> customerRoomBooking = crDao.getAllCustomerRoom(CustomerAndRoom.BOOKING_STATUS);
		if (customerRoomBooking != null){
			bRoomNoColumn.setCellValueFactory(new PropertyValueFactory<CustomerAndRoom, String>("roomNumber"));
			bCheckInColumn.setCellValueFactory(new PropertyValueFactory<CustomerAndRoom, String>("checkInDate"));
			bCheckOutColumn.setCellValueFactory(new PropertyValueFactory<CustomerAndRoom, String>("checkOutDate"));
			
			reloadTableView(bookingTable, customerRoomBooking);
			lblBookingRooms.setText("Booking Rooms (" + customerRoomBooking.size() + ")");
		}
		
		List<CustomerAndRoom> customerRoomChecked = crDao.getAllCustomerRoom(CustomerAndRoom.CHECKED_STATUS);
		if (customerRoomChecked != null){
			cRoomNoColumn.setCellValueFactory(new PropertyValueFactory<CustomerAndRoom, String>("roomNumber"));
			cCheckInColumn.setCellValueFactory(new PropertyValueFactory<CustomerAndRoom, String>("checkInDate"));
			cCheckOutColumn.setCellValueFactory(new PropertyValueFactory<CustomerAndRoom, String>("checkOutDate"));
			
			reloadTableView(checkedInTable, customerRoomChecked);
			lblCheckedInRooms.setText("Checked-in Rooms (" + customerRoomChecked.size() + ")");
		}
		
	}
}
