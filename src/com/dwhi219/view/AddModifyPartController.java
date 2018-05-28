package com.dwhi219.view;

import com.dwhi219.MainApp;
import com.dwhi219.model.Inhouse;
import com.dwhi219.model.Outsourced;
import com.dwhi219.model.Part;
import com.dwhi219.util.Constants;
import com.dwhi219.util.Constants.Mode;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Add/Modify Part controller class
 *
 * @author Dan
 */
public class AddModifyPartController {

    @FXML
    private Label actionLabel;

    @FXML
    private RadioButton inhouse;
    @FXML
    private RadioButton outsourced;

    @FXML
    private TextField partIdField;
    @FXML
    private TextField partNameField;
    @FXML
    private TextField inventoryField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;
    @FXML
    private Label companyNameLabel;
    @FXML
    private TextField companyNameField;
    @FXML
    private Label machineIdLabel;
    @FXML
    private TextField machineIdField;

    private Part part;
    private MainApp mainApp;
    private Mode mode;

    public AddModifyPartController() {
    }

    @FXML
    private void initialize() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setPart(Mode mode, Part part) {
        this.part = part;
        this.mode = mode;

        switch (mode) {
            case ADD:
                actionLabel.setText("Add Part");
                companyNameLabel.setVisible(false);
                companyNameField.setVisible(false);
                machineIdLabel.setVisible(true);
                machineIdField.setVisible(true);
                break;
            case MODIFY:
                actionLabel.setText("Modify Part");
                if (part instanceof Inhouse) {
                    inhouse.setSelected(true);
                    companyNameLabel.setVisible(false);
                    companyNameField.setVisible(false);
                    machineIdLabel.setVisible(true);
                    machineIdField.setVisible(true);
                    machineIdField.setText(String.valueOf(((Inhouse) part).getMachineID()));
                } else if (part instanceof Outsourced) {
                    outsourced.setSelected(true);
                    machineIdLabel.setVisible(false);
                    machineIdField.setVisible(false);
                    companyNameLabel.setVisible(true);
                    companyNameField.setVisible(true);
                    companyNameField.setText(((Outsourced) part).getCompanyName());
                }
                partIdField.setText(String.valueOf(part.getPartID()));
                partNameField.setText(part.getName());
                inventoryField.setText(String.valueOf(part.getInStock()));
                priceField.setText(String.valueOf(part.getPrice()));
                maxField.setText(String.valueOf(part.getMax()));
                minField.setText(String.valueOf(part.getMin()));
                break;
        }

    }

    @FXML
    private void handleRadioButtons() {
        if (inhouse.isSelected()) {
            companyNameLabel.setVisible(false);
            companyNameField.setVisible(false);
            machineIdLabel.setVisible(true);
            machineIdField.setVisible(true);
        } else if (outsourced.isSelected()) {
            machineIdLabel.setVisible(false);
            machineIdField.setVisible(false);
            companyNameLabel.setVisible(true);
            companyNameField.setVisible(true);
        }
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            if (part == null) {
                if (inhouse.isSelected()) {
                    part = new Inhouse();
                } else {
                    part = new Outsourced();
                }
            }
            part.setName(partNameField.getText());
            part.setInStock(Integer.parseInt(inventoryField.getText()));
            part.setPrice(Double.parseDouble(priceField.getText()));
            part.setMax(Integer.parseInt(maxField.getText()));
            part.setMin(Integer.parseInt(minField.getText()));
            if (part instanceof Inhouse) {
                if (outsourced.isSelected()) {
                    part = new Outsourced((Inhouse) part);
                    ((Outsourced) part).setCompanyName(companyNameField.getText());
                } else {
                    ((Inhouse) part).setMachineID(Integer.parseInt(machineIdField.getText()));
                }
            }
            if (part instanceof Outsourced) {
                if (inhouse.isSelected()) {
                    part = new Inhouse((Outsourced) part);
                    ((Inhouse) part).setMachineID(Integer.parseInt(machineIdField.getText()));
                } else {
                    ((Outsourced) part).setCompanyName(companyNameField.getText());
                }
            }
            switch (mode) {
                case ADD:
                    mainApp.getInventory().addPart(part);
                    break;
                case MODIFY:
                    mainApp.getInventory().updatePart(part.getPartID(), part);
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
        if (partNameField.getText() == null || partNameField.getText().length() == 0) {
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
        if (inhouse.isSelected()) {
            if (machineIdField.getText() == null || machineIdField.getText().length() == 0) {
                errorMessage += Constants.MISSING_MACHINE_ID;
            } else {
                try {
                    Integer.parseInt(machineIdField.getText());
                } catch (NumberFormatException e) {
                    errorMessage += Constants.MACHINE_ID_NAN;
                }
            }
        }
        if (outsourced.isSelected()) {
            if (companyNameField.getText() == null || companyNameField.getText().length() == 0) {
                errorMessage += Constants.MISSING_COMPANY_NAME;
            }
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
