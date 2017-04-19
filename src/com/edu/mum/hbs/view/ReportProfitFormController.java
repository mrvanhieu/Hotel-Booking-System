package com.edu.mum.hbs.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.edu.mum.hbs.dao.DaoFactoryImpl;
import com.edu.mum.hbs.dao.InvoiceRecordDao;
import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.Profit;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReportProfitFormController extends ControllerBase{

	@FXML	private DatePicker fromDate;
	@FXML	private DatePicker toDate;

	@FXML	private Label lblSumInfo;
	@FXML	private Label lblSum;

	@FXML	private TableView<Profit> profitTable;
	@FXML	private TableColumn<Profit, String> customerNameColumn;
	@FXML	private TableColumn<Profit, String> customerPhoneColumn;
	@FXML	private TableColumn<Profit, String> roomNumberColumn;
	@FXML	private TableColumn<Profit, String> checkInDateColumn;
	@FXML	private TableColumn<Profit, String> checkOutDateColumn;
	@FXML	private TableColumn<Profit, String> roomAmountColumn;
	@FXML	private TableColumn<Profit, String> usedServicesColumn;
	@FXML	private TableColumn<Profit, String> totalAmountColumn;


	private List<Profit> profits;

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
		customerNameColumn.setCellValueFactory(new PropertyValueFactory<Profit, String>("fullName"));
		customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<Profit, String>("phoneNo"));
		roomNumberColumn.setCellValueFactory(new PropertyValueFactory<Profit, String>("roomNumber"));
		checkInDateColumn.setCellValueFactory(new PropertyValueFactory<Profit, String>("checkInDate"));
		checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<Profit, String>("checkOutDate"));
		roomAmountColumn.setCellValueFactory(new PropertyValueFactory<Profit, String>("roomAmountDollar"));
		usedServicesColumn.setCellValueFactory(new PropertyValueFactory<Profit, String>("serviceAmountDollar"));
		totalAmountColumn.setCellValueFactory(new PropertyValueFactory<Profit, String>("totalAmountDollar"));
		
		String szFromDate = fromDate.getValue().toString();
		String szToDate = toDate.getValue().toString();
		InvoiceRecordDao irDao = (InvoiceRecordDao) DaoFactoryImpl.getFactory().createDao(InvoiceRecord.TABLE_NAME);
		profits = irDao.getAllProfitRecordsFromToDate(szFromDate, szToDate);

        reloadTableView(profitTable, profits);
        
        double dProfit = 0.0;
        if (profits.size() > 0) {
        	for (Profit profit : profits) {
        		dProfit += profit.getTotalAmount();
        	}
        }
        lblSumInfo.setText("From " + szFromDate + " to " + szToDate + ", the total profit is:");
        lblSum.setText("$" + dProfit);        
	}

	public void resetSearch(){
		DatePicker[] dateFields = new DatePicker[] { fromDate, toDate };	
        clearFormFields(dateFields);
        clearFormFields(profitTable);
        lblSumInfo.setText("From xxx to yyy, the total profit is:");
        lblSum.setText("$...");
	}

}
