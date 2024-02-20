package org.game;

import org.game.Items.Item;
import org.game.Items.ItemLoader;
import org.game.Items.ItemProperties;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    Item[] items = ItemLoader.loadItems("items.json");
    Item getTestItem(final int i, final int quantity) {
        Item item = new Item(items[(i % items.length)]);
        item.setQuantity(quantity);
        return item;
    }

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
    void testGetEmptyItemList() {
        Inventory inventory = new Inventory();

        assertEquals(new HashMap<String, Integer>(), inventory.getItems());
    }

    @Test
    void testGetItemsSizeWithNoItemsReturnsZero() {
        Inventory inventory = new Inventory();

        assertEquals(0, inventory.getItemsSize());
    }

    @Test
    void testGetNoItemsWithNoItemsReturnsZero() {
        Inventory inventory = new Inventory();

        assertEquals(0, inventory.getNoItems());
    }

    @Test
    void testAddCoupleItems() {
        Inventory inventory = createInventoryWithItemsInQuantities(2, new int[]{4, 2});

        assertEquals(2, inventory.getItemsSize());
        assertEquals(6, inventory.getNoItems());
    }

    @Test
    void testGetItemWithId() {
        Inventory inventory = new Inventory();
        Item item = new Item("item", 1, new ItemProperties());
        inventory.addItem(item);

        assertEquals(item.getQuantity(), inventory.getItem(item.getId()).getQuantity());
    }

    @Test
    void testAddItemThatWasInInventoryIncrementsQuantity() {
        Inventory inventory = new Inventory();
        int quantity = 1;
        Item item = getTestItem(1, quantity);
        inventory.addItem(item);
        inventory.addItem(item);

        assertEquals(item.getName(), inventory.getItem(item.getId()).getName());
        assertEquals(2, inventory.getItem(item.getId()).getQuantity());
    }

    @Test
    void testAddNegativeOrZeroQuantityOfItemsReturnsFalse() {
        Inventory inventory = new Inventory();
        Item item = new Item("item", -4, new ItemProperties());
        Item item2 = new Item("item2", 0, new ItemProperties());
        inventory.addItem(item);
        inventory.addItem(item2);

        assertFalse(inventory.addItem(item));
        assertFalse(inventory.addItem(item2));
        assertEquals(0, inventory.getNoItems());
    }

    @Test
    void testGetItemsNames() {
        Inventory inventory = new Inventory();
        Item item1 = getTestItem(1, 1);
        Item item2 = getTestItem(2, 2);
        inventory.addItem(item1);
        inventory.addItem(item2);
        String[] list = {item1.getName(), item2.getName()};
        List<String> trueList = inventory.getItemsNames();
        for (int i = 0; i < list.length; i++){
            assertTrue(trueList.contains(list[i]));
        }
    }

    @Test
    void testUseItemReducesQuantityAndNoItems() {
        Inventory inventory = new Inventory();
        int quantity = 5;
        Item item = getTestItem(1, quantity);
        inventory.addItem(item);

        assertTrue(inventory.useItem(item.getId(), 5));
        assertEquals(0, inventory.getNoItems());
        assertEquals(0, inventory.getItem(item.getId()).getQuantity());
    }

    @Test
    void testUseItemReturnsFalseAndDoesntReduceNoItemsIfInsufficientQuantity() {
        Inventory inventory = new Inventory();
        int quantity = 5;
        Item item = getTestItem(1, quantity);
        inventory.addItem(item);

        assertFalse(inventory.useItem(item.getId(), 6));
        assertEquals(5, inventory.getNoItems());
        assertEquals(5, inventory.getItem(item.getId()).getQuantity());
    }

    @Test
    void testUseItemWithZeroOrNegativeQuantityReturnsFalseAndDoesNothing() {
        Inventory inventory = new Inventory();
        int quantity = 5;
        Item item = getTestItem(1, quantity);
        inventory.addItem(item);

        assertFalse(inventory.useItem(item.getId(), 0));
        assertFalse(inventory.useItem(item.getId(), -1));
        assertEquals(quantity, inventory.getNoItems());
        assertEquals(quantity, inventory.getItem(item.getId()).getQuantity());
    }

    @Test
    void testEquals() {
        Inventory inventory = new Inventory();
        Inventory inventory1 = new Inventory();
        Item item = new Item ("Item", 5, new ItemProperties());
        inventory.addItem(item);
        inventory1.addItem(item);

        assertTrue(inventory.equals(inventory1));
    }
}