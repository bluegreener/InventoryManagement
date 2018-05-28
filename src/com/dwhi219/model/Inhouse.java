package com.dwhi219.model;

/**
 * A part that has been produced in-house
 *
 * @author Dan
 */
public class Inhouse extends Part {

    private int machineID;

    public Inhouse() {
        super();
    }

    public Inhouse(Outsourced part) {
        super(part);
    }

    public Inhouse(String name, double price, int inStock, int min, int max, int machineID) {
        super(name, price, inStock, min, max);
        this.machineID = machineID;
    }

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

}
