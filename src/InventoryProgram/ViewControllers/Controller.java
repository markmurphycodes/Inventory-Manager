package InventoryProgram.ViewControllers;

import InventoryProgram.Models.*;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public static Part selectedPart;
    public static Product selectedProduct;
    public static boolean initialized = true;

    @FXML public RadioButton inHouse, outsourced;
    @FXML public TextField id, name, price, stock, minStock, maxStock, partVar;
    @FXML public Label addPartVarLabel;
    @FXML public javafx.scene.control.Button addPartButton, addProdButton, exitButton;

    @FXML public javafx.scene.control.TextField prodSearchBar, partSearchBar;

    @FXML private TableView<Product> prodTable;
    @FXML private TableColumn<Product, Integer> prodColID;
    @FXML private TableColumn<Product, String> prodColName;
    @FXML private TableColumn<Product, String> prodColStock;
    @FXML private TableColumn<Product, String> prodColCost;

    @FXML private TableView<Part> partTable;
    @FXML private TableColumn<Part, Integer> partColID;
    @FXML private TableColumn<Part, String> partColName;
    @FXML private TableColumn<Part, String> partColStock;
    @FXML private TableColumn<Part, String> partColCost;


    public void addPart(Event event)
    {

        try {
            int addPartID = Inventory.getAllParts().size() + 1;
            String addPartName = name.getText();
            double addPartPrice = Double.parseDouble(price.getText());
            int addPartStock = Integer.parseInt(stock.getText());
            int addPartMin = Integer.parseInt(minStock.getText());
            int addPartMax = Integer.parseInt(maxStock.getText());
            String addPartVar = partVar.getText();

            if (inHouse.isSelected()) {
                InHouse inHouseProd = new InHouse(addPartID, addPartName, addPartPrice, addPartStock, addPartMin, addPartMax, Integer.parseInt(addPartVar));

                if (inHouseProd.getStock() < inHouseProd.getMin() || inHouseProd.getStock() > inHouseProd.getMax())
                    throw new Exception("Stock must be between minimum and maximum values!");

                Inventory.addPart(inHouseProd);

            } else if (outsourced.isSelected()) {
                Outsourced outsourcedProd = new Outsourced(addPartID, addPartName, addPartPrice, addPartStock, addPartMin, addPartMax, addPartVar);

                if (outsourcedProd.getStock() < outsourcedProd.getMin() || outsourcedProd.getStock() > outsourcedProd.getMax())
                    throw new Exception("Stock must be between minimum and maximum values!");

                Inventory.addPart(outsourcedProd);
            }

            Stage stage = (Stage) addPartButton.getScene().getWindow();
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
        if (inHouse.isSelected())
        {
            addPartVarLabel.setText("Machine ID");
        }
        else
        {
            addPartVarLabel.setText("Company Name");
        }
    }


    public void deletePart(Event event)
    {
        Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());
    }

    public void deleteProduct(Event event)
    {
        Inventory.deleteProduct(prodTable.getSelectionModel().getSelectedItem());
    }


    /*
    Populate tables with product and part information
     */
    public void populateProductTable()
    {

        ObservableList<Product> populateListValues = Inventory.getAllProducts();

        prodColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        prodColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodColStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodColCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodTable.setItems(populateListValues);
        prodTable.getColumns().setAll(prodColID, prodColName, prodColStock, prodColCost);

    }

    public void populatePartTable()
    {

        ObservableList<Part> populateListValues = Inventory.getAllParts();

        partColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        partColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partColStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partColCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTable.setItems(populateListValues);
        partTable.getColumns().setAll(partColID, partColName, partColStock, partColCost);

    }


    public void searchPartTable(Event event)
    {
        ObservableList<Part> tempPartList = Inventory.lookupPart(partSearchBar.getText());

        partColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        partColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partColStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partColCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTable.setItems(tempPartList);
        partTable.getColumns().setAll(partColID, partColName, partColStock, partColCost);

    }


    public void searchProdTable(Event event)
    {

        ObservableList<Product> tempProdList = Inventory.lookupProduct(prodSearchBar.getText());

        prodColID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        prodColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodColStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodColCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodTable.setItems(tempProdList);
        prodTable.getColumns().setAll(prodColID, prodColName, prodColStock, prodColCost);

    }


    /*
    Opening dialogues based on button clicks
     */
    public void openAddPart(Event event)
    {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAddProduct(Event event)
    {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openModifyProduct(Event event)
    {
        if (prodTable.getSelectionModel().getSelectedItem() != null) {
            try {
                selectedProduct = Inventory.lookupProduct(prodTable.getSelectionModel().getSelectedItem().getID());
                Parent parent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void openModifyPart(Event event)
    {
        if (partTable.getSelectionModel().getSelectedItem() != null) {
            try {
                selectedPart = Inventory.lookupPart(partTable.getSelectionModel().getSelectedItem().getID());
                Parent parent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /*
        Exit button
    */
    public void exitButtonAction(Event event)
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        if (initialized)
        {
            populateProductTable();
            populatePartTable();
            initialized = false;
        }
    }

}
