package com.dwhi219.model;

/**
 * Object representing one part, for use in products
 *
 * @author Dan
 */
public abstract class Part {

    private static int count = 0;
    private int partID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    public Part() {
        this.partID = ++count;
    }

    public Part(Part part) {
        this.partID = part.getPartID();
        this.name = part.getName();
        this.price = part.getPrice();
        this.inStock = part.getInStock();
        this.min = part.getMin();
        this.max = part.getMax();
    }

    public Part(String name, double price, int inStock, int min, int max) {
        this.partID = ++count;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

}
