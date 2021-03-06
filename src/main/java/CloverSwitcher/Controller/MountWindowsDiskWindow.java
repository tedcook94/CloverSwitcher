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

public class MountWindowsDiskWindow implements Initializable {

    @FXML
    private TextArea outputText;
    @FXML
    private Label inputLabel;
    @FXML
    private TextField inputField;

    private static int diskToMount;
    static int getDiskToMount() { return diskToMount; }


    @FXML
    private void openMountPartitionWindow(ActionEvent event) throws IOException {
        try {
            diskToMount = Integer.parseInt(inputField.getText().trim());

            if (diskToMount >= 0) {
                if (!MountManager.listPartitionsWindows(diskToMount).contains("is not valid")) {
                    Parent mountPartitionWindow = FXMLLoader.load(getClass().getClassLoader().getResource("mountWindowsPartitionWindow.fxml"));
                    Scene scene = new Scene(mountPartitionWindow);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setOnCloseRequest(e -> MainWindow.childWindowOpen = false);
                    stage.show();
                } else  {
                    showInvalidDiskAlert();
                }
            } else {
                showInvalidDiskAlert();
            }
        } catch (NumberFormatException e) {
            showInvalidDiskAlert();
        }
    }

    private static void showInvalidDiskAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Error Selecting Disk");
        alert.setContentText("Please enter a valid disk number.");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        outputText.setText(MountManager.listDisksWindows());
        inputLabel.setText("Enter the disk number of your Clover disk: ");
    }
}
