package controller;

import exception.ProcessExeption;
import helper.IntervalEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Loan;
import process.ProcessLoan;

import java.net.URL;
import java.util.ResourceBundle;

public class addLoanBoxController implements Initializable {
    @FXML
    public Button saveBtn;
    @FXML
    public TextField nameText; // name text field
    public TextArea descriptionTextArea; // info text area
    public ComboBox interestIntervalCombo;
    public ComboBox paymentIntervalCombo;
    public TextField timeSpanText;
    public TextField baseValueText; // value text field
    public TextField interestRateText;
    public ComboBox unitCombo;

    public boolean saved = false;
    public String id;
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

    private Loan loan;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init interval combo
        setIntervalCombo();
    }

    public void saveBtnClick(ActionEvent actionEvent) throws ProcessExeption {
        //TODO: save add loan to database and show list view

        try {
            id =ProcessLoan.addLoan(nameText.getText(),descriptionTextArea.getText(),Double.valueOf(interestRateText.getText().replaceAll(",","")),Integer.valueOf(timeSpanText.getText()),helper.IntervalEnum.INTERVAL.valueOf(interestIntervalCombo.getSelectionModel().getSelectedItem().toString()),helper.IntervalEnum.INTERVAL.valueOf(paymentIntervalCombo.getSelectionModel().getSelectedItem().toString()));
            saved = true;
        }
        catch (ProcessExeption pe)
        {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Missing something");
            alertWarning.initStyle(StageStyle.TRANSPARENT); // set alert border not shown
            alertWarning.setHeaderText("Some data is incorrect");
            alertWarning.setContentText("Please check carefully");
            alertWarning.showAndWait();
            System.out.println(pe.getErrorCodeMessage());
            return;        }
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // get stage of program, primary stage
        stage.close();
    }

    public void setIntervalCombo(){
        // type list
        ObservableList<String> typeInterestInterval = FXCollections.observableArrayList("DAILY", "WEEKLY","MONTHLY","QUARTERLY","YEARLY","ONE_TIME");
        ObservableList<String> typePaymentInterval = FXCollections.observableArrayList("DAILY", "WEEKLY","MONTHLY","QUARTERLY","YEARLY","ONE_TIME");

        // set data of combo interval
        interestIntervalCombo.setItems(typeInterestInterval);
        paymentIntervalCombo.setItems(typePaymentInterval);
    }

    public void resetBtnClick(ActionEvent actionEvent) {
    }
}
