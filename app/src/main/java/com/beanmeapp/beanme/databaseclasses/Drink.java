package com.beanmeapp.beanme.databaseclasses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Contains all of the attributes associated with drinks along with setters and getters.
 */
public class Drink {


    private DrinkAttributes.Name name;
    private DrinkAttributes.Size size;
    private DrinkAttributes.Flavor flavor;
    private DrinkAttributes.Temperature temperature;
    private DrinkAttributes.Blend blend;
    private DrinkAttributes.Milk milk;
    private int numShots;
    private List<DrinkAttributes.Addon> addonList;
    private List<String> comments;

    /**
     * Default constructor, creates a plain coffee drink.
     */
    public Drink() {
        this(DrinkAttributes.Name.COFFEE);
    }

    /**
     * Constructs the drink with the specific name.
     * @param name String, the name of the drink.
     */
    public Drink(DrinkAttributes.Name name) {
        //TODO initialize all of the other fields
        this.name = name;
        addonList = new ArrayList<DrinkAttributes.Addon>();
        comments = new ArrayList<String>();
    }

    /**
     * Constructor for all fields
     * @param name String - name of drink
     * @param size DrinkAttributes.Size, the size to set the drink.
     * @param flavor ^
     * @param temperature ^
     * @param blend ^
     * @param milk ^
     * @param numShots int, the number of shots to set it to.
     * @param addonList List<DrinkAttributes.Addon>, the addon list to set the drink.'
     * @param comments List<String>, the comments for the drink.
     */
    public Drink(DrinkAttributes.Name name, DrinkAttributes.Size size, DrinkAttributes.Flavor flavor,
                 DrinkAttributes.Temperature temperature, DrinkAttributes.Blend blend,
                 DrinkAttributes.Milk milk, int numShots, List<DrinkAttributes.Addon> addonList,
                 List<String> comments) {
        this.name = name;
        this.size = size;
        this.flavor = flavor;
        this.temperature = temperature;
        this.blend = blend;
        this.milk = milk;
        this.numShots = numShots;
        this.addonList = addonList;
        this.comments = comments;
    }

    /**
     * Returns the name of the drink.
     * @return String, the name of the drink.
     */
    public DrinkAttributes.Name getName() {
        return name;
    }

    /**
     * Sets the name of the drink.
     * @param name String, what to name the drink.
     */
    public void setName(DrinkAttributes.Name name) {
        this.name = name;
    }

    /**
     * Gets the size of the drink.
     * @return DrinkAttributes.Size, the size of the drink.
     */
    public DrinkAttributes.Size getSize() {
        return size;
    }

    /**
     * Sets the size of the drink.
     * @param size DrinkAttributes.Size, the size to set the drink.
     */
    public void setSize(DrinkAttributes.Size size) {
        this.size = size;
    }

    /**
     * Gets the flavor of the drink.
     * @return DrinkAttributes.Flavor, the flavor of the drink.
     */
    public DrinkAttributes.Flavor getFlavor() {
        return flavor;
    }

    /**
     * Sets the flavor of the drink.
     * @param flavor DrinkAttributes.Flavor, the flavor to set the drink.
     */
    public void setFlavor(DrinkAttributes.Flavor flavor) {
        this.flavor = flavor;
    }

    /**
     * Gets the temperature of the Drink.
     * @return DrinkAttributes.Temperature, the temperature of the drink.
     */
    public DrinkAttributes.Temperature getTemperature() {
        return temperature;
    }

    /**
     * Sets the temperature of the drink.
     * @param temperature DrinkAttributes.Temperature, the temperature to set the drink.
     */
    public void setTemperature(DrinkAttributes.Temperature temperature) {
        this.temperature = temperature;
    }

    /**
     * Gets the blend of the drink.
     * @return DrinkAttributes.Blend, the blend of the drink.
     */
    public DrinkAttributes.Blend getBlend() {
        return blend;
    }

    /**
     * Sets the blend of the drink.
     * @param blend DrinkAttributes.Blend, the blend to set the drink.
     */
    public void setBlend(DrinkAttributes.Blend blend) {
        this.blend = blend;
    }

    /**
     * Gets the type of milk in the drink.
     * @return DrinkAttributes.Milk, the milk that is in the drink.
     */
    public DrinkAttributes.Milk getMilk() {
        return milk;
    }

    /**
     * Sets the type of milk that is in the drink.
     * @param milk DrinkAttributes.Milk, the milk to put in the drink.
     */
    public void setMilk(DrinkAttributes.Milk milk) {
        this.milk = milk;
    }

