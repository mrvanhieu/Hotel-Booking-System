package com.edu.mum.hbs.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.edu.mum.hbs.entity.RoomService;
import com.edu.mum.hbs.restapi.IRestAdapter;
import com.edu.mum.hbs.restapi.RestAdapterProxy;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RoomServiceFormController extends ControllerBase {
	@FXML
	private ChoiceBox<String> roomNumber;
	@FXML
	private ChoiceBox<String> serviceDesc;
	@FXML
	private ChoiceBox<String> quantity;
	@FXML
	private DatePicker serviceDate;

	@FXML
	private TableView<RoomService> roomServiceTable;
	@FXML
	private TableColumn<RoomService, String> roomNumberColumn;
	@FXML
	private TableColumn<RoomService, String> serviceDescColumn;
	@FXML
	private TableColumn<RoomService, String> quantityColumn;
	@FXML
	private TableColumn<RoomService, String> serviceDateColumn;

	// For populate information into 02 ChoiceBox

	private IRestAdapter adapter = RestAdapterProxy.getRestProxy();

	// For adding/updating and removing data in DB
	private List<RoomService> roomServices = new ArrayList<RoomService>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		roomNumberColumn.setCellValueFactory(new PropertyValueFactory<RoomService, String>("roomNumber"));
		serviceDescColumn.setCellValueFactory(new PropertyValueFactory<RoomService, String>("serviceDesc"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<RoomService, String>("quantity"));
		serviceDateColumn.setCellValueFactory(new PropertyValueFactory<RoomService, String>("serviceDate"));

		// Get current roomServices from DB and show into TableView
		roomServices = adapter.getAllRoomServices();
		reloadTableView(roomServiceTable, roomServices);
		roomServiceTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RoomService>() {
			@Override
			public void changed(ObservableValue<? extends RoomService> observable, RoomService oldValue,
					RoomService newValue) {
				if (newValue == null)
					return;
				roomNumber.setValue(newValue.getRoomNumber());
				serviceDesc.setValue(newValue.getServiceDesc());
				quantity.setValue(String.valueOf(newValue.getQuantity()));
				serviceDate.setValue(newValue.getServiceDate());
			}
		});

		List<String> usedRooms = new ArrayList<String>();
		// Add an empty value to the ChoiceBox
		usedRooms.add("");
		// Append all rooms which are in "checked-in" status
		usedRooms.addAll(adapter.getUsedRooms("Checked"));
		populateData2ChoiceBox(roomNumber, usedRooms);

		List<String> listServiceDesc = new ArrayList<String>();
		// Add an empty value to the ChoiceBox
		listServiceDesc.add("");
		listServiceDesc.addAll(adapter.loadServicesDesc());
		populateData2ChoiceBox(serviceDesc, listServiceDesc);

		List<String> quantityList = new ArrayList<String>();
		// Add an empty value to the ChoiceBox
		quantityList.add("");
		for (int i = 1; i < 10; i++) {
			quantityList.add(i + "");
		}
		populateData2ChoiceBox(quantity, quantityList);

		serviceDate.setValue(LocalDate.now());
	}

	@FXML
	public void addRoomService() {
		if (roomNumber.getValue() == null || serviceDesc.getValue() == null || quantity.getValue() == null
				|| serviceDate.getValue() == null || roomNumber.getValue().isEmpty() || serviceDesc.getValue().isEmpty()
				|| quantity.getValue().isEmpty()) {
			showAlert(AlertType.INFORMATION, "Enter Values", null, "You have to fill all form data to continue.");
			return;
		}

		RoomService roomService = new RoomService();

		roomService.setRoomNumber(roomNumber.getValue());
		roomService.setServiceDesc(serviceDesc.getValue());
		roomService.setQuantityByString(quantity.getValue());
		roomService.setServiceDate(serviceDate.getValue());

		// save current roomService data to DB
		adapter.addRoomService(roomService);

		// Add newest added roomService to the list
		roomServices.add(roomService);
		reloadTableView(roomServiceTable, roomServices);

		// for clearing form fields
		Object[] roomServiceFields = new Object[] { serviceDesc, roomNumber, quantity };
		clearFormFields(roomServiceFields);
	}

	@FXML
	public void updateRoomService() {
		RoomService roomServiceSelected = roomServiceTable.getSelectionModel().getSelectedItem();
		if (roomServiceSelected == null) {
			showAlert(AlertType.INFORMATION, "Select room service", null,
					"You have to select a room's service in table to continue.");
			return;
		}

		if (!roomServiceSelected.getRoomNumber().equals(roomNumber.getValue())
				|| !roomServiceSelected.getServiceDesc().equals(serviceDesc.getValue())
				|| !roomServiceSelected.getServiceDate().equals(serviceDate.getValue())) {

			showAlert(AlertType.INFORMATION, "Wrong values are entered", null,
					"You can only change Quantity value for updating.");
			roomNumber.setValue(roomServiceSelected.getRoomNumber());
			serviceDesc.setValue(roomServiceSelected.getServiceDesc());
			serviceDate.setValue(roomServiceSelected.getServiceDate());
			return;
		}
		if (quantity.getValue() == null || quantity.getValue().isEmpty()) {
			showAlert(AlertType.INFORMATION, "Wrong value is entered", null, "Quantity value cannot be empty.");
			quantity.setValue(roomServiceSelected.getQuantity() + "");
			return;
		}

		RoomService roomService = new RoomService();

		roomService.setRoomNumber(roomNumber.getValue());
		roomService.setServiceDesc(serviceDesc.getValue());
		roomService.setQuantityByString(quantity.getValue());
		roomService.setServiceDate(serviceDate.getValue());

		// save current service data to DB
		adapter.updateRoomService(roomService);

		// Replace the newest service info to the list
		int roomServiceSelectedInList = roomServiceTable.getSelectionModel().getSelectedIndex();
		roomServices.set(roomServiceSelectedInList, roomService);
		reloadTableView(roomServiceTable, roomServices);

		// for clearing form fields
		Object[] roomServiceFields = new Object[] { serviceDesc, roomNumber, quantity };
		clearFormFields(roomServiceFields);
	}

	@FXML
	public void deleteRoomService() {
		RoomService roomServiceSelected = roomServiceTable.getSelectionModel().getSelectedItem();
		if (roomServiceSelected == null) {
			showAlert(AlertType.INFORMATION, "Select room service", null,
					"You have to select a room's service in table to continue.");
			return;
		}

		// delete in DB base on the serviceDesc's value
		adapter.deleteRoomService(roomServiceSelected);

		// get selected service from table view,
		int roomServiceSelectedInList = roomServiceTable.getSelectionModel().getSelectedIndex();
		roomServices.remove(roomServiceSelectedInList);
		reloadTableView(roomServiceTable, roomServices);

		// Clear form for entering new service
		Object[] roomServiceFields = new Object[] { serviceDesc, roomNumber, quantity };
		clearFormFields(roomServiceFields);
	}

}
