package com.edu.mum.hbs.view;

import java.io.IOException;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.edu.mum.hbs.dao.CustomerAndRoomDao;
import com.edu.mum.hbs.dao.DaoFactory;
import com.edu.mum.hbs.dao.InvoiceRecordDao;
import com.edu.mum.hbs.dao.RoomDao;
import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.entity.RoomService;
import com.edu.mum.hbs.dao.CustomerDao;
import com.edu.mum.hbs.dao.RoomServiceDao;
import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.RoomDate;
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


	private CustomerDao cdao = (CustomerDao) DaoFactory.getDaoFactory(Customer.TABLE_NAME);
	private CustomerAndRoomDao crdao = (CustomerAndRoomDao) DaoFactory.getDaoFactory(CustomerAndRoom.TABLE_NAME);
	private RoomDao rdao = (RoomDao) DaoFactory.getDaoFactory(Room.TABLE_NAME);

	private List<RoomDate> checkedRooms = new ArrayList<RoomDate>();
		
	@FXML	public void searchCustomer() {
		String szSearch = searchText.getText();
		if (szSearch == null || szSearch.equals("")){
			showAlert(AlertType.INFORMATION, "Enter Values", null, "You have to fill in search content to continue.");
			return;
		}
		
		Customer customer = cdao.getCustomerFromPassportOrPhone(szSearch);
		
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
		List<CustomerAndRoom> customerAndRooms = crdao.getCustomerAndRoom(customer.getPassportOrId(), CustomerAndRoom.CHECKED_STATUS);
		List<RoomDate> roomDates = new ArrayList<>();
		for (CustomerAndRoom cr : customerAndRooms){
			Room r = rdao.getRoom(cr.getRoomNumber());
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
			RoomServiceDao rsDao = (RoomServiceDao) DaoFactory.getDaoFactory(RoomService.TABLE_NAME);
			
			InvoiceRecord invoiceRecord = new InvoiceRecord();
			invoiceRecord.setPassportOrId(passport.getText());
			invoiceRecord.setRoomNumber(roomDate.getRoomNumber());
			invoiceRecord.setCheckInDate(roomDate.getCheckInDate());
			invoiceRecord.setCheckOutDate(roomDate.getCheckOutDate());
			int days = Period.between(invoiceRecord.getCheckInDate(),invoiceRecord.getCheckOutDate()).getDays();
			invoiceRecord.setRoomAmount(roomDate.getRoomPrice()*days);
			invoiceRecord.setServiceAmount(rsDao.getTotalUsingService(roomDate.getRoomNumber()));
			invoiceRecord.setTotalAmount(invoiceRecord.getRoomAmount() + invoiceRecord.getServiceAmount());
			InvoiceRecordDao irDao = (InvoiceRecordDao) DaoFactory.getDaoFactory(InvoiceRecord.TABLE_NAME);
			List<RoomService> roomServices = rsDao.getAllRoomService(roomDate.getRoomNumber());
			rsDao.delete(roomDate.getRoomNumber());
			irDao.addInvoice(invoiceRecord);
			crdao.delete(passport.getText(),roomDate.getRoomNumber());
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