package com.dwhi219.model;

import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 * Object representing one product
 *
 * @author Dan
 */
public class Product {

    private ObservableList<Part> associatedParts;
    private static int count = 0;
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    public Product() {
        this.productID = ++count;
    }

    public Product(ObservableList<Part> associatedParts, String name, double price, int inStock, int min, int max) {
        this.associatedParts = associatedParts;
        this.productID = ++count;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
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

    public void addAssociatedPart(Part part) {
        if (!associatedParts.contains(part)) {
            associatedParts.add(part);
        }
    }

    public boolean removeAssociatedPart(int id) {
        Part p = lookupAssociatedPart(id);
        return associatedParts.remove(p);
    }

    public Part lookupAssociatedPart(int id) {
        for (Part p : associatedParts) {
            if (id == p.getPartID()) {
                return p;
            }
        }
        return null;
    }
}
