package org.game;

import org.game.Items.Item;
import org.game.Items.ItemProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HeroTest {
    Hero getBasicHero() {return new Hero("Guy",  0, 1);}
    Hero getNormalHero(){return new Hero("Artur", 4, 20);}
    Inventory createInventoryWithItemsInQuantities
            (int noItems, int[] quantities){
        Inventory inventory = new Inventory();
        Item item;
        for (int i = 0; i < noItems; i++){
            item = new Item(String.format("item%s", i), quantities[i], new ItemProperties());
            inventory.addItem(item);
        }
        return inventory;
    }

    @Test
    void testGetSetFiningAbility() {
        Hero hero = getNormalHero();
        Assertions.assertEquals(4, hero.getFindingAbility());
        hero.setFindingAbility(6);
        Assertions.assertEquals(6, hero.getFindingAbility());
    }

    @Test
    void testGetSetInventory() {
        Hero hero = getBasicHero();
        Assertions.assertEquals(new Inventory(), hero.getInventory());
        Inventory inventory = createInventoryWithItemsInQuantities(2, new int[]{5,3});
        hero.setInventory(inventory);
        Assertions.assertEquals(inventory, hero.getInventory());
    }

    @Test
    void testGetSetHealth() {
        Hero hero = getBasicHero();
        int newHealth = 42;
        hero.setHealth(newHealth);
        Assertions.assertEquals(newHealth, hero.getHealth());
    }


}