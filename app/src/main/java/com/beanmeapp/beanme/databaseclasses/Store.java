package com.beanmeapp.beanme.databaseclasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Will contain all of the information associated with a specific store. Should be populated by ParseToolbox.getStore to be read by application.
 */
public class Store {
    private String name;
    private List<Drink> drinks;

    /**
     * Default store will be Starbucks.
     */
    public Store() {
        this("Starbucks");

    }

    /**
     * Constructs a store for a specific name.
     * @param name String, the name of the store.
     */
    public Store(String name) {
        this.name = name;
        drinks = new ArrayList<Drink>();
    }

    /**
     * Gets the name of the store.
     * @return String, the name of the store.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the store.
     * @param name String, the name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of drinks for this store.
     * @return List<Drink>, the list of the drinks at this store.
     */
    public List<Drink> getDrinks() {
        return drinks;
    }

    /**
     * Sets the list of drinks for this store.
     * @param drinks List<Drink>, the list of drinks for this store.
     */
    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }
}
