package controller;

import exception.ProcessExeption;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Transaction;
import process.ProcessCategories;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class addCategoriesBoxController implements Initializable {
    @FXML
    public Button iconSelectBtn; // click to select icon button
    public Button saveBtn;
    @FXML
    public TextField nameText; // name text field
    public ImageView iconImage; // icon display image
    public TextArea infoTextArea; // info text area
    public ComboBox typeCombo; // pick type combo box

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

    private Transaction transaction; // not used but maybe later



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("src/main/resources/img/self-icon.png");
        Image image = new Image(file.toURI().toString(),50,50,false,true);
        iconImage.setImage(image);
        // setup combo box
        typeComboSet();
    }

    public void iconSelectBtnClick(ActionEvent e) {
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        FileChooser fc = new FileChooser();

        // set initial folder when open dialog to select item
        String imgDirectoryLink = "src/main/resources/img/icon/add-more";
        File imgDirectory = new File(imgDirectoryLink);
        if(!imgDirectory.canRead()) {
            imgDirectory = new File("c:/");
        }
        fc.setInitialDirectory(imgDirectory);

        fc.setTitle("Pick an image");
        // just accept image file
        FileChooser.ExtensionFilter imageExtension = new FileChooser.ExtensionFilter("Image files", "*.jpg","*.png");
        fc.getExtensionFilters().add(imageExtension);
        File file = fc.showOpenDialog(stage);
        // pick image
        if(file!=null) {
            Image image = new Image(file.toURI().toString(),50,50,false,true);
            iconImage.setImage(image);
        }
    }

    public void saveBtnClick(ActionEvent actionEvent) throws ProcessExeption {
        //TODO: save add categories to database and show list view
        try {
            ProcessCategories.saveCategories(nameText.getText(),iconImage.getImage().getUrl(),infoTextArea.getText(),typeCombo.getSelectionModel().getSelectedItem().toString());
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
            return;
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // get stage of program, primary stage
        stage.close();
    }

    public void typeComboSet() {
        // type list
        ObservableList<String> typeCategories = FXCollections.observableArrayList("Income", "Expense");

        // set data of combo type
        typeCombo.setItems(typeCategories);
    }
}
