package com.beanmeapp.beanme.databaseclasses;

/**
 * The different attributes for the drinks.
 */
public class DrinkAttributes {

    public enum Name {
        COFFEE, MOCHA, LATTE, FRAPPUCCINO, CAPPUCCINO, ESPRESSO, AMERICANO, TEA
    }

    public enum Addon {
        //WHIP_CREAM, CHOCOLATE_SYRUP, CARAMEL_SYRUP, CHOCOLATE_POWDER, VANILLA_POWDER, NUTMEG, CINNAMON
        FOAM, CHOCOLATE, CARAMEL, WHIPPED_CREAM
    }

    public enum Size {
        SMALL, MEDIUM, LARGE, EXTRA_LARGE
    }

    public enum Flavor {
        NONE, HAZELNUT, COCONUT, GREEN_TEA, CHAI_TEA, CHOCOLATE, VANILLA
    }

    public enum Temperature {
        COLD, HOT
    }

    public enum Blend {
        NONE, BLENDED, ICED
    }

    public enum Milk {
        NONE, CREAM, ALMOND, SOY, WHOLE, ONE_PERCENT, TWO_PERCENT, NON_FAT
    }
}
