package com.example.androidapp.ShopInventory;

/**
 * Class for the items to be placed into the list adapter
 */
public class ListItemObjectShop {
    public final String name;
    public final String description;

    /**
     * Creates a List Item Object for Shop items
     * @param name The name of the item
     * @param description The description of the object
     */
    public ListItemObjectShop(String name, String description) {
        this.name = name;
        this.description = String.valueOf(description);
    }

    /**
     * Gets the name of the item
     * @return Returns the item name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the item
     * @return Returns the price of the item
     */
    public String getDescription() {
        return description;
    }
}
