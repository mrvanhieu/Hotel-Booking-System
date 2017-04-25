package com.edu.mum.hbs.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.InvoiceRecordBuilder;
import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.entity.RoomDate;
import com.edu.mum.hbs.entity.RoomService;
import com.edu.mum.hbs.entity.StandardStrategy;
import com.edu.mum.hbs.entity.StrategyContext;
import com.edu.mum.hbs.entity.VIPStrategy;
import com.edu.mum.hbs.restapi.IRestAdapter;
import com.edu.mum.hbs.restapi.RestAdapterProxy;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class CheckoutProcessorController extends ControllerBase {
	@FXML
	private TextField searchText;
	@FXML
	private TextField fullName;
	@FXML
    TextField passport;
	@FXML
	private TextField phoneNo;

	@FXML
	TableView<RoomDate> checkedTable;
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

	private List<RoomDate> checkedRooms = new ArrayList<RoomDate>();


    @FXML
    public final void checkOut() {
        if (checkoutValidation()) {
            RoomDate roomDate = checkedTable.getSelectionModel().getSelectedItem();
            RoomDate clonedRoomDate = (RoomDate) roomDate.doClone();
            List<RoomService> roomServices = adapter.getAllRoomServicesByRoomNumber(clonedRoomDate.getRoom_number());
            InvoiceRecord invoiceRecord = processInvoice(clonedRoomDate, roomServices);
            processRoomService(clonedRoomDate);
            checkedRooms.remove(roomDate);
            reloadTableView(checkedTable, checkedRooms);
            showInvoiceData(invoiceRecord, roomServices);
        }
    }

    public abstract boolean checkoutValidation();
    public abstract InvoiceRecord processInvoice(RoomDate roomdate, List<RoomService> roomServices);
    public abstract void processRoomService(RoomDate roomdate);

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
				CustomerAndRoom.CHECKED_STATUS);
		List<RoomDate> roomDates = new ArrayList<>();
		for (CustomerAndRoom cr : customerAndRooms) {
			Room r = adapter.getRoom(cr.getRoomNumber());
			RoomDate rd = new RoomDate(r, cr.getCheckInDate(), cr.getCheckOutDate());
			roomDates.add(rd);
		}

		checkedRooms.addAll(roomDates);
		reloadTableView(checkedTable, roomDates);
	}

	boolean checkNonEmptyCustomer() {
		if (fullName.getText().isEmpty() || passport.getText().isEmpty()) {
			return false;
		}
		return true;
	}


    public void showInvoiceData(InvoiceRecord invoiceRecord, List<RoomService> roomServices) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("InvoiceFormPopup.fxml").openStream());
			InvoiceFormPopupController controller = (InvoiceFormPopupController) loader.getController();
			controller.viewData(invoiceRecord, roomServices);
			Scene scene = new Scene(root);
			scene.getStylesheets().setAll(getClass().getResource("style.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}