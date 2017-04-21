package com.edu.mum.hbs.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.edu.mum.hbs.dao.DaoFactoryImpl;
import com.edu.mum.hbs.dao.CustomerAndRoomDao;
import com.edu.mum.hbs.entity.CustRoomDetails;
import com.edu.mum.hbs.entity.CustomerAndRoom;
import com.edu.mum.hbs.restapi.RestAdapter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReportCustomerRoomFormController extends ControllerBase{

	@FXML	private DatePicker fromDate;
	@FXML	private DatePicker toDate;

	@FXML	private TableView<CustRoomDetails> customerRoomTable;
	@FXML	private TableColumn<CustRoomDetails, String> customerNameColumn;
	@FXML	private TableColumn<CustRoomDetails, String> customerIDColumn;
	@FXML	private TableColumn<CustRoomDetails, String> roomNumberColumn;
	@FXML	private TableColumn<CustRoomDetails, String> checkInDateColumn;
	@FXML	private TableColumn<CustRoomDetails, String> checkOutDateColumn;
	@FXML	private TableColumn<CustRoomDetails, String> roomStatusColumn;

	private static RestAdapter adapter = new RestAdapter();
	private List<CustRoomDetails> custRoomDetails = new ArrayList<CustRoomDetails>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fromDate.setValue(LocalDate.now().minusDays(30));
		toDate.setValue(LocalDate.now());
	}

	@FXML
	public void lookupInfo() {
		if (fromDate.getValue() == null || toDate.getValue() == null){
			showAlert(AlertType.INFORMATION, "Enter Dates", null, "You have to fill From Date and To Date to continue.");
			return;
		}
		CustomerAndRoomDao crDao = (CustomerAndRoomDao) DaoFactoryImpl.getFactory().createDao(CustomerAndRoom.TABLE_NAME);
		
		String szFromDate = fromDate.getValue().toString();
		String szToDate = toDate.getValue().toString();
		
//		custRoomDetails = crDao.getCustomerRoomFullFromToDate(szFromDate, szToDate);
		custRoomDetails = adapter.getCustomerRoomFullFromToDate(szFromDate, szToDate);

		customerNameColumn.setCellValueFactory(new PropertyValueFactory<CustRoomDetails, String>("fullName"));
		customerIDColumn.setCellValueFactory(new PropertyValueFactory<CustRoomDetails, String>("passportOrId"));
		roomNumberColumn.setCellValueFactory(new PropertyValueFactory<CustRoomDetails, String>("roomNumber"));
		checkInDateColumn.setCellValueFactory(new PropertyValueFactory<CustRoomDetails, String>("checkInDate"));
		checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<CustRoomDetails, String>("checkOutDate"));
		roomStatusColumn.setCellValueFactory(new PropertyValueFactory<CustRoomDetails, String>("status"));
		
        reloadTableView(customerRoomTable, custRoomDetails);
	}

	public void resetSearch(){
		DatePicker[] dateFields = new DatePicker[] { fromDate, toDate };	
        clearFormFields(dateFields);
        clearFormFields(customerRoomTable);
	}

}
