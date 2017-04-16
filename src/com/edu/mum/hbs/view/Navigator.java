package com.edu.mum.hbs.view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;

/**
 * Utility class for controlling navigation between vistas.
 *
 * All methods on the navigator are static to facilitate
 * simple access from anywhere in the application.
 */
public class Navigator {

    /**
     * Convenience constants for fxml layouts managed by the navigator.
     */
    public static final String MAIN    = "main.fxml";
    public static final String CHECK_OUT_FORM = "CheckOutForm.fxml";
    public static final String ROOM_MANAGEMENT = "RoomForm.fxml";
    public static final String CUSTOMER_ROOM_FORM = "CustomerRoomForm.fxml";
    public static final String SERVICE_FORM = "ServiceForm.fxml";
    public static final String LOGIN = "login.fxml";
    public static final String ROOM_SERVICE_FORM = "RoomServiceForm.fxml";
    public static final String INVOICE_FORM = "InvoiceForm.fxml";
    public static final String CHECK_IN_FORM = "CheckInForm.fxml";

    public static final String RPT_CUST_ROOM_INFO = "ReportCustomerRoomForm.fxml";
    public static final String RPT_ROOM_INFO = "ReportRoomForm.fxml";
    public static final String RPT_PROFIT_INFO = "ReportProfitForm.fxml";
    
    
    /** The main application layout controller. */
    private static MainController mainController;

    /**
     * Stores the main controller for later use in navigation tasks.
     *
     * @param mainController the main application layout controller.
     */
    public static void setMainController(MainController mainController) {
        Navigator.mainController = mainController;
    }

    /**
     * Loads the vista specified by the fxml file into the
     * vistaHolder pane of the main application layout.
     *
     * Previously loaded vista for the same fxml file are not cached.
     * The fxml is loaded anew and a new vista node hierarchy generated
     * every time this method is invoked.
     *
     * A more sophisticated load function could potentially add some
     * enhancements or optimizations, for example:
     *   cache FXMLLoaders
     *   cache loaded vista nodes, so they can be recalled or reused
     *   allow a user to specify vista node reuse or new creation
     *   allow back and forward history like a browser
     *
     * @param fxml the fxml file to be loaded.
     */
    public static void loadScene(String fxml) {
        System.out.println(fxml);
        try {
            mainController.setLibraryInternalScene(
                FXMLLoader.load(
                    Navigator.class.getResource(
                        fxml
                    )
                )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}