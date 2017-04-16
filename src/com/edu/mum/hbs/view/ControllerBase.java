package com.edu.mum.hbs.view;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ControllerBase implements Initializable {
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void clearFormFields(Object... controlLists) {
        for(Object aControl : controlLists) {
        	if (aControl instanceof TextField) {
        		((TextField)aControl).setText("");
        	} else if (aControl instanceof ChoiceBox) {
        		((ChoiceBox)aControl).setValue("");
        	} else if (aControl instanceof DatePicker) {
        		((DatePicker)aControl).setValue(null);
        	} else if (aControl instanceof TableView){
				((TableView) aControl).setItems(null);
			}
        }
    }
    
    public void showMessage(String message, Text alertId) {
        alertId.setText(message);
        createFader(alertId);
    }
    
    private FadeTransition createFader(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), node);
        fade.setFromValue(1);
        fade.setToValue(0);

        return fade;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	public <T> void reloadTableView(TableView<T> tblView, List<T> dataForShowing) {
		//TODO: reload table view ==> need to double this
        ObservableList<T> data = FXCollections.observableArrayList(dataForShowing);
        tblView.setItems(data);		
	}

	protected <T,S> void populateData2ChoiceBox(ChoiceBox<T> box, List<S> data){
		ObservableList<S> boxData = FXCollections.observableArrayList(data);
		box.setItems((ObservableList<T>) boxData);
	}

	public Optional<ButtonType> showAlert(Alert.AlertType type, String title, String headerText, String contentText){
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		return alert.showAndWait();
	}

}
