package com.dwhi219;

import com.dwhi219.model.Inhouse;
import com.dwhi219.model.Inventory;
import com.dwhi219.model.Outsourced;
import com.dwhi219.model.Part;
import com.dwhi219.model.Product;
import com.dwhi219.util.Constants;
import com.dwhi219.util.Constants.Mode;
import com.dwhi219.view.AddModifyPartController;
import com.dwhi219.view.AddModifyProductController;
import com.dwhi219.view.MainScreenController;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main application
 *
 * @author Dan
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private Inventory inventory = new Inventory();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Inventory Management System");

        showMainScreen();
    }

    public MainApp() {
        Part inhouse = new Inhouse("Inhouse name", 2.00, 4, 0, 8, 5260);
        Part outsourced = new Outsourced("Outsourced name", 2.00, 4, 0, 8, "Testco");
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(inhouse);
        Product product = new Product(parts, "Product name", 30.00, 1, 0, 20);
        inventory.addPart(inhouse);
        inventory.addPart(outsourced);
        inventory.addProduct(product);
    }

    public void showMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainScreen.fxml"));
            AnchorPane mainScreen = (AnchorPane) loader.load();

            Scene scene = new Scene(mainScreen);
            primaryStage.setScene(scene);
            primaryStage.show();

            MainScreenController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddModifyPartScreen(Mode mode, Part part) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AddModifyPart.fxml"));
            Parent addModifyScreenParent = loader.load();
            Scene addModifyScreenScene = new Scene(addModifyScreenParent);

            AddModifyPartController controller = loader.getController();
            controller.setMainApp(this);
            controller.setPart(mode, part);

            primaryStage.setScene(addModifyScreenScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddModifyProductScreen(Mode mode, Product product) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AddModifyProduct.fxml"));
            Parent addModifyScreenParent = loader.load();
            Scene addModifyScreenScene = new Scene(addModifyScreenParent);

            AddModifyProductController controller = loader.getController();
            controller.setMainApp(this);
            controller.setProduct(mode, product);

            primaryStage.setScene(addModifyScreenScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean throwConfirmation() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(Constants.CONFIRMATION);

        ButtonType yes = new ButtonType("Yes", ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == yes;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void exit() {
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
