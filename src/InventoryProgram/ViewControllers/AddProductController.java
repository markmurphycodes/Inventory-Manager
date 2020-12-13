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

public class AddProductController implements Initializable{

    private Product tempProduct = new Product();

    @FXML public javafx.scene.control.TextField partSearchBar;

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

    @FXML public TextField addProdID, addProdName, addProdStock, addProdCost, addProdMax, addProdMin;

    @FXML public Button addParts, deleteParts, saveChanges, exitButton;

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
        ObservableList<Part> tempPartList = Inventory.lookupPart(partSearchBar.getText());

        allPartsColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        allPartsColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsColStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsColCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        allPartsTable.setItems(tempPartList);
        allPartsTable.getColumns().setAll(allPartsColID, allPartsColName, allPartsColStock, allPartsColCost);

    }

    public void addParts(Event event)
    {
        Part selectedProductPart = (Part) allPartsTable.getSelectionModel().getSelectedItem();
        tempProduct.addAssociatedPart(selectedProductPart);
    }

    public void deleteParts(Event event)
    {
        Part selectedProductPart = (Part) productPartsTable.getSelectionModel().getSelectedItem();
        tempProduct.deleteAssociatedPart(selectedProductPart);
    }

    public void saveChanges(Event event)
    {
        String errorMessage;
        try
        {
            if (addProdName.getText().equals("") || addProdCost.getText().equals("") || addProdStock.getText().equals(""))
            {
                throw new Exception("Please ensure that you have entered a name, price and inventory quantity!");
            }

            tempProduct.setID(Inventory.getAllProducts().size() + 1);
            tempProduct.setName(addProdName.getText());
            tempProduct.setPrice(Double.parseDouble(addProdCost.getText()));
            tempProduct.setStock(Integer.parseInt(addProdStock.getText()));
            tempProduct.setMin(Integer.parseInt(addProdMin.getText()));
            tempProduct.setMax(Integer.parseInt(addProdMax.getText()));

            if (tempProduct.getStock() < tempProduct.getMin() || tempProduct.getStock() > tempProduct.getMax())
            {
                throw new Exception("Stock must be between minimum and maximum values!");
            }

            Inventory.addProduct(tempProduct);

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
    }

}
