package CloverSwitcher.Controller;

import CloverSwitcher.Model.BootEntry;
import CloverSwitcher.Model.EntryList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditWindow implements Initializable {

    private int entryIndex = MainWindow.getEntryToModify();

    @FXML
    private TextField nameField;
    @FXML
    private TextField uuidField;

    @FXML
    private void saveEdit(ActionEvent event) throws IOException {
        String entryName = nameField.getText();
        String uuid = uuidField.getText();

        if (entryName.length() == 0 || uuid.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Saving Edit");
            alert.setContentText("Both Entry Name and UUID are required to save the entry.");
            alert.showAndWait();
        } else {
            EntryList.updateEntry(entryIndex, new BootEntry(entryName, uuid));
            returnToMainWindow(event);
        }
    }

    @FXML
    private void cancelEdit(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel editing the entry?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            returnToMainWindow(event);
        }
    }

    private void returnToMainWindow(ActionEvent event) throws IOException {
        Parent mainWindow = FXMLLoader.load(getClass().getClassLoader().getResource("mainWindow.fxml"));
        Scene scene = new Scene(mainWindow);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BootEntry entry = EntryList.getEntryList().get(entryIndex);
        nameField.setText(entry.getEntryName());
        uuidField.setText(entry.getUuid());
    }
}
