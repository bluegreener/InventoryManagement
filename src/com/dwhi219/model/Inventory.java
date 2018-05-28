package com.dwhi219.model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents the entire company inventory, including all available parts and
 * products
 *
 * @author Dan
 */
public class Inventory {

    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Part> allParts = FXCollections.observableArrayList();

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> products) {
        this.products = products;
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public void setAllParts(ObservableList<Part> allParts) {
        this.allParts = allParts;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public boolean removeProduct(int id) {
        Product p = lookupProduct(id);
        return products.remove(p);
    }

    public Product lookupProduct(int id) {
        for (Product p : products) {
            if (id == p.getProductID()) {
                return p;
            }
        }
        return null;
    }

    public void updateProduct(int id, Product product) {
        int index = products.indexOf(lookupProduct(id));
        products.set(index, product);
    }

    public void addPart(Part part) {
        allParts.add(part);
    }

    public boolean deletePart(Part part) {
        if (allParts.indexOf(part) >= 0) {
            allParts.remove(part);
            return true;
        } else {
            return false;
        }
    }

    public Part lookupPart(int id) {
        for (Part p : allParts) {
            if (id == p.getPartID()) {
                return p;
            }
        }
        return null;
    }

    public void updatePart(int id, Part part) {
        int index = allParts.indexOf(lookupPart(id));
        allParts.set(index, part);
    }
}
