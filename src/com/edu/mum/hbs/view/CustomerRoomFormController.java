package com.edu.mum.hbs.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import com.edu.mum.hbs.dao.DaoFactoryImpl;
import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.dao.CustomerAndRoomDao;
import com.edu.mum.hbs.dao.CustomerDao;
import com.edu.mum.hbs.dao.RoomDao;
import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.RoomDate;
import com.edu.mum.hbs.notification.EmailNotificationObserver;
import com.edu.mum.hbs.notification.LoggingNotificationObserver;
import com.edu.mum.hbs.notification.NotificationObserver;
import com.edu.mum.hbs.notification.NotificationSubject;
import com.edu.mum.hbs.restapi.IRestAdapter;
import com.edu.mum.hbs.restapi.RestAdapter;
import com.edu.mum.hbs.restapi.RestAdapterProxy;
import com.edu.mum.hbs.util.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerRoomFormController extends ControllerBase implements NotificationSubject{
	@FXML	private TextField fullName;
	@FXML	private DatePicker dob;
	@FXML	private TextField passport;
	@FXML	private TextField address;
	@FXML	private TextField phoneNo;
	@FXML	private ChoiceBox<String> roomNumber;
	@FXML	private ChoiceBox<String> roomType;
	@FXML	private ChoiceBox<String> roomClass;
	@FXML	private Label lblRoomInfo;
	@FXML	private DatePicker checkInDate;
	@FXML	private DatePicker checkOutDate;

	@FXML	private TableView<RoomDate> roomTable;
	@FXML	private TableColumn<RoomDate, String> roomNumberColumn;
	@FXML	private TableColumn<RoomDate, String> priceColumn;
	@FXML	private TableColumn<RoomDate, String> roomTypeColumn;
	@FXML	private TableColumn<RoomDate, String> roomClassColumn;
	@FXML	private TableColumn<RoomDate, String> checkInDateColumn;
	@FXML	private TableColumn<RoomDate, String> checkOutDateColumn;

	private List<NotificationObserver> observers = new ArrayList<>();
	private static String STATUS = "Booking";
	IRestAdapter adapter = RestAdapterProxy.getRestProxy();
	private RoomDao rdao = (RoomDao) DaoFactoryImpl.getFactory().createDao(Room.TABLE_NAME);

	private List<RoomDate> rooms = new ArrayList<RoomDate>();
	private List<Room> availableRooms = new ArrayList<Room>();
	private List<String> stringRoomNumbers = new ArrayList<String>();
	private HashSet<String> stringRoomTypes = new HashSet<>();
	private HashSet<String> stringRoomClass = new HashSet<>();
	// for clearing form field
	@SuppressWarnings("unchecked")

//http://www.java2s.com/Tutorials/Java/JavaFX/0450__JavaFX_ChoiceBox.htm
//Example JavaFX ChoiceBox control backed by Database IDs
//  https://gist.github.com/jewelsea/1422104	
	
	@FXML
	public void addRoom() {
		if (roomNumber.getValue() == null || roomClass.getValue() == null 
				|| roomType.getValue() == null || checkInDate.getValue() == null
				|| checkOutDate.getValue() == null){
			showAlert(AlertType.INFORMATION, "Enter Values", null, "You have to fill all form data to continue.");
			return;
		}
		Room room = new Room();
		room.setRoomPriceByString(lblRoomInfo.getText());
		room.setRoom_number(roomNumber.getValue());
		room.setRoom_type(roomType.getValue());
		room.setRoom_class(roomClass.getValue());
		RoomDate roomDate = new RoomDate(room,checkInDate.getValue(), checkOutDate.getValue());

		roomNumberColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("room_number"));
		roomTypeColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("room_type"));
		roomClassColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("room_class"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("price"));
		checkInDateColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("checkInDate"));
		checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("checkOutDate"));
		
		rooms.add(roomDate);
		
        ObservableList<RoomDate> data = FXCollections.observableArrayList(rooms);
        roomTable.setItems(data);
        //ChoiceBox<String>[] roomFields = new ChoiceBox[] {roomNumber, roomType, roomClass };
        for (int i = 0; i < availableRooms.size(); i++){
        	if (availableRooms.get(i).getRoom_number().equals(room.getRoom_number())){
        		availableRooms.remove(i);
        		break;
        	}
        }
        stringRoomTypes.clear();
        stringRoomClass.clear();
        stringRoomNumbers.clear();
        for (Room r : availableRooms){
			stringRoomNumbers.add(r.getRoom_number());
			stringRoomTypes.add(r.getRoom_type());
			stringRoomClass.add(r.getRoom_class());
		}
        populateData2ChoiceBox(roomType, new ArrayList<>(stringRoomTypes));
        populateData2ChoiceBox(roomClass, new ArrayList<>(stringRoomClass));
        populateData2ChoiceBox(roomNumber, stringRoomNumbers);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
