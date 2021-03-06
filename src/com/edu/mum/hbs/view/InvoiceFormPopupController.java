package com.edu.mum.hbs.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.edu.mum.hbs.dao.DaoFactoryImpl;
import com.edu.mum.hbs.dao.RoomDao;
import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.entity.RoomService;
import com.edu.mum.hbs.restapi.IRestAdapter;
import com.edu.mum.hbs.restapi.RestAdapterProxy;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

		passport.setText(invoiceRecord.getPassport_id());
		roomNumber.setText(invoiceRecord.getRoom_number());
		checkInDate.setText(String.valueOf(invoiceRecord.getCheck_in_date()));
		checkOutDate.setText(String.valueOf(invoiceRecord.getCheck_out_date()));
		lblRoomAmount.setText("$" + invoiceRecord.getRoom_amount());
		lblServiceAmount.setText("$" + invoiceRecord.getService_amount());
		lblTotalAmount.setText("$" + invoiceRecord.getTotal_amount());

		Customer customer = adapter.getCustomer(invoiceRecord.getPassport_id());
		fullName.setText(customer.getFullname());
		phoneNo.setText(customer.getPhoneNo());

		Room room =adapter.getRoom(invoiceRecord.getRoom_number());
		roomType.setText(room.getRoom_type());
		roomClass.setText(room.getRoom_class());
		roomPrice.setText(String.valueOf(room.getPrice()));
	}

	@FXML
	public void printInvoice(){
		showAlert(Alert.AlertType.INFORMATION,"Notice","Hello :)", "This feature will be coming soon");
	}
}
