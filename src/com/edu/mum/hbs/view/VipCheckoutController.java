package com.edu.mum.hbs.view;

import com.edu.mum.hbs.entity.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

public class VipCheckoutController extends CheckoutProcessorController {
	public void processRoomService(RoomDate roomdate){
		adapter.deleteRoomServiceByString(roomdate.getRoom_number());
		adapter.deleteCustomerAndRooms(passport.getText(), roomdate.getRoom_number());
	}

	public InvoiceRecord processInvoice(RoomDate roomdate, List<RoomService> roomServices){
		InvoiceRecordBuilder invoiceRecordBuilder = new InvoiceRecordBuilder();
		invoiceRecordBuilder.buildPassportOrId(passport.getText());
		invoiceRecordBuilder.buildRoomNumber(roomdate.getRoom_number());
		LocalDate checkInDate = roomdate.getCheckInDate();
		LocalDate checkOutDate = roomdate.getCheckOutDate();
		int days = Period.between(checkInDate, checkOutDate).getDays();
		double roomAmount = roomdate.getPrice() * days;

		double serviceAmount = 0.0;
		String roomClass = roomdate.getRoom_class();
		StrategyContext strategyContext;
		if (roomClass.equalsIgnoreCase("VIP")) {
			strategyContext = new StrategyContext(new VIPStrategy());
		} else {
			strategyContext = new StrategyContext(new StandardStrategy());
		}
		serviceAmount = strategyContext.getRoomServiceAmount(roomServices);
		invoiceRecordBuilder.buildCheckInDate(checkInDate);
		invoiceRecordBuilder.buildCheckOutDate(checkOutDate);
		invoiceRecordBuilder.buildRoomAmount(roomAmount);
		invoiceRecordBuilder.buildServiceAmount(serviceAmount);
		invoiceRecordBuilder.buildTotalAmount(roomAmount + serviceAmount);
		InvoiceRecord invoiceRecord = invoiceRecordBuilder.getInvoiceRecord();
		adapter.addInvoice(invoiceRecord);
		return invoiceRecordBuilder.getInvoiceRecord();
	}

	public boolean checkoutValidation() {
		if (!checkNonEmptyCustomer()) {
			showAlert(AlertType.INFORMATION, "Enter Values", null, "You have to fill all form data to continue.");
			return false;
		}
		RoomDate roomDate = checkedTable.getSelectionModel().getSelectedItem();
		if (roomDate == null) {
			showAlert(AlertType.INFORMATION, "Enter Values", null, "Please select one row to continue.");
			return false;
		}
		Optional<ButtonType> result = showAlert(AlertType.CONFIRMATION, "Check Out Confirmation", "Are you sure?", "");
		if (result.get() == ButtonType.OK) {
			return true;
		}
		return false;
	}
}