    /**
     * Gets the number of shots in the drink.
     * @return int, the number of shots in the drink.
     */
    public int getNumShots() {
        return numShots;
    }

    /**
     * Sets the number of shots in the drink.
     * @param numShots int, the number of shots to set it to.
     */
    public void setNumShots(int numShots) {
        this.numShots = numShots;
    }

    /**
     * Adds a single shot more to the drink.
     */
    public void addShot() {
        ++this.numShots;
    }

    /**
     * Takes away a shot if there is atleast one in the drink.
     */
    public void subShot() {
        if (numShots > 0) {
            --this.numShots;
        }
    }

    /**
     * Gets a list of all of the addons in the drink.
     * @return List<DrinkAttributes.Addon>, the list of addons in the drink.
     */
    public List<DrinkAttributes.Addon> getAddonList() {
        return addonList;
    }

    /**
     * Sets all of the addons in the drink.
     * @param addonList List<DrinkAttributes.Addon>, the addon list to set the drink.
     */
    public void setAddonList(List<DrinkAttributes.Addon> addonList) {
        addonList = addonList;
    }

    /**
     * Adds an Addon to the list of addons for the drink.
     * @param addon Addon, the addon to add.
     * @return boolean, true if it was able to add.
     */
    public boolean addAddon(DrinkAttributes.Addon addon) {
        return addonList.add(addon);
    }

