package InventoryProgram.ViewControllers;

import InventoryProgram.Models.InHouse;
import InventoryProgram.Models.Inventory;
import InventoryProgram.Models.Outsourced;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static InventoryProgram.ViewControllers.Controller.selectedPart;

public class ModifyPartController implements Initializable {


    @FXML public TextField modPartId, modPartName, modPartPrice, modPartStock, modPartMinStock, modPartMaxStock, modPartVar;
    @FXML public Label modPartVarLabel;
    @FXML public RadioButton modPartInHouse, modPartOutsourced;

    @FXML public Button modifyPartButton, exitButton;

    public void modifyPart(Event event)
    {

        try {
            if (modPartInHouse.isSelected()) {
                InHouse newPart = new InHouse(Integer.parseInt(modPartId.getText()),
                        modPartName.getText(),
                        Double.parseDouble(modPartPrice.getText()),
                        Integer.parseInt((modPartStock.getText())),
                        Integer.parseInt(modPartMinStock.getText()),
                        Integer.parseInt(modPartMaxStock.getText()),
                        Integer.parseInt(modPartVar.getText()));

                if (newPart.getStock() < newPart.getMin() || newPart.getStock() > newPart.getMax())
                    throw new Exception("Stock must be between minimum and maximum values!");

                Inventory.updatePart(selectedPart.getID() - 1, newPart);
            } else if (modPartOutsourced.isSelected()) {
                Outsourced newPart = new Outsourced(Integer.parseInt(modPartId.getText()),
                        modPartName.getText(),
                        Double.parseDouble(modPartPrice.getText()),
                        Integer.parseInt((modPartStock.getText())),
                        Integer.parseInt(modPartMinStock.getText()),
                        Integer.parseInt(modPartMaxStock.getText()),
                        modPartVar.getText());

                if (newPart.getStock() < newPart.getMin() || newPart.getStock() > newPart.getMax())
                    throw new Exception("Stock must be between minimum and maximum values!");

                Inventory.updatePart(selectedPart.getID() - 1, newPart);
            }

            Stage stage = (Stage) modifyPartButton.getScene().getWindow();
            stage.close();

        }
        catch(Exception e)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.toString());
            a.showAndWait();
        }

    }

    public void changeVarLabel(Event event)
    {
        if (modPartInHouse.isSelected())
        {
            modPartVarLabel.setText("Machine ID");
        }
        else
        {
            modPartVarLabel.setText("Company Name");
        }
        modPartVar.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        modPartId.setText(Integer.toString(selectedPart.getID()));
        modPartName.setText(selectedPart.getName());
        modPartStock.setText(Integer.toString(selectedPart.getStock()));
        modPartPrice.setText(Double.toString(selectedPart.getPrice()));
        modPartMinStock.setText(Integer.toString(selectedPart.getMin()));
        modPartMaxStock.setText(Integer.toString(selectedPart.getMax()));

        if (selectedPart instanceof InHouse)
        {
            modPartVarLabel.setText("Machine ID");
            modPartVar.setText(Integer.toString(((InHouse) selectedPart).getMachineID()));
            modPartInHouse.setSelected(true);
        }
        else if (selectedPart instanceof Outsourced)
        {
            modPartVarLabel.setText("Company Name");
            modPartVar.setText(((Outsourced) selectedPart).getCompanyName());
            modPartOutsourced.setSelected(true);
        }
    }


    public void exitButtonAction(Event event)
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
