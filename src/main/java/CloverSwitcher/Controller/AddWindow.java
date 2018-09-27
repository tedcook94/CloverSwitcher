package CloverSwitcher.Controller;

import CloverSwitcher.Model.BootEntry;
import CloverSwitcher.Model.EntryList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class AddWindow {

    @FXML
    private TextField nameField;
    @FXML
    private TextField uuidField;

    @FXML
    private void saveEntry(ActionEvent event) {
        String entryName = nameField.getText();
        String uuid = uuidField.getText();

        if (entryName.length() == 0 || uuid.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Both Entry Name and UUID are required to save the entry.");
            alert.setTitle("Error");
            alert.setHeaderText("Error Saving Entry");
            alert.showAndWait();
        } else {
            EntryList.saveNewEntry(new BootEntry(entryName, uuid));
            closeWindow(event);
        }
    }

    @FXML
    private void cancelEntry(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel adding a new entry?", MainWindow.yesButton, MainWindow.noButton);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == MainWindow.yesButton) {
            closeWindow(event);
        }
    }

    private void closeWindow(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
        MainWindow.childWindowOpen = false;
    }
}
