package com.dwhi219.util;

/**
 * Constants for use throughout the application
 *
 * @author Dan
 */
public class Constants {

    public enum Mode {
        ADD, MODIFY;
    }

    public static final String CONFIRMATION = "Are you sure you want to do that?";
    public static final String MISSING_NAME = "Missing name\n";
    public static final String MISSING_INVENTORY = "Missing inventory count\n";
    public static final String INVENTORY_NAN = "Inventory count must be a number\n";
    public static final String NEGATIVE_INVENTORY = "Inventory cannot be negative\n";
    public static final String MISSING_PRICE = "Missing price\n";
    public static final String PRICE_NAN = "Price must be a number\n";
    public static final String NEGATIVE_PRICE = "Price cannot be negative\n";
    public static final String MISSING_MAX = "Missing maximum count\n";
    public static final String MAX_NAN = "Maximum count must be a number\n";
    public static final String NEGATIVE_MAX = "Maximum count cannot be negative\n";
    public static final String MISSING_MIN = "Missing minimum count\n";
    public static final String MIN_NAN = "Minimum count must be a number\n";
    public static final String NEGATIVE_MIN = "Minimum count cannot be negative\n";
    public static final String MISSING_MACHINE_ID = "Missing machine ID\n";
    public static final String MACHINE_ID_NAN = "Machine ID must be a number\n";
    public static final String MISSING_COMPANY_NAME = "Missing company name\n";
    public static final String MIN_MORE_MAX = "Minimum count cannot be more than the maximum count\n";
    public static final String MAX_LESS_MIN = "Maximum count cannot be less than the minimum count\n";
    public static final String INV_OUT_OF_BOUNDS = "Inventory count must be between minimum and maximum counts\n";
    public static final String NO_ASSOC_PARTS = "Product must have at least one associated part";
    public static final String PRICE_TOO_LOW = "Product price cannot be lower than the cost of its associated parts";
}
