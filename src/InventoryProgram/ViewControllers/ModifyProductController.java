package InventoryProgram.ViewControllers;

import InventoryProgram.Models.Inventory;
import InventoryProgram.Models.Part;
import InventoryProgram.Models.Product;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static InventoryProgram.ViewControllers.Controller.selectedProduct;

public class ModifyProductController implements Initializable {

    public static Part selectedProductPart;
    private Product tempProduct = (Product) selectedProduct.clone();

    @FXML public Button addParts, deleteParts, saveChanges, exitButton;

    @FXML public TextField modProdPartID, modProdPartName, modProdPartStock, modProdPartCost, modProdPartMax, modProdPartMin;

    @FXML public javafx.scene.control.TextField modProdPartSearchBar;

    @FXML private TableView allPartsTable;
    @FXML private TableColumn<Part, Integer> allPartsColID;
    @FXML private TableColumn<Part, String> allPartsColName;
    @FXML private TableColumn<Part, String> allPartsColStock;
    @FXML private TableColumn<Part, String> allPartsColCost;

    @FXML private TableView productPartsTable;
    @FXML private TableColumn<Part, Integer> prodPartsColID;
    @FXML private TableColumn<Part, String> prodPartsColName;
    @FXML private TableColumn<Part, String> prodPartsColStock;
    @FXML private TableColumn<Part, String> prodPartsColCost;

    public ModifyProductController() throws CloneNotSupportedException {
    }

    public void populatePartTable()
    {

        ObservableList<Part> allPartsListValues = Inventory.getAllParts();
        ObservableList<Part> productPartsListValues = tempProduct.getAllAssociatedParts();

        allPartsColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        allPartsColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsColStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsColCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        allPartsTable.setItems(allPartsListValues);
        allPartsTable.getColumns().setAll(allPartsColID, allPartsColName, allPartsColStock, allPartsColCost);


        prodPartsColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        prodPartsColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodPartsColStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPartsColCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        productPartsTable.setItems(productPartsListValues);
        productPartsTable.getColumns().setAll(prodPartsColID, prodPartsColName, prodPartsColStock, prodPartsColCost);

    }


    public void exitButtonAction(Event event)
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }


    public void searchPartTable(Event event)
    {
        ObservableList<Part> tempPartList = Inventory.lookupPart(modProdPartSearchBar.getText());

        allPartsColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        allPartsColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsColStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsColCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        allPartsTable.setItems(tempPartList);
        allPartsTable.getColumns().setAll(allPartsColID, allPartsColName, allPartsColStock, allPartsColCost);

    }


    public void addParts(Event event)
    {
        selectedProductPart = (Part) allPartsTable.getSelectionModel().getSelectedItem();

        if (!selectedProduct.getAllAssociatedParts().contains(selectedProductPart))
            tempProduct.addAssociatedPart(selectedProductPart);
    }


    public void deleteParts(Event event)
    {
        selectedProductPart = (Part) productPartsTable.getSelectionModel().getSelectedItem();
        tempProduct.deleteAssociatedPart(selectedProductPart);
    }


    public void saveChanges(Event event)
    {
        try
        {
            if (modProdPartName.getText().equals("") || modProdPartCost.getText().equals("") || modProdPartStock.getText().equals(""))
            {
                throw new Exception("Please ensure that you have entered a name, price and inventory quantity!");
            }

            tempProduct.setID(Integer.parseInt(modProdPartID.getText()));
            tempProduct.setName(modProdPartName.getText());
            tempProduct.setPrice(Double.parseDouble(modProdPartCost.getText()));
            tempProduct.setStock(Integer.parseInt(modProdPartStock.getText()));
            tempProduct.setMin(Integer.parseInt(modProdPartMin.getText()));
            tempProduct.setMax(Integer.parseInt(modProdPartMax.getText()));

            if (tempProduct.getStock() < tempProduct.getMin() || tempProduct.getStock() > tempProduct.getMax())
            {
                throw new Exception("Stock must be between minimum and maximum values!");
            }

            Inventory.updateProduct(selectedProduct.getID() - 1, tempProduct);

            Stage stage = (Stage) saveChanges.getScene().getWindow();
            stage.close();
        }
        catch(Exception e)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.toString());
            a.showAndWait();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        populatePartTable();
        modProdPartID.setText(Integer.toString(selectedProduct.getID()));
        modProdPartName.setText(selectedProduct.getName());
        modProdPartStock.setText(Integer.toString(selectedProduct.getStock()));
        modProdPartCost.setText(Double.toString(selectedProduct.getPrice()));
        modProdPartMax.setText(Integer.toString(selectedProduct.getMax()));
        modProdPartMin.setText(Integer.toString(selectedProduct.getMin()));

    }
}
