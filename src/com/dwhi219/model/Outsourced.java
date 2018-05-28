package com.dwhi219.model;

/**
 * A part object that comes from an external organization
 *
 * @author Dan
 */
public class Outsourced extends Part {

    private String companyName;

    public Outsourced() {
        super();
    }

    public Outsourced(Inhouse part) {
        super(part);
    }

    public Outsourced(String name, double price, int inStock, int min, int max, String companyName) {
        super(name, price, inStock, min, max);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
