package com.edu.mum.hbs.view;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;

import com.edu.mum.hbs.dao.DaoFactoryImpl;
import com.edu.mum.hbs.dao.InvoiceRecordDao;
import com.edu.mum.hbs.dao.RoomDao;
import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.OccupancyVisitor;
import com.edu.mum.hbs.entity.ProfitVisitor;
import com.edu.mum.hbs.entity.Revenue;
import com.edu.mum.hbs.entity.Room;
import com.edu.mum.hbs.restapi.IRestAdapter;
import com.edu.mum.hbs.restapi.RestAdapter;

import com.edu.mum.hbs.restapi.IRestAdapter;
import com.edu.mum.hbs.restapi.RestAdapter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReportRevenueFormController extends ControllerBase {

	@FXML
	private DatePicker fromDate;
	@FXML
	private DatePicker toDate;

	@FXML
	private Label lblSumInfo;
	@FXML
	private Label lblSum;
	@FXML	private Label lblOccupancyInfo;
	@FXML	private Label lblOccupancyRate;

	@FXML
	private TableView<Revenue> revenueTable;
	@FXML
	private TableColumn<Revenue, String> customerNameColumn;
	@FXML
	private TableColumn<Revenue, String> customerPhoneColumn;
	@FXML
	private TableColumn<Revenue, String> roomNumberColumn;
	@FXML
	private TableColumn<Revenue, String> checkInDateColumn;
	@FXML
	private TableColumn<Revenue, String> checkOutDateColumn;
	@FXML
	private TableColumn<Revenue, String> roomAmountColumn;
	@FXML
	private TableColumn<Revenue, String> usedServicesColumn;
	@FXML
	private TableColumn<Revenue, String> totalAmountColumn;

	private List<Revenue> revenues;
	IRestAdapter adapter = RestAdapter.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fromDate.setValue(LocalDate.now().minusDays(30));
		toDate.setValue(LocalDate.now());
	}

	@FXML
	public void lookupInfo() {
		if (fromDate.getValue() == null || toDate.getValue() == null) {
			showAlert(AlertType.INFORMATION, "Enter Dates", null,
					"You have to fill From Date and To Date to continue.");
			return;
		}
		customerNameColumn.setCellValueFactory(new PropertyValueFactory<Revenue, String>("fullName"));
		customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<Revenue, String>("phoneNo"));
		roomNumberColumn.setCellValueFactory(new PropertyValueFactory<Revenue, String>("roomNumber"));
		checkInDateColumn.setCellValueFactory(new PropertyValueFactory<Revenue, String>("checkInDate"));
		checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<Revenue, String>("checkOutDate"));
		roomAmountColumn.setCellValueFactory(new PropertyValueFactory<Revenue, String>("roomAmountDollar"));
		usedServicesColumn.setCellValueFactory(new PropertyValueFactory<Revenue, String>("serviceAmountDollar"));
		totalAmountColumn.setCellValueFactory(new PropertyValueFactory<Revenue, String>("totalAmountDollar"));

		String szFromDate = fromDate.getValue().toString();
		String szToDate = toDate.getValue().toString();
		revenues = adapter.getAllRevenueRecordsFromToDate(szFromDate, szToDate);
		RoomDao rooms = (RoomDao) DaoFactoryImpl.getFactory().createDao(Room.TABLE_NAME);
		Period period = Period.between(fromDate.getValue(), toDate.getValue());
		reloadTableView(revenueTable, revenues);

        ProfitVisitor profitCalc = new ProfitVisitor();
        OccupancyVisitor occupancy = new OccupancyVisitor();
        
		double dRevenue = 0.0;
		if (revenues.size() > 0) {
			for (Revenue revenue : revenues) {
				revenue.accept(profitCalc);
				revenue.accept(occupancy);
			}
		}
		lblSumInfo.setText("From " + szFromDate + " to " + szToDate + ", the total revenue is:");
		lblSum.setText("$" + profitCalc.getTotalProfit());
        lblOccupancyInfo.setText("From " + szFromDate + " to " + szToDate + ", the occupancy rate is: ");
        //lblOccupancyRate.setText(occupancy.getOccupancyDays()*100/(rooms.getAllRooms().size()*period.getDays()) + "%");
		lblOccupancyRate.setText(occupancy.getOccupancyDays()*100/(adapter.getAllRooms().size()*period.getDays()) + "%");
	}

	public void resetSearch() {
		DatePicker[] dateFields = new DatePicker[] { fromDate, toDate };
		clearFormFields(dateFields);
		clearFormFields(revenueTable);
		lblSumInfo.setText("From xxx to yyy, the total revenue is:");
		lblSum.setText("$...");
	}

}