//		availableRooms = rdao.getAvailableRooms();
		availableRooms = adapter.getAvailableRooms();
		for (Room room : availableRooms){
			//stringRoomNumbers.add(room.getRoomNumber());
			stringRoomTypes.add(room.getRoom_type());
			//stringRoomClass.add(room.getRoomClass());
		}
		populateData2ChoiceBox(roomType, new ArrayList<>(stringRoomTypes));
		roomType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				//stringRoomNumbers.clear();
				stringRoomClass.clear();
				if ((int) newValue < 0){
					return;
				}
				for (Room room : availableRooms){
					if (room.getRoom_type().equals(roomType.getItems().get((int) newValue))){
						stringRoomClass.add(room.getRoom_class());
						//stringRoomNumbers.add(room.getRoomNumber());
					}
				}
				//populateData(roomNumber, stringRoomNumbers);
				populateData2ChoiceBox(roomClass, new ArrayList<>(stringRoomClass));
			}
			
		});
		
		roomNumber.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				if ((int) newValue < 0){
					return;
				}
				for (Room room : availableRooms){
					if (room.getRoom_number().equals(roomNumber.getItems().get((int) newValue))){
						lblRoomInfo.setText(room.getPrice()+"");
					}
				}
			}
			
		});
		
		roomClass.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				stringRoomNumbers.clear();
				if ((int)newValue <0) { 
					return;
				}
		for (Room room : availableRooms){
			if (room.getRoom_class().equals(roomClass.getItems().get((int) newValue))){
				//stringRoomClass.add(room.getRoomClass());
				stringRoomNumbers.add(room.getRoom_number());
			}
		}
		populateData2ChoiceBox(roomNumber, stringRoomNumbers);
	}

});

		//Register the Observers
		register(new EmailNotificationObserver());
		register(new LoggingNotificationObserver());
		}

private boolean checkNonEmptyCustomer(){
		if (fullName.getText().isEmpty() || passport.getText().isEmpty()){
			return false;
		}
		return true;
	}
	
	@FXML
	public void addCustomerRoom() {
		if (!checkNonEmptyCustomer()){
			showAlert(AlertType.INFORMATION, "Enter Values", null, "You have to fill all form data to continue.");
			return;
		}
		Customer customer = new Customer();
		customer.setFullName(fullName.getText());
		customer.setPassport_id(passport.getText());
		customer.setPhoneNo(phoneNo.getText());
		customer.setAddress(address.getText());
		customer.setDob(dob.getValue());
		
		adapter.addCustomer(customer);

		CustomerAndRoomDao customerAndRoomDao = (CustomerAndRoomDao) DaoFactoryImpl.getFactory().createDao(CustomerAndRoom.TABLE_NAME);
		customerAndRoomDao.addCustomerAndRooms(customer.getPassport_id(), rooms, STATUS);
		//Clear form for entering new Customer & Rooms
		Object[] customerFields = new Object[] { fullName, passport, address, phoneNo, roomTable, dob, checkInDate, checkOutDate };
		clearFormFields(customerFields);

		// Notify Observers
		if(getTotalPrice() >= Constants.MAX_ROOM_PRICE) {
			notifyObservers(customer, rooms);
		}
	}

	public void addCustomerAndChecking(){
		STATUS = "Checked";
		addCustomerRoom();
		STATUS = "Booking";
	}

	private double getTotalPrice(){
		double total =0;
		for(RoomDate room : rooms){
			total += room.getPrice();
		}

		return total;
	}
	@Override
	public void register(NotificationObserver observer) {
		if(observer != null && !observers.contains(observer)){
			observers.add(observer);
		}
	}

	@Override
	public void unregister(NotificationObserver observer) {
		if(observer != null && observers.contains(observer)){
			observers.remove(observer);
		}
	}

	@Override
	public void notifyObservers(Customer customer, List<RoomDate> rooms) {
		for(NotificationObserver observer : observers){
			observer.update(customer, rooms);
		}
	}
}
