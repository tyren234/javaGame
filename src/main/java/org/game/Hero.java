package org.game;

import org.game.Items.Item;

public class Hero {
    private String name;
    private int findingAbility;
    private Inventory inventory;
    private int health;
    private Item weapon;
    private Item armour;
    private int maxHealth;

    Hero(String name, int findingAbility, int health, int maxHealth) {
        this.name = name;
        this.findingAbility = findingAbility;
        this.inventory = new Inventory();
        this.health = health;
        this.maxHealth = maxHealth;
    }

    Hero(String name, int findingAbility, int health) {
        this(name, findingAbility, health, health);
    }
    public void setFindingAbility(int value) {
        findingAbility = value;
    }

    public int getFindingAbility() {
        return findingAbility;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void addHealth(final int health) {
        this.health = Math.min(health + this.health, maxHealth);
    }

    public void addItems(final Inventory newItems) {
        this.inventory = this.inventory.join(newItems);
    }

    public String info() {
        String info = (
                "Name:       " + name + "\n" +
                        "Health:     " + health + "\n" +
                        "Perception: " + findingAbility + "\n"
        );
        if (getDefence() != 0) {
            info += "Defence:    " + getDefence() + "\n";
        }
        if (getAttack() != 0) {
            info += "Attack:     " + getAttack() + "\n";
        }
        return info;
    }

    public int getDefence() {
        if (armour == null || !armour.getProperties().usable || !armour.getProperties().armour) return 0;
        return armour.getProperties().value;
    }

    public int getAttack() {
        if (weapon == null || !weapon.getProperties().usable || !weapon.getProperties().weapon) return 0;
        return weapon.getProperties().value;
    }

    public void equip(String id, Equipment type) {
        Item item = inventory.getItem(id);
        inventory.useItem(id, 1);
        Item clone = new Item(item);
        clone.setQuantity(1);

        if (type == Equipment.Armour) {
            if (armour != null) inventory.addItem(armour, 1);
            armour = clone;
        } else if (type == Equipment.Weapon) {
            if (weapon != null) inventory.addItem(weapon, 1);
            weapon = clone;
        }
    }

    public String getEquipmentPrintString() {
        if (armour == null && weapon == null) return "You haven't got anything equipped";

        String output = "Your equipment:\n";
        if (armour != null) output += "Armour: " + armour.getName() + " " + getDefence() + "\n";
        if (weapon != null) output += "Weapon: " + weapon.getName() + " " + getAttack();

        return output;
    }

    public boolean isAlive(){
        return health > 0;
    }
}
