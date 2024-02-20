package org.game.Items;

import org.game.Inventory;

import java.util.Random;

public final class RandomItemsListGenerator {
    private static Random generator = new Random();

    public static <T> T getRandomElement(T[] array) {
        int random = generator.nextInt(array.length);
        return array[random];
    }

    public static Inventory getRandomItems(final int numberOfItems, final Item[] itemsList) {
        Inventory newInventory = new Inventory();
        for (int i = 0; i < numberOfItems; i++) {
            Item randomItem = getRandomElement(itemsList);
            randomItem.setQuantity(1);
            newInventory.addItem(randomItem);
        }
        return newInventory;
    }
}
