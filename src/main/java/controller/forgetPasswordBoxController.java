package controller;

import exception.ProcessExeption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;
import process.ProcessUser;

import java.net.URL;
import java.util.ResourceBundle;

public class forgetPasswordBoxController implements Initializable {
    @FXML
    public Button saveBtn;
    @FXML
    public TextField nameText;
    public TextField passwordText;
    public DatePicker dobpicker;
    public TextField emailText;

    // test dialog stage, not used but maybe later
    public Stage dialogEditStage;

    public void setDialogStage(Stage dialogStage) { // not use this set method but maybe used later
        this.dialogEditStage = dialogStage;
    }

    public void closeBtnClick(ActionEvent e){
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow(); // get stage of program, primary stage
        stage.close();
        //dialogEditStage.close();
    }

    // position to move screen around easily
    private double xOffset = 0;
    private double yOffset = 0;
    // move scene around
    public void drag(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    public void press(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private User user; // not used but maybe later



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void getPasswordBtnClick(ActionEvent actionEvent) throws ProcessExeption {
        passwordText.setText("");
        try {
            passwordText.setText(ProcessUser.getPassword(nameText.getText(), emailText.getText(), dobpicker.getValue()));
        }
        catch (ProcessExeption pe){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Not correct input");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("No data found");
            alertWarning.setContentText("Please fill in the suitable info");
            alertWarning.showAndWait();
        }
//        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // get stage of program, primary stage
//        stage.close();
    }
}
