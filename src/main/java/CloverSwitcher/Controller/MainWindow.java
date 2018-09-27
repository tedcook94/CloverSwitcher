package CloverSwitcher.Controller;

import CloverSwitcher.Model.BootEntry;
import CloverSwitcher.Model.EntryList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

    @FXML
    private TableView<BootEntry> entryTable;
    @FXML
    private TableColumn<BootEntry, String> entryNameColumn;
    @FXML
    private TableColumn<BootEntry, String> uuidColumn;
    @FXML
    private TextFlow outputArea;

    private static int entryToModify;
    public static int getEntryToModify() { return entryToModify; }

    private Text logText = new Text();


    private void logToConsole(String newLine) {
        String existingText = logText.getText();
        String newText = "";
        newText = existingText.length() == 0 ? newLine : existingText + "\n" + newLine;

        logText.setStyle("-fx-fill: #33ff00; -fx-font-family: Inconsolata;");
        logText.setText(newText);
        outputArea.getChildren().remove(logText);
        outputArea.getChildren().add(logText);
    }

    @FXML
    private void openAddEntryWindow(ActionEvent event) throws IOException {
        Parent addWindow = FXMLLoader.load(getClass().getClassLoader().getResource("addWindow.fxml"));
        Scene scene = new Scene(addWindow);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void openEditEntryWindow(ActionEvent event) throws IOException {
        BootEntry entry = entryTable.getSelectionModel().getSelectedItem();
        if (entry != null) {
            entryToModify = EntryList.getEntryList().indexOf(entry);

            Parent editWindow = FXMLLoader.load(getClass().getClassLoader().getResource("editWindow.fxml"));
            Scene scene = new Scene(editWindow);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edit Error");
            alert.setHeaderText("No Entry Selected");
            alert.setContentText("Select an entry from the table to edit it.");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteEntry(ActionEvent event) {
        BootEntry entry = entryTable.getSelectionModel().getSelectedItem();
        if (entry != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Entry Deletion");
            alert.setHeaderText("Confirm Delete?");
            alert.setContentText("Are you sure you want to delete the entry \"" + entry.getEntryName() + "\"?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                EntryList.deleteEntry(entry);
                updateEntryTable();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion Error");
            alert.setHeaderText("No Entry Selected");
            alert.setContentText("Select an entry from the table to delete it.");
            alert.showAndWait();
        }
    }

    @FXML
    private void clearConsole() {
        logText.setText("");
        outputArea.getChildren().remove(logText);
    }

    private void updateEntryTable() { entryTable.setItems(EntryList.getEntryList()); }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        entryNameColumn.setCellValueFactory(cellData -> cellData.getValue().entryNameProperty());
        uuidColumn.setCellValueFactory(cellData -> cellData.getValue().uuidProperty());
        updateEntryTable();
    }
}