    /**
     * Removes an addon from the drink.
     * @param addon Addon, the addon to remove.
     * @return boolean, true if it was able to remove the addon.
     */
    public boolean removeAddon(DrinkAttributes.Addon addon) {
        for (Iterator<DrinkAttributes.Addon> it = addonList.iterator(); it.hasNext();) {
            DrinkAttributes.Addon add = it.next();
            if (add == addon) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of comments.
     * @return List<String>, the comments for the drink.
     */
    public List<String> getComments() {
        return comments;
    }

    /**
     * Sets the comments for the drink.
     * @param comments List<String>, the comments for the drink.
     */
    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    /**
     * Adds a comment to the comments list.
     * @param comment String, the comment to add.
     * @return boolean, true if could successfully add the comment.
     */
    public boolean addComment(String comment) {
        return this.comments.add(comment);
    }

    /**
     * Removes the specific comment.
     * @param comment String, the comment to remove.
     * @return boolean, if it could remove the comment.
     */
    public boolean removeComment(String comment) {
        return removeComment(comment, -1);
    }

    /**
     * Removes the comment at a specific location.
     * @param position int, the position of the comment to remove.
     * @return boolean, if it could remove the comment.
     */
    public boolean removeComment(int position) {
        return removeComment(null, position);
    }

    /**
     * Handles deletion of the comments.
     * @param comment String, the comment to remove.
     * @param position int, the position of the comment to remove.
     * @return boolean, if the comment could be removed.
     */
    private boolean removeComment(String comment, int position) {
        if (comment != null) {
            for (int i = 0; i < comments.size(); ++i) {
                if (comments.get(i).equals(comment)) {
                    comments.remove(i);
                    return true;
                }
            }
        } else if (position > 0 && position < comments.size()) {
            comments.remove(position);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.size.toString().toLowerCase());
        builder.append(" ");

        builder.append(this.temperature.toString().toLowerCase());
        builder.append(" ");

        if (this.blend != null && this.blend != DrinkAttributes.Blend.NONE) {
            builder.append(this.blend.toString().toLowerCase());
            builder.append(" ");
        }

        if (this.milk != null && this.milk != DrinkAttributes.Milk.NONE) {
            builder.append(this.milk.toString().toLowerCase());
            builder.append(" milk ");
        }

        if (this.flavor != null && this.flavor != DrinkAttributes.Flavor.NONE) {
            builder.append(this.flavor.toString().toLowerCase());
            builder.append(" ");
        }

        builder.append(this.name.toString().toLowerCase());

        if (this.numShots != 0) {
            builder.append(" with ");
            builder.append(this.numShots);
            builder.append(" shots");
        }

        if (this.addonList != null && this.addonList.size() > 0) {
            if (numShots == 0) {
                builder.append(" with ");
            } else {
                builder.append(" and ");
            }
            for (DrinkAttributes.Addon addon : this.addonList) {
                builder.append(addon.toString().toLowerCase());
                if (this.addonList.indexOf(addon) != this.addonList.size() - 1)
                    builder.append(", ");
                else
                    builder.append(".");
            }
        }


        if (this.comments != null && !(this.comments.size() > 0)) {
            for (String comment : this.comments) {
                builder.append(comment + " ");
            }
        }

        builder.append("\n");
        return builder.toString();
    }

    /**
     * Serializes the drink to be sent to a host.
     * @param drink Drink, the drink to be serialized.
     * @return String, the serialized drink.
     */
    public static String serialize(Drink drink) {
        StringBuilder builder = new StringBuilder();
        builder.append("name:" + drink.getName() + " ");
        builder.append("size:" + drink.getSize() + " ");
        builder.append("flavor:" + drink.getFlavor() + " ");
        builder.append("temperature:" + drink.getTemperature() + " ");
        builder.append("blend:" + drink.getBlend() + " ");
        builder.append("milk:" + drink.getMilk() + " ");
        builder.append("numShots:" + drink.getNumShots() + " ");
        builder.append("addonList:");
        for (int i = 0; i < drink.getAddonList().size(); ++i) {
            builder.append(drink.getAddonList().get(i));
            if (i == (drink.getAddonList().size() - 1)) {
                builder.append(" ");
            } else {
                builder.append(",");
            }
        }
        builder.append("comments:" + drink.getComments());
        for (int i = 0; i < drink.getComments().size(); ++i) {
            builder.append(drink.getComments().get(i));
            if (i != (drink.getComments().size() - 1)) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    /**
     * Inflates the string to a drink.
     * @param drinkString String, the serialized version of the drink.
     * @return Drink, the inflated string.
     */
    public static Drink inflate(String drinkString) {
        Drink drink = new Drink();
        String[] splitDrink = drinkString.split(" ");
        for (int i = 0; i < splitDrink.length; ++i) {
            switch (i) {
                case 0: //name
                    try {
                        drink.setName(DrinkAttributes.Name.valueOf(splitDrink[i].substring(splitDrink[i].indexOf(":") + 1)));
                    } catch (Exception e) {
                        drink.setName(DrinkAttributes.Name.COFFEE);
                    }
                    break;
                case 1: //size
                    try {
                        drink.setSize(DrinkAttributes.Size
                                .valueOf(splitDrink[i].substring(splitDrink[i].indexOf(":") + 1)));
                    } catch (Exception e) {
                        drink.setSize(DrinkAttributes.Size.MEDIUM);
                    }
                    break;
                case 2: //flavor
                    try {
                        drink.setFlavor(DrinkAttributes.Flavor
                                .valueOf(splitDrink[i].substring(splitDrink[i].indexOf(":") + 1)));
                    } catch (Exception e) {
                        drink.setFlavor(DrinkAttributes.Flavor.NONE);
                    }
                    break;
                case 3: //temperature
                    try {
                        drink.setTemperature(DrinkAttributes.Temperature
                                .valueOf(splitDrink[i].substring(splitDrink[i].indexOf(":") + 1)));
                    } catch (Exception e) {
                        drink.setTemperature(DrinkAttributes.Temperature.HOT);
                    }
                    break;
                case 4: //blend
                    try {
                        drink.setBlend(DrinkAttributes.Blend
                                .valueOf(splitDrink[i].substring(splitDrink[i].indexOf(":") + 1)));
                    } catch (Exception e) {
                        drink.setBlend(DrinkAttributes.Blend.NONE);
                    }
                    break;
                case 5: //milk
                    try {
                        drink.setMilk(DrinkAttributes.Milk
                                .valueOf(splitDrink[i].substring(splitDrink[i].indexOf(":") + 1)));
                    } catch (Exception e) {
                        drink.setMilk(DrinkAttributes.Milk.WHOLE);
                    }
                    break;
                case 6: //numShots
                    try {
                        drink.setNumShots(
                                Integer.parseInt(
                                        splitDrink[i].substring(splitDrink[i].indexOf(":") + 1)));
                    } catch (Exception e) {
                        drink.setNumShots(0);
                    }
                    break;
                case 7: //addonList
                    try {
                        String[] addons = splitDrink[i].substring(splitDrink[i].indexOf(":") + 1)
                                .split(",");
                        for (String addon : addons) {
                            drink.addAddon(DrinkAttributes.Addon.valueOf(addon));
                        }
                    } catch (Exception e) {
                        drink.setAddonList(new ArrayList<DrinkAttributes.Addon>());
                    }
                    break;
                case 8: //comments
                    try {
                        String[] comments = splitDrink[i].substring(splitDrink[i].indexOf(":") + 1)
                                .split(",");
                        for (String comment : comments) {
                            drink.addComment(comment);
                        }
                    } catch (Exception e) {
                        drink.setComments(new ArrayList<String>());
                    }
                    break;
            }
        }
        return drink;
    }
}
