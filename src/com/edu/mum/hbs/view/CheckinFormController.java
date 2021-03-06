package com.edu.mum.hbs.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.entity.RoomDate;
import com.edu.mum.hbs.restapi.IRestAdapter;
import com.edu.mum.hbs.restapi.RestAdapterProxy;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CheckinFormController extends ControllerBase {
	@FXML
	private TextField searchText;
	@FXML
	private TextField fullName;
	@FXML
	private TextField passport;
	@FXML
	private TextField phoneNo;

	@FXML
	private TableView<RoomDate> bookingTable;
	@FXML
	private TableColumn<RoomDate, String> roomNumberColumn;
	@FXML
	private TableColumn<RoomDate, String> priceColumn;
	@FXML
	private TableColumn<RoomDate, String> roomTypeColumn;
	@FXML
	private TableColumn<RoomDate, String> roomClassColumn;
	@FXML
	private TableColumn<RoomDate, String> checkInDateColumn;
	@FXML
	private TableColumn<RoomDate, String> checkOutDateColumn;

	IRestAdapter adapter = RestAdapterProxy.getRestProxy();
	private List<RoomDate> bookingRooms = new ArrayList<RoomDate>();

	@FXML
	public void searchCustomer() {
		String szSearch = searchText.getText();
		if (szSearch == null || szSearch.equals("")) {
			showAlert(AlertType.INFORMATION, "Enter Values", null, "You have to fill in search content to continue.");
			return;
		}

		Customer customer = adapter.getCustomerFromPassportOrPhone(szSearch);

		if (customer == null) {
			// Show error msg
			fullName.setText("");
			passport.setText("");
			phoneNo.setText("");
			showAlert(AlertType.INFORMATION, "Cannot Find", null,
					"We can not find any data matching with your criteria. Please try with another one");
			return;
		}

		roomNumberColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("room_number"));
		roomTypeColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("room_type"));
		roomClassColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("room_class"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("price"));
		checkInDateColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("checkInDate"));
		checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("checkOutDate"));

		// If found a customer

		// 0. Reset search field for next search
		searchText.setText("");
		fullName.setText(customer.getFullname());
		passport.setText(customer.getPassport_id());
		phoneNo.setText(customer.getPhoneNo());

		// 1. getCustomerAndRoom
		List<CustomerAndRoom> customerAndRooms = adapter.getCustomerAndRoom(customer.getPassport_id(),
				CustomerAndRoom.BOOKING_STATUS);
		List<RoomDate> roomDates = new ArrayList<>();
		for (CustomerAndRoom cr : customerAndRooms) {
			Room r = adapter.getRoom(cr.getRoomNumber());
			RoomDate rd = new RoomDate(r, cr.getCheckInDate(), cr.getCheckOutDate());
			roomDates.add(rd);
		}

		bookingRooms.addAll(roomDates);
		reloadTableView(bookingTable, roomDates);
	}

	private boolean checkNonEmptyCustomer() {
		if (fullName.getText().isEmpty() || passport.getText().isEmpty()) {
			return false;
		}
		return true;
	}

	@FXML
	public void checkIn() {

		if (!checkNonEmptyCustomer()) {
			showAlert(AlertType.INFORMATION, "Enter Values", null, "You have to fill all form data to continue.");
			return;
		}
		RoomDate roomDate = bookingTable.getSelectionModel().getSelectedItem();
		if (roomDate == null)
			return;
		Optional<ButtonType> result = showAlert(AlertType.CONFIRMATION, "Check In Confirmation", "Are you sure?", "");
		if (result.get() == ButtonType.OK) {
			adapter.updateCustomerAndRooms(roomDate.getRoom_number(), CustomerAndRoom.CHECKED_STATUS);
			bookingRooms.remove(roomDate);
			reloadTableView(bookingTable, bookingRooms);
		} else {
			// ... user chose CANCEL or closed the dialog

		}
	}

	public void cancelBooking() {
		if (!checkNonEmptyCustomer()) {
			showAlert(AlertType.INFORMATION, "Enter Values", null, "You have to fill all form data to continue.");
			return;
		}
		RoomDate roomDate = bookingTable.getSelectionModel().getSelectedItem();
		if (roomDate == null)
			return;
		Optional<ButtonType> result = showAlert(AlertType.CONFIRMATION, "Cancel Booking Confirmation", "Are you sure?",
				"");
		if (result.get() == ButtonType.OK) {
			adapter.deleteCustomerAndRooms(passport.getText(), roomDate.getRoom_number());
			bookingRooms.remove(roomDate);
			reloadTableView(bookingTable, bookingRooms);
		} else {
			// ... user chose CANCEL or closed the dialog

		}
	}
}