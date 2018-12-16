package CloverSwitcher.Controller;

import CloverSwitcher.Model.MountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MountUnixPartitionWindow implements Initializable {

    @FXML
    private TextArea outputText;
    @FXML
    private Label diskLabel;
    @FXML
    private Label partitionLabel;
    @FXML
    private TextField diskField;
    @FXML
    private TextField partitionField;

    private static int diskToMount;
    static int getDiskToMount() { return diskToMount; }
    private static int partitionToMount;
    static int getPartitionToMount() { return partitionToMount; }

    @FXML
    private void setDefaultBootEntry(ActionEvent event) {
        if (SystemUtils.IS_OS_MAC) {
            try {
                diskToMount = Integer.parseInt(diskField.getText().trim());
            } catch (NumberFormatException e) {
                showInvalidDiskAlert();
                return;
            }

            try {
                partitionToMount = Integer.parseInt(partitionField.getText().trim());
            } catch (NumberFormatException e) {
                showInvalidPartitionAlert();
                return;
            }

            if (diskToMount >= 0) {
                if (partitionToMount >= 0) {
                    MountManager.mountPartitionMac(diskToMount, partitionToMount);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText("Default Volume Set");
                    alert.setContentText("The default volume was successfully set to " + MainWindow.getEntryToSetAsDefault().getEntryName() + " (" + MainWindow.getEntryToSetAsDefault().getUuid() + ").");
                    alert.showAndWait();

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                    MainWindow.childWindowOpen = false;
                } else {
                    showInvalidPartitionAlert();
                }
            } else {
                showInvalidDiskAlert();
            }
        }
    }

    private static void showInvalidDiskAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Error Selecting Disk");
        alert.setContentText("Please enter a valid disk number.");
        alert.showAndWait();
    }

    private static void showInvalidPartitionAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Error Selecting Partition");
        alert.setContentText("Please enter a valid partition number.");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (SystemUtils.IS_OS_MAC) {
            outputText.setText(MountManager.listPartitionsMac());
            diskLabel.setText("Clover disk number: ");
            partitionLabel.setText("Partition:");
        }
    }
}
