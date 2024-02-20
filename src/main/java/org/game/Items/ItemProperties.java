package org.game.Items;

public class ItemProperties {
    public String description;
    public int cost;
    public boolean edible;
    public int healthRestored;
    public boolean usable;
    public boolean weapon;
    public boolean armour;
    public int value;

    public String toString() {
        return "Cost: " + cost
                + " description: " + description
                + " edible: " + edible
                + " healthRestored: " + healthRestored
                + " weapon: " + weapon
                + " armour: " + armour
                + " value: " + value;
    }
}
