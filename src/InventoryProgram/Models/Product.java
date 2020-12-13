package InventoryProgram.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product implements  Cloneable{

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    public Product(int id, String name, double price, int stock, int min, int max) {
        this.productID = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public Product() {

    }

    /*
    SETTERS
     */
    public void setID(int id) { this.productID = id; }

    public void setName(String name) { this.name = name; }

    public void setPrice(double price) { this.price = price; }

    public void setStock(int stock) { this.stock = stock; }

    public void setMin(int min) { this.min = min; }

    public void setMax(int max) { this.max = max; }

    /*
    GETTERS
     */
    public int getID() { return this.productID; }

    public String getName() { return this.name; }

    public double getPrice() { return this.price; }

    public int getStock() { return this.stock; }

    public int getMin() { return this.min; }

    public int getMax() { return this.max; }

    public ObservableList<Part> getAllAssociatedParts() { return this.associatedParts; }


    /*
    Add or delete an associated part
     */
    public void addAssociatedPart(Part part) {

        if (!this.associatedParts.contains(part))
            this.associatedParts.add(part);
    }

    public boolean deleteAssociatedPart(Part selectedAssPart) {

        if (this.associatedParts.contains(selectedAssPart))
        {
            associatedParts.remove(selectedAssPart);
            return true;
        }

       return false;

    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        Product prod = (Product) super.clone();

        // deep cloning for immutable fields
        prod.associatedParts = FXCollections.observableArrayList();
        // Deep Copy of field by field
        for (int i = 0; i < this.associatedParts.size(); i++) {
            System.out.println(this.associatedParts.get(i));
            prod.addAssociatedPart((Part)this.associatedParts.get(i).clone());
            System.out.println(prod.associatedParts.get(i));
        }

        return prod;

    }
}
