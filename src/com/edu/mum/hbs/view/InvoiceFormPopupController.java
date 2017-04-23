package com.edu.mum.hbs.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.edu.mum.hbs.dao.CustomerDao;
import com.edu.mum.hbs.dao.DaoFactoryImpl;
import com.edu.mum.hbs.dao.RoomDao;
import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.entity.RoomService;
import com.edu.mum.hbs.restapi.IRestAdapter;
import com.edu.mum.hbs.restapi.RestAdapter;
import com.edu.mum.hbs.restapi.RestAdapterProxy;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class InvoiceFormPopupController extends ControllerBase {
	@FXML	private TextField fullName;
	@FXML	private TextField passport;
	@FXML	private TextField phoneNo;
	@FXML	private TextField roomNumber;
	@FXML	private TextField roomType;
	@FXML	private TextField roomClass;
	@FXML	private TextField roomPrice;
	@FXML	private TextField checkInDate;
	@FXML	private TextField checkOutDate;
	@FXML	private Label lblRoomAmount;
	@FXML	private Label lblServiceAmount;
	@FXML	private Label lblTotalAmount;

	@FXML	private TableView<RoomService> roomServiceTable;
	@FXML	private TableColumn<RoomService, String> roomNumberColumn;
	@FXML	private TableColumn<RoomService, String> serviceDescColumn;
	@FXML	private TableColumn<RoomService, String> quantityColumn;
	@FXML	private TableColumn<RoomService, String> serviceDateColumn;
	@FXML	private TableColumn<RoomService, String> serviceAmountColumn;
	@FXML	private TableColumn<RoomService, String> servicePriceColumn;
	IRestAdapter adapter = RestAdapterProxy.getRestProxy();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		super.initialize(location, resources);
	}
	

	public void viewData(InvoiceRecord invoiceRecord, List<RoomService> roomServices) {

		roomNumberColumn.setCellValueFactory(new PropertyValueFactory<RoomService, String>("roomNumber"));
		serviceDescColumn.setCellValueFactory(new PropertyValueFactory<RoomService, String>("serviceDesc"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<RoomService, String>("quantity"));
		serviceDateColumn.setCellValueFactory(new PropertyValueFactory<RoomService, String>("serviceDate"));
		servicePriceColumn.setCellValueFactory(new PropertyValueFactory<RoomService, String>("servicePrice"));
		serviceAmountColumn.setCellValueFactory(new PropertyValueFactory<RoomService, String>("serviceAmount"));
		

		reloadTableView(roomServiceTable, roomServices);

		passport.setText(invoiceRecord.getPassportOrId());
		roomNumber.setText(invoiceRecord.getRoomNumber());
		checkInDate.setText(String.valueOf(invoiceRecord.getCheckInDate()));
		checkOutDate.setText(String.valueOf(invoiceRecord.getCheckOutDate()));
		lblRoomAmount.setText("$" + invoiceRecord.getRoomAmount());
		lblServiceAmount.setText("$" + invoiceRecord.getServiceAmount());
		lblTotalAmount.setText("$" + invoiceRecord.getTotalAmount());

		Customer customer = adapter.getCustomer(invoiceRecord.getPassportOrId());
		fullName.setText(customer.getFullName());
		phoneNo.setText(customer.getPhoneNo());

		RoomDao rdao = (RoomDao) DaoFactoryImpl.getFactory().createDao(Room.TABLE_NAME);
//		Room room = rdao.getRoom(invoiceRecord.getRoomNumber());
		Room room =adapter.getRoom(invoiceRecord.getRoomNumber());
		roomType.setText(room.getRoomType());
		roomClass.setText(room.getRoomClass());
		roomPrice.setText(String.valueOf(room.getRoomPrice()));
	}

	@FXML
	public void printInvoice(){
		showAlert(Alert.AlertType.INFORMATION,"Notice","Hello :)", "This feature will be coming soon");
	}
}
