package com.edu.mum.hbs.view;

import com.edu.mum.hbs.dao.UserSession;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Main controller class for the entire layout.
 */
public class MainController {

    /** Holder of a switchable vista. */
    @FXML
    private StackPane libraryHolder;

    /**
     * Replaces the vista displayed in the vista holder with a new vista.
     *
     * @param node
     *            the vista node to be swapped in.
     */
    public void setLibraryInternalScene(Node node) {
        libraryHolder.getChildren().setAll(node);
    }

    /**
     * Event handler fired when the user requests a new vista.
     *
     * @param event
     *            the event that triggered the handler.
     */
    @FXML
    public void goToCheckOutForm() {
        Navigator.loadScene(Navigator.CHECK_OUT_FORM);
    }
    
    @FXML
    public void goToCustomerRoomForm() {
        if (UserSession.GetSession.getSession()!= null) {
            Navigator.loadScene(Navigator.CUSTOMER_ROOM_FORM);
       }
    }

    @FXML
    public void goToRoomManagement() {
    	if (UserSession.GetSession.getSession()!= null) {
            Navigator.loadScene(Navigator.ROOM_MANAGEMENT);
       }

    }

    @FXML
    public void goToServiceForm() {
        if (UserSession.GetSession.getSession()!= null) {
            Navigator.loadScene(Navigator.SERVICE_FORM);
        }
    }
    
    public void goToRoomServiceForm(){
    	if (UserSession.GetSession.getSession()!= null) {
            Navigator.loadScene(Navigator.ROOM_SERVICE_FORM);
        }
    }

    public void goToInvoiceForm(){
    	if (UserSession.GetSession.getSession()!= null) {
            Navigator.loadScene(Navigator.INVOICE_FORM);
        }
    }

    public void goToCheckInForm(){
    	if (UserSession.GetSession.getSession()!= null) {
            Navigator.loadScene(Navigator.CHECK_IN_FORM);
        }
    }

    public void goToReportCustomerRoomForm(){
    	if (UserSession.GetSession.getSession() != null) {
    		Navigator.loadScene(Navigator.RPT_CUST_ROOM_INFO);
    	}
    }
    
    public void goToReportRoomForm(){
    	if (UserSession.GetSession.getSession() != null) {
    		Navigator.loadScene(Navigator.RPT_ROOM_INFO);
    	}
    }
    
    public void goToReportProfitForm(){
    	if (UserSession.GetSession.getSession() != null) {
    		Navigator.loadScene(Navigator.RPT_PROFIT_INFO);
    	}
    }
    
}