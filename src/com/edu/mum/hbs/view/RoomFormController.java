package com.edu.mum.hbs.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.restapi.IRestAdapter;
import com.edu.mum.hbs.restapi.RestAdapter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RoomFormController extends ControllerBase {
	@FXML
	private TextField roomNumber;
	@FXML
	private TextField roomType;
	@FXML
	private TextField roomClass;
	@FXML
	private TextField roomPrice;

	@FXML
	private TableView<Room> roomTable;
	@FXML
	private TableColumn<Room, String> roomNumberColumn;
	@FXML
	private TableColumn<Room, String> roomPriceColumn;
	@FXML
	private TableColumn<Room, String> roomTypeColumn;
	@FXML
	private TableColumn<Room, String> roomClassColumn;

	private List<Room> rooms = new ArrayList<Room>();
	IRestAdapter adapter = RestAdapter.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Room> listRooms = adapter.getAllRooms();

		roomNumberColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("room_number"));
		roomTypeColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("room_type"));
		roomClassColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("room_class"));
		roomPriceColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("price"));
		rooms.addAll(listRooms);
		reloadTableView(roomTable, listRooms);
        roomTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Room>() {
            @Override
            public void changed(ObservableValue<? extends Room> observable, Room oldValue, Room newValue) {
                if (newValue == null) return;
                roomNumber.setText(newValue.getRoom_number());
                roomClass.setText(newValue.getRoom_class());
                roomType.setText(newValue.getRoom_type());
                roomPrice.setText(String.valueOf(newValue.getPrice()));
            }
        });
	}

	@FXML
	public void addRoom() {
		if (roomNumber.getText().isEmpty() || roomClass.getText().isEmpty() || roomType.getText().isEmpty()) {
			showAlert(AlertType.INFORMATION, "Enter Values", null, "You have to fill all form data to continue.");
			return;
		}
		Room room = new Room();

		room.setRoomPriceByString(roomPrice.getText());
		room.setRoom_number(roomNumber.getText());
		room.setRoom_type(roomType.getText());
		room.setRoom_class(roomClass.getText());

		adapter.addRoom(room);

		// Add newest added room to the list
		rooms.add(room);
		reloadTableView(roomTable, rooms);
		TextField[] roomFields = new TextField[] { roomNumber, roomType, roomClass, roomPrice };
		clearFormFields(roomFields);
	}

	@FXML
	public void updateRoom() {
		if (roomNumber.getText().isEmpty() || roomClass.getText().isEmpty() || roomType.getText().isEmpty()) {
			showAlert(AlertType.INFORMATION, "Enter Values", null, "You have to fill all form data to continue.");
			return;
		}
		Room roomSelected = roomTable.getSelectionModel().getSelectedItem();
		if (roomSelected == null)
			return;
		Room room = new Room();

		room.setRoomPriceByString(roomPrice.getText());
		room.setRoom_number(roomNumber.getText());
		room.setRoom_type(roomType.getText());
		room.setRoom_class(roomClass.getText());
//        Room roomExistence = rdao.getRoom(room.getRoomNumber());
		Room roomExistence =adapter.getRoom(room.getRoom_number());

		if (roomExistence == null) {
			adapter.addRoom(room);

			rooms.add(room);
		} else {
			adapter.updateRoom(room);
			int roomSelectedInList = roomTable.getSelectionModel().getSelectedIndex();
			// Replace the newest room info to the list
			rooms.set(roomSelectedInList, room);
		}
		reloadTableView(roomTable, rooms);
		TextField[] roomFields = new TextField[] { roomNumber, roomType, roomClass, roomPrice };
		clearFormFields(roomFields);
	}

	@FXML
	public void deleteRoom() {
		// int roomSelectedInList = 0;
		Room room = roomTable.getSelectionModel().getSelectedItem();
		if (room == null) {
			showAlert(AlertType.INFORMATION, "Enter Values", null, "Please select one row to continue.");
			return;
		}

		adapter.deleteRoom(room);
		rooms.remove(room);
		TextField[] roomFields = new TextField[] { roomNumber, roomType, roomClass, roomPrice };
		// Clear form for entering new Rooms
		clearFormFields(roomFields);
		reloadTableView(roomTable, rooms);
		// show success msg
	}

}