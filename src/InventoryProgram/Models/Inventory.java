package InventoryProgram.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) { allParts.add(allParts.size(), newPart); }

    public static void addProduct(Product newProduct) { allProducts.add(newProduct); }

    public static Part lookupPart(int partID)
    {

        for (int i = 0; i <= allParts.size(); i++)
        {
            if (allParts.get(i).getID() == partID)
                return allParts.get(i);
        }

        return null;
    }


    public static ObservableList<Part> lookupPart(String partName)
    {

        ObservableList<Part> returnList = FXCollections.observableArrayList();

        for (int i = 0; i < allParts.size(); i++)
        {
            if (allParts.get(i).getName().toLowerCase().contains(partName.toLowerCase()))
                returnList.add(allParts.get(i));
        }

        return returnList;
    }


    public static Product lookupProduct(int productID)
    {

        for (int i = 0; i <= allProducts.size(); i++)
        {
            if (allProducts.get(i).getID() == productID)
                return allProducts.get(i);
        }

        return null;
    }

    public static ObservableList<Product> lookupProduct(String productName) {

        ObservableList<Product> returnList = FXCollections.observableArrayList();

        for (int i = 0; i < allProducts.size(); i++)
        {
            if (allProducts.get(i).getName().toLowerCase().contains(productName.toLowerCase()))
                returnList.add(allProducts.get(i));
        }

        return returnList;
    }

    public static void updatePart(int index, Part newPart) {

        allParts.set(index, newPart);

    }

    public static void updateProduct(int index, Product newProduct){

        allProducts.set(index, newProduct);

    }

    public static boolean deletePart(Part selectedPart) {

        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }

        return false;

    }

    public static boolean deleteProduct(Product selectedProduct) {

        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }

        return false;

    }

    public static ObservableList<Part> getAllParts() { return allParts; }

    public static ObservableList<Product> getAllProducts() { return allProducts; }
}
