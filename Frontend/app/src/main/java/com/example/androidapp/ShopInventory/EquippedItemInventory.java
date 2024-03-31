package com.example.androidapp.ShopInventory;

public class EquippedItemInventory extends ListItemObjectInventory{
    private final String name;
    private final String description;


    /**
     * Creates a List Item Object for Shop items
     *
     * @param name        The name of the item
     * @param description The description of the object
     */
    public EquippedItemInventory(String name, String description) {
        super(name, description);
        this.name = name;
        this.description = description;
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
