package CloverSwitcher.Controller;

import CloverSwitcher.Model.BootEntry;
import CloverSwitcher.Model.EntryList;
import CloverSwitcher.Model.JsonManager;
import CloverSwitcher.Model.MountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    static int getEntryToModify() { return entryToModify; }

    private static BootEntry entryToMount;
    static BootEntry getEntryToMount() { return entryToMount; }

    static boolean childWindowOpen = false;
    private Text logText = new Text();

    static ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    static ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);


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
        if (!childWindowOpen) {
            Parent addWindow = FXMLLoader.load(getClass().getClassLoader().getResource("addWindow.fxml"));
            Scene scene = new Scene(addWindow);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(e -> childWindowOpen = false);
            stage.show();
            childWindowOpen = true;
        }
    }

    @FXML
    private void openEditEntryWindow(ActionEvent event) throws IOException {
        BootEntry entry = entryTable.getSelectionModel().getSelectedItem();
        if (!childWindowOpen) {
            if (entry != null) {
                entryToModify = EntryList.getEntryList().indexOf(entry);

                Parent editWindow = FXMLLoader.load(getClass().getClassLoader().getResource("editWindow.fxml"));
                Scene scene = new Scene(editWindow);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setOnCloseRequest(e -> childWindowOpen = false);
                stage.show();
                childWindowOpen = true;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit Error");
                alert.setHeaderText("No Entry Selected");
                alert.setContentText("Select an entry from the table to edit it.");
                alert.showAndWait();
            }
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
    private void openMountDiskWindow(ActionEvent event) throws IOException {
        BootEntry entry = entryTable.getSelectionModel().getSelectedItem();
        if (!childWindowOpen) {
            if (entry != null) {
                entryToMount = entry;

                Parent mountDiskWindow = FXMLLoader.load(getClass().getClassLoader().getResource("mountDiskWindow.fxml"));
                Scene scene = new Scene(mountDiskWindow);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setOnCloseRequest(e -> childWindowOpen = false);
                stage.show();
                childWindowOpen = true;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("No Entry Selected");
                alert.setContentText("Select an entry from the table to set it as the default boot entry.");
                alert.showAndWait();
            }
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
        JsonManager.readFromFile();
        entryNameColumn.setCellValueFactory(cellData -> cellData.getValue().entryNameProperty());
        uuidColumn.setCellValueFactory(cellData -> cellData.getValue().uuidProperty());
        updateEntryTable();
    }
}
