package com.dwhi219.view;

import com.dwhi219.MainApp;
import com.dwhi219.model.Part;
import com.dwhi219.model.Product;
import com.dwhi219.util.Constants.Mode;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Main screen controller
 *
 * @author Dan
 */
public class MainScreenController {

    @FXML
    private TextField partSearchField;
    private ObservableList<Part> partSearchResults = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private TextField productSearchField;
    private ObservableList<Product> productSearchResults = FXCollections.observableArrayList();

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    private MainApp mainApp;

    public MainScreenController() {
    }

    @FXML
    private void initialize() {
        partIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPartID()).asObject());
        partNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        partInventoryLevelColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInStock()).asObject());
        partPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        productIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProductID()).asObject());
        productNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        productInventoryLevelColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInStock()).asObject());
        productPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        partTable.setItems(mainApp.getInventory().getAllParts());
        productTable.setItems(mainApp.getInventory().getProducts());
    }

    @FXML
    private void handleExit() {
        mainApp.exit();
    }

    @FXML
    private void handleAddPart() {
        Part tempPart = null;
        mainApp.showAddModifyPartScreen(Mode.ADD, tempPart);
    }

    @FXML
    private void handleModifyPart() {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            mainApp.showAddModifyPartScreen(Mode.MODIFY, selectedPart);
        }
    }

    @FXML
    private void handleDeletePart() {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            if (mainApp.throwConfirmation()) {
                mainApp.getInventory().deletePart(selectedPart);
            }
        }
    }

    @FXML
    private void handleAddProduct() {
        Product tempProduct = null;
        mainApp.showAddModifyProductScreen(Mode.ADD, tempProduct);
    }

    @FXML
    private void handleModifyProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            mainApp.showAddModifyProductScreen(Mode.MODIFY, selectedProduct);
        }
    }

    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if(mainApp.throwConfirmation()) {
            mainApp.getInventory().removeProduct(selectedProduct.getProductID());
        }      
    }

    @FXML
    private void handlePartSearch() {
        String searchTerm = partSearchField.getText().toLowerCase();
        boolean found = false;
        if (searchTerm.equals("")) {
            partTable.setItems(mainApp.getInventory().getAllParts());
        } else {
            for (Part p : mainApp.getInventory().getAllParts()) {
                if (p.getName().toLowerCase().contains(searchTerm) || searchTerm.equals(String.valueOf(p.getPartID()))) {
                    found = true;
                    if (!partSearchResults.contains(p)) {
                        partSearchResults.add(p);
                    }
                }
            }
            if (found) {
                partTable.setItems(partSearchResults);
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Part Not Found");
                alert.setHeaderText("Warning");
                alert.setContentText("A part matching " + searchTerm + " was not found");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleProductSearch() {
        String searchTerm = productSearchField.getText().toLowerCase();
        boolean found = false;
        if (searchTerm.equals("")) {
            productTable.setItems(mainApp.getInventory().getProducts());
        } else {
            for (Product p : mainApp.getInventory().getProducts()) {
                if (p.getName().toLowerCase().contains(searchTerm) || searchTerm.equals(String.valueOf(p.getProductID()))) {
                    found = true;
                    if (!productSearchResults.contains(p)) {
                        productSearchResults.add(p);
                    }
                }
            }
            if (found) {
                productTable.setItems(productSearchResults);
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Product Not Found");
                alert.setHeaderText("Warning");
                alert.setContentText("A product matching " + searchTerm + " was not found");
                alert.showAndWait();
            }
        }
    }

}
