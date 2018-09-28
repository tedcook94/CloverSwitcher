package CloverSwitcher.Controller;

import CloverSwitcher.Model.MountManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.commons.lang3.SystemUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class MountPartitionWindow implements Initializable {

    @FXML
    private TextArea outputText;
    @FXML
    private Label inputLabel;
    @FXML
    private TextField inputField;

    private static int partitionToMount;
    static int getPartitionToMount() { return partitionToMount; }


    @FXML
    private void setDefaultBootEntry() {
        if (SystemUtils.IS_OS_WINDOWS) {
            try {
                partitionToMount = Integer.parseInt(inputField.getText().trim());

                if (partitionToMount > 0) {
                    String result = MountManager.mountPartitionWindows(MountDiskWindow.getDiskToMount(), partitionToMount);
                    if (!result.contains("successfully assigned")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error Mounting Partition");
                        alert.setContentText("The partition failed to mount as drive Z. This likely occurred because there is alreay another volume mounted there.");
                        alert.showAndWait();
                    }
                } else {
                    showInvalidPartitionAlert();
                }
            } catch (NumberFormatException e) {
                showInvalidPartitionAlert();
            }
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
        if (SystemUtils.IS_OS_WINDOWS) {
            outputText.setText(MountManager.listPartitionsWindows(MountDiskWindow.getDiskToMount()));
            inputLabel.setText("Enter the number of your EFI partition: ");
        }
    }
}
