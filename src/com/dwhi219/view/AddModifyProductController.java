package com.dwhi219.view;

import com.dwhi219.MainApp;
import com.dwhi219.model.Part;
import com.dwhi219.model.Product;
import com.dwhi219.util.Constants;
import com.dwhi219.util.Constants.Mode;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Add/Modify Product controller class
 *
 * @author Dan
 */
public class AddModifyProductController {

    @FXML
    private Label actionLabel;

    @FXML
    private TextField productIdField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField inventoryField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;

    @FXML
    private TextField addPartSearchField;
    private ObservableList<Part> addPartSearchResults = FXCollections.observableArrayList();
    @FXML
    private TableView<Part> availablePartsTable;
    @FXML
    private TableColumn<Part, Integer> availablePartIdColumn;
    @FXML
    private TableColumn<Part, String> availablePartNameColumn;
    @FXML
    private TableColumn<Part, Integer> availablePartInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> availablePartPriceColumn;

    @FXML
    private TableView<Part> productAssociatedParts;
    @FXML
    private TableColumn<Part, Integer> productAssociatedPartsPartIdColumn;
    @FXML
    private TableColumn<Part, String> productAssociatedPartsPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> productAssociatedPartsInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> productAssociatedPartsPartPriceColumn;

    private ObservableList<Part> tempPartList = FXCollections.observableArrayList();
    private ObservableList<Part> partsToRemove = FXCollections.observableArrayList();
    private Product product;
    private MainApp mainApp;
    private Mode mode;

    public AddModifyProductController() {
    }

    @FXML
    private void initialize() {
        availablePartIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPartID()).asObject());
        availablePartNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        availablePartInventoryLevelColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInStock()).asObject());
        availablePartPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        productAssociatedPartsPartIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPartID()).asObject());
        productAssociatedPartsPartNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        productAssociatedPartsInventoryLevelColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInStock()).asObject());
        productAssociatedPartsPartPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        availablePartsTable.setItems(mainApp.getInventory().getAllParts());
    }

    public void setProduct(Mode mode, Product product) {
        this.product = product;
        this.mode = mode;

        switch (mode) {
            case ADD:
                actionLabel.setText("Add Product");
                break;
            case MODIFY:
                actionLabel.setText("Modify Product");
                productNameField.setText(product.getName());
                inventoryField.setText(String.valueOf(product.getInStock()));
                priceField.setText(String.valueOf(product.getPrice()));
                maxField.setText(String.valueOf(product.getMax()));
                minField.setText(String.valueOf(product.getMin()));
                tempPartList.addAll(product.getAssociatedParts());
                break;
        }
        productAssociatedParts.setItems(tempPartList);
    }

    @FXML
    private void handlePartSearch() {
        String searchTerm = addPartSearchField.getText().toLowerCase();
        boolean found = false;
        if (searchTerm.equals("")) {
            availablePartsTable.setItems(mainApp.getInventory().getAllParts());
        } else {
            for (Part p : mainApp.getInventory().getAllParts()) {
                if (p.getName().toLowerCase().contains(searchTerm) || searchTerm.equals(String.valueOf(p.getPartID()))) {
                    found = true;
                    if (!addPartSearchResults.contains(p)) {
                        addPartSearchResults.add(p);
                    }
                }
            }
            if (found) {
                availablePartsTable.setItems(addPartSearchResults);
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
    private void handleAddPart() {
        Part selectedPart = availablePartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            tempPartList.add(selectedPart);
        }
    }

    @FXML
    private void handleDeletePart() {
        Part selectedPart = productAssociatedParts.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            if (mainApp.throwConfirmation()) {
                partsToRemove.add(selectedPart);
                tempPartList.remove(selectedPart);
            }
        }
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            if (product == null) {
                product = new Product();
                product.setAssociatedParts(tempPartList);
            } else {
                partsToRemove.forEach((toRemove) -> {
                    product.removeAssociatedPart(toRemove.getPartID());
                });
                tempPartList.forEach((toAdd) -> {
                    product.addAssociatedPart(toAdd);
                });
            }
            product.setName(productNameField.getText());
            product.setInStock(Integer.parseInt(inventoryField.getText()));
            product.setPrice(Double.parseDouble(priceField.getText()));
            product.setMax(Integer.parseInt(maxField.getText()));
            product.setMin(Integer.parseInt(minField.getText()));

            switch (mode) {
                case ADD:
                    mainApp.getInventory().addProduct(product);
                    break;
                case MODIFY:
                    mainApp.getInventory().updateProduct(product.getProductID(), product);
                    break;
            }
            mainApp.showMainScreen();
        }
    }

    @FXML
    private void handleCancel() {
        if(mainApp.throwConfirmation()) {
            mainApp.showMainScreen();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";
        int inventory = 0;
        int min = 0;
        int max = 0;
        double price = 0;
        double partCost = 0;
        for(Part p : tempPartList) {
            partCost += p.getPrice();
        }

        if (productNameField.getText() == null || productNameField.getText().length() == 0) {
            errorMessage += Constants.MISSING_NAME;
        }
        if (inventoryField.getText() == null || inventoryField.getText().length() == 0) {
            errorMessage += Constants.MISSING_INVENTORY;
        } else {
            try {
                inventory = Integer.parseInt(inventoryField.getText());
            } catch (NumberFormatException e) {
                errorMessage += Constants.INVENTORY_NAN;
            }
        }
        if (inventory < 0) {
            errorMessage += Constants.NEGATIVE_INVENTORY;
        }
        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += Constants.MISSING_PRICE;
        } else {
            try {
                price = Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += Constants.PRICE_NAN;
            }
        }
        if (price < 0) {
            errorMessage += Constants.NEGATIVE_PRICE;
        }
        if (price < partCost) {
            errorMessage += Constants.PRICE_TOO_LOW;
        }
        if (maxField.getText() == null || maxField.getText().length() == 0) {
            errorMessage += Constants.MISSING_MAX;
        } else {
            try {
                max = Integer.parseInt(maxField.getText());
            } catch (NumberFormatException e) {
                errorMessage += Constants.MAX_NAN;
            }
        }
        if (max < 0) {
            errorMessage += Constants.NEGATIVE_MAX;
        }
        if (minField.getText() == null || minField.getText().length() == 0) {
            errorMessage += Constants.MISSING_MIN;
        } else {
            try {
                min = Integer.parseInt(minField.getText());
            } catch (NumberFormatException e) {
                errorMessage += Constants.MIN_NAN;
            }
        }
        if (min < 0) {
            errorMessage += Constants.NEGATIVE_MIN;
        }
        if (min > max) {
            errorMessage += Constants.MIN_MORE_MAX;
        }
        if (max < min) {
            errorMessage += Constants.MAX_LESS_MIN;
        }
        if (inventory < min || inventory > max) {
            errorMessage += Constants.INV_OUT_OF_BOUNDS;
        }
        if (productAssociatedParts.getItems().size() == 0) {
            errorMessage += Constants.NO_ASSOC_PARTS;
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
}
