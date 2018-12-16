package CloverSwitcher.Controller;

import CloverSwitcher.Model.MountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.lang3.SystemUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class MountWindowsPartitionWindow implements Initializable {

    @FXML
    private TextArea outputText;
    @FXML
    private Label inputLabel;
    @FXML
    private TextField inputField;

    private static int partitionToMount;
    static int getPartitionToMount() { return partitionToMount; }

    @FXML
    private void setDefaultBootEntry(ActionEvent event) {
        try {
            partitionToMount = Integer.parseInt(inputField.getText().trim());

            if (partitionToMount > 0) {
                String result = MountManager.mountPartitionWindows(MountWindowsDiskWindow.getDiskToMount(), partitionToMount);
                if (!result.contains("successfully assigned")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Mounting Partition");
                    alert.setContentText("The partition failed to mount as drive Z. This likely occurred because there is already another volume mounted there or the app is not running as administrator.");
                    alert.showAndWait();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                    MainWindow.childWindowOpen = false;
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText("Default Volume Set");
                    alert.setContentText("The default volume was successfully set to " + MainWindow.getEntryToSetAsDefault().getEntryName() + " (" + MainWindow.getEntryToSetAsDefault().getUuid() + ").");
                    alert.showAndWait();

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                    MainWindow.childWindowOpen = false;
                }
            } else {
                showInvalidPartitionAlert();
            }
        } catch (NumberFormatException e) {
            showInvalidPartitionAlert();
        }

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
        outputText.setText(MountManager.listPartitionsWindows(MountWindowsDiskWindow.getDiskToMount()));
        inputLabel.setText("Enter the number of your EFI partition: ");
    }
}
