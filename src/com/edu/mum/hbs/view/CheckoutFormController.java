package com.edu.mum.hbs.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.edu.mum.hbs.dao.DaoFactoryImpl;
import com.edu.mum.hbs.dao.RoomDao;
import com.edu.mum.hbs.dao.RoomServiceDao;
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

public class CheckoutFormController extends ControllerBase{
	@FXML	private TextField searchText;
	@FXML	private TextField fullName;
	@FXML	private TextField passport;
	@FXML	private TextField phoneNo;
	
	@FXML	private TableView<RoomDate> checkedTable;
	@FXML	private TableColumn<RoomDate, String> roomNumberColumn;
	@FXML	private TableColumn<RoomDate, String> priceColumn;
	@FXML	private TableColumn<RoomDate, String> roomTypeColumn;
	@FXML	private TableColumn<RoomDate, String> roomClassColumn;
	@FXML	private TableColumn<RoomDate, String> checkInDateColumn;
	@FXML	private TableColumn<RoomDate, String> checkOutDateColumn;

	IRestAdapter adapter = RestAdapterProxy.getRestProxy();

	private RoomDao rdao = (RoomDao) DaoFactoryImpl.getFactory().createDao(Room.TABLE_NAME);

	private List<RoomDate> checkedRooms = new ArrayList<RoomDate>();
		
	@FXML	public void searchCustomer() {
		String szSearch = searchText.getText();
		if (szSearch == null || szSearch.equals("")){
			showAlert(AlertType.INFORMATION, "Enter Values", null, "You have to fill in search content to continue.");
			return;
		}
		
		Customer customer = adapter.getCustomerFromPassportOrPhone(szSearch);
		
		if (customer == null) {
			//Show error msg
			fullName.setText("");
			passport.setText("");
			phoneNo.setText("");			
			showAlert(AlertType.INFORMATION, "Cannot Find", null, "We can not find any data matching with your criteria. Please try with another one");
			return;
		}

		roomNumberColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("roomNumber"));
		roomTypeColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("roomType"));
		roomClassColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("roomClass"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("roomPrice"));
		checkInDateColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("checkInDate"));
		checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<RoomDate, String>("checkOutDate"));
				
		//If found a customer
		
		//0. Reset search field for next search
		searchText.setText("");
		fullName.setText(customer.getFullName());
		passport.setText(customer.getPassportOrId());
		phoneNo.setText(customer.getPhoneNo());
		
		//1. getCustomerAndRoom
//		List<CustomerAndRoom> customerAndRooms = crdao.getCustomerAndRoom(customer.getPassportOrId(), CustomerAndRoom.CHECKED_STATUS);
		List<CustomerAndRoom> customerAndRooms = adapter.getCustomerAndRoom(customer.getPassportOrId(), CustomerAndRoom.CHECKED_STATUS);
		List<RoomDate> roomDates = new ArrayList<>();
		for (CustomerAndRoom cr : customerAndRooms){
			//Room r = rdao.getRoom(cr.getRoomNumber());
			Room r =adapter.getRoom(cr.getRoomNumber());
			RoomDate rd = new RoomDate(r,cr.getCheckInDate(),cr.getCheckOutDate());
			roomDates.add(rd);
		}

		checkedRooms.addAll(roomDates);
		reloadTableView(checkedTable, roomDates);
	}

	private boolean checkNonEmptyCustomer(){
		if (fullName.getText().isEmpty() || passport.getText().isEmpty()){
			return false;
		}
		return true;
	}

	@FXML
	public void checkOut() {

		if (!checkNonEmptyCustomer()){
			showAlert(AlertType.INFORMATION, "Enter Values", null, "You have to fill all form data to continue.");
			return;
		}
		RoomDate roomDate = checkedTable.getSelectionModel().getSelectedItem();
		if (roomDate == null) {
			showAlert(AlertType.INFORMATION, "Enter Values", null, "Please select one row to continue.");
			return;
		}
		Optional<ButtonType> result = showAlert(AlertType.CONFIRMATION,"Check Out Confirmation","Are you sure?","");
		if (result.get() == ButtonType.OK){
			RoomDate clonedRoomDate = (RoomDate)roomDate.doClone();
			RoomServiceDao rsDao = (RoomServiceDao) DaoFactoryImpl.getFactory().createDao(RoomService.TABLE_NAME);
			
			InvoiceRecordBuilder invoiceRecordBuilder = new InvoiceRecordBuilder();
			invoiceRecordBuilder.buildPassportOrId(passport.getText());
			invoiceRecordBuilder.buildRoomNumber(clonedRoomDate.getRoomNumber());
			LocalDate checkInDate = clonedRoomDate.getCheckInDate();
			LocalDate checkOutDate =  clonedRoomDate.getCheckOutDate();
			int days = Period.between(checkInDate,checkOutDate).getDays();
			double roomAmount = clonedRoomDate.getRoomPrice()*days;

			// double serviceAmount = rsDao.getTotalUsingService(clonedRoomDate.getRoomNumber());
			List<RoomService> roomServices = adapter.getAllRoomServicesByRoomNumber(clonedRoomDate.getRoomNumber());
			double serviceAmount = 0.0;
			String roomClass = clonedRoomDate.getRoomClass();
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
				
      adapter.deleteRoomServiceByString(clonedRoomDate.getRoomNumber());
			adapter.addInvoice(invoiceRecord);
			//crdao.delete(passport.getText(),clonedRoomDate.getRoomNumber());
			adapter.deleteCustomerAndRooms(passport.getText(),clonedRoomDate.getRoomNumber());
			checkedRooms.remove(roomDate);
			reloadTableView(checkedTable, checkedRooms);
			showInvoiceData(invoiceRecord,roomServices);
		} else {
			// ... user chose CANCEL or closed the dialog

		}
	}
	
	public void showInvoiceData(InvoiceRecord invoiceRecord, List<RoomService> roomServices){
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("InvoiceFormPopup.fxml").openStream());
			InvoiceFormPopupController controller = (InvoiceFormPopupController) loader.getController();
			controller.viewData(invoiceRecord,roomServices);
			Scene scene = new Scene(root);
			scene.getStylesheets().setAll(
					getClass().getResource("style.css").toExternalForm()
			);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
