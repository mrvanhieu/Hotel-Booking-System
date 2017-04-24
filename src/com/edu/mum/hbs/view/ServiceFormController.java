package com.edu.mum.hbs.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.edu.mum.hbs.entity.Service;
import com.edu.mum.hbs.restapi.IRestAdapter;
import com.edu.mum.hbs.restapi.RestAdapterProxy;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ServiceFormController extends ControllerBase {
	@FXML
	private TextField serviceDesc;
	@FXML
	private TextField servicePrice;

	@FXML
	private TableView<Service> serviceTable;
	@FXML
	private TableColumn<Service, String> serviceDescColumn;
	@FXML
	private TableColumn<Service, String> servicePriceColumn;

	private IRestAdapter adapter = RestAdapterProxy.getRestProxy();

	private List<Service> services = new ArrayList<Service>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Service> listServices = adapter.loadServices();
		if (listServices != null) {

			serviceDescColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("serviceDesc"));
			servicePriceColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("servicePrice"));

			services.addAll(listServices);
			reloadTableView(serviceTable, listServices);
			serviceTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Service>() {
				@Override
				public void changed(ObservableValue<? extends Service> observable, Service oldValue, Service newValue) {
					if (newValue == null) return;
					serviceDesc.setText(newValue.getService_desc());
					servicePrice.setText(String.valueOf(newValue.getService_price()));
				}
			});
		}

	}

	@FXML
	public void addService() {
		if (serviceDesc.getText().isEmpty() || servicePrice.getText().isEmpty()) {
			showAlert(AlertType.INFORMATION, "Enter Values", null, "You have to fill all form data to continue.");
			return;
		}

		Service service = new Service();
		
		service.setService_desc(serviceDesc.getText());
		service.setServicePriceByString(servicePrice.getText());
		adapter.addService(service);

		// Add newest added service to the list
		services.add(service);
		reloadTableView(serviceTable, services);

		// for clearing form fields
		TextField[] serviceFields = new TextField[] { serviceDesc, servicePrice };
		clearFormFields(serviceFields);
	}

	@FXML
	public void updateService() {
		if (serviceDesc.getText().isEmpty() || servicePrice.getText().isEmpty()) {
			showAlert(AlertType.INFORMATION, "Enter Values", null, "You have to fill all form data to continue.");
			return;
		}

		Service serviceSelected = serviceTable.getSelectionModel().getSelectedItem();
		if (serviceSelected == null)
			return;

		Service service = new Service();
		
		service.setService_desc(serviceDesc.getText());
		service.setServicePriceByString(servicePrice.getText());

		Service serviceExistence = adapter.getService(service.getService_desc());
		if (serviceExistence == null) {
			adapter.addService(service);
			services.add(service);
		} else {
			adapter.updateService(service);
			int serviceSelectedInList = serviceTable.getSelectionModel().getSelectedIndex();
			// Replace the newest room info to the list
			services.set(serviceSelectedInList, service);
		}
		// save current service data to DB
		// travel in "services" for finding the service object which have
		// serviceID
		reloadTableView(serviceTable, services);
		// for clearing form fields
		TextField[] serviceFields = new TextField[] { serviceDesc, servicePrice };
		clearFormFields(serviceFields);

	}

	@FXML
	public void deleteService() {

		// get selected service from table view, delete in DB base on the
		// serviceID's value
		Service service = serviceTable.getSelectionModel().getSelectedItem();
		if (service == null) {
			showAlert(AlertType.INFORMATION, "Enter Values", null, "Please select one row to continue.");
			return;
		}

		adapter.deleteService(service);
		services.remove(service);
		// Clear form for entering new service
		TextField[] serviceFields = new TextField[] { serviceDesc, servicePrice };
		clearFormFields(serviceFields);
		reloadTableView(serviceTable, services);
	}

}