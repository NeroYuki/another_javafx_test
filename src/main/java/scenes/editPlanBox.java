package scenes;

import controller.editPlanController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class editPlanBox {
//    private double xOffset = 0;
//    private double yOffset = 0;
private Scene scene;
    private editPlanController controller;
    public editPlanBox() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/fxml/frmEditPlan.fxml"));

        Parent root = loader.load();
        scene = new Scene(root);
        controller = loader.getController();
    }

    public Scene getScene() {
        return scene;
    }

    public editPlanController getController() {
        return controller;
    }
}
