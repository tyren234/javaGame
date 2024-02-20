package org.game;

import org.game.Items.Item;
import org.game.Items.ItemProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ItemTest {
    @Test
    void testGetQuantity() {
        Item item = new Item("", 0, new ItemProperties());
        Assertions.assertEquals(item.getQuantity(), 0);
    }

    @Test
    void testGetName() {
        String name = "namae";
        Item item = new Item(name, 0, new ItemProperties());
        Assertions.assertEquals(name, item.getName());
    }

    @Test
    void testSetName() {
        String previousName = "previousName";
        String newName = "newName";
        Item item = new Item(previousName, 1, new ItemProperties());
        item.setName(newName);
        Assertions.assertEquals(newName, item.getName());
    }

    @Test
    void testSetQuantity() {
        int previousQuantity = 1;
        int newQuantity= 2;
        Item item = new Item("name", previousQuantity, new ItemProperties());
        item.setQuantity(newQuantity);
        Assertions.assertEquals(item.getQuantity(), newQuantity);
    }

    @Test
    void testNegativeQuantityCreatesZeroQuantityItem() {
        String name = "namae";
        int quantity = -10;
        int expectedQuantity = 0;
        Item item = new Item(name, quantity, new ItemProperties());
        Assertions.assertEquals(item.getQuantity(), expectedQuantity);
    }

    @Test
    void testItemDescription(){
        ItemProperties properties = new ItemProperties();
        String expected = "Uuu! Shiny!";
        properties.description = expected;

        int quantity = 5;
        String name = "Item0";
        Item item = new Item(name, quantity, properties);
        Item itemNoDescription = new Item(name, quantity, new ItemProperties());
        Assertions.assertEquals(item.description(), expected);
        Assertions.assertEquals(itemNoDescription.description(), "");
    }
}