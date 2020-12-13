package InventoryProgram.Models;

abstract public class Part implements Cloneable{

    private int partID;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    public Part(int id, String name, double price, int stock, int min, int max) {
        this.partID = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /*
    SETTERS
     */
    public void setID(int id) { this.partID = id; }

    public void setName(String name) { this.name = name; }

    public void setPrice(double price) { this.price = price; }

    public void setStock(int stock) { this.stock = stock; }

    public void setMin(int min) { this.min = min; }

    public void setMax(int max) { this.max = max; }

    /*
    GETTERS
     */
    public int getID() { return this.partID; }

    public String getName() { return this.name; }

    public double getPrice() { return this.price; }

    public int getStock() { return this.stock; }

    public int getMin() { return this.min; }

    public int getMax() { return this.max; }


    @Override
    public Object clone() throws CloneNotSupportedException {

        Object obj = super.clone();

        Part part = (Part) obj;

        return part;

    }


}
