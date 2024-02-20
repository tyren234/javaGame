package org.game;

import org.game.Items.Item;
import org.game.Items.ItemProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

class RoomTest {
    void testFindHiddenItemsWithFindability(int findability, int findingDifficulty){
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item("Item0", 2, new ItemProperties()));

        ArrayList<Integer> intList = new ArrayList<>();
        intList.add(new Integer(findingDifficulty));

        Room room = new Room(false, false, true, false,
                itemList, intList);
        Inventory foundItems = room.findHiddenItems(findability);

        if (findability >= findingDifficulty){
            Assertions.assertEquals(1, foundItems.getItemsSize());
            Assertions.assertEquals("Item0", foundItems.getItemsNames().get(0));
            Assertions.assertEquals(2, foundItems.getItems().values().stream().map(Item::getQuantity).collect(Collectors.toList()).get(0));
        }
        else{
            Assertions.assertEquals(0, foundItems.getNoItems());
        }
    }

    void printItemsArrayList(ArrayList<Item> arrList){
        for (Item item : arrList){
            System.out.printf("[%s, %d]", item.getName(), item.getQuantity());
        }
        System.out.println();
    }
    @Test
    void testIsNESW() {
        Room room = new Room(true,true,true,true);
        Assertions.assertTrue(room.isN());
        Assertions.assertTrue(room.isE());
        Assertions.assertTrue(room.isW());
        Assertions.assertTrue(room.isS());
    }

    @Test
    void testIsNotNESW() {
        Room room = new Room(false, false, false, false);
        Assertions.assertFalse(room.isN());
        Assertions.assertFalse(room.isE());
        Assertions.assertFalse(room.isW());
        Assertions.assertFalse(room.isS());
    }

    @Test
    void testFindHiddenItemsWithAdequateFindingAbility() {
        testFindHiddenItemsWithFindability(2,2);
    }

    @Test
    void testFindHiddenItemsWithGreaterFindingAbility() {
        testFindHiddenItemsWithFindability(5,2);
    }

    @Test
    void testFindHiddenItemsWithLowerFindingAbility(){
        testFindHiddenItemsWithFindability(1, 2);
    }

    @Test
    void testFindHiddenItemsWorksOnlyOnce() {
        int findingDifficulty = 2;
        int findability = 2;

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item("Item0", 2, new ItemProperties()));

        ArrayList<Integer> findingDiffList = new ArrayList<>();
        findingDiffList.add(findingDifficulty);

        Room room = new Room(false, false, true, false,
                itemList, findingDiffList);

        room.findHiddenItems(findability);
        Inventory items2 = room.findHiddenItems(findability);

        Assertions.assertEquals(0, items2.getNoItems());
    }

    @Test
    void testFindHiddenItemsWorksOnlyOnceWithManyItems() {

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item("Item0", 2, new ItemProperties()));
        itemList.add(new Item("Item1", 2, new ItemProperties()));
        itemList.add(new Item("Item2", 2, new ItemProperties()));

        ArrayList<Integer> findingDiffList = new ArrayList<>();
        findingDiffList.add(0);
        findingDiffList.add(1);
        findingDiffList.add(2);

        Room room = new Room(false, false, true, false,
                itemList, findingDiffList);

        Inventory itemsFound1 = room.findHiddenItems(2);
        Inventory itemsFound2 = room.findHiddenItems(2);

        Assertions.assertEquals(3, itemsFound1.getItemsSize());
        Assertions.assertEquals(6, itemsFound1.getNoItems());
        Assertions.assertEquals(0, itemsFound2.getItemsSize());
        Assertions.assertEquals(0, itemsFound2.getNoItems());
    }

    @Test
    void testRoomEnterGivesYouVisibleItemsAndTestVisibleItemsConstructor(){
        Inventory visibleItems = new Inventory();
        Item item = new Item("Item0", 5, new ItemProperties());
        visibleItems.addItem(item);
        Room room = new Room(false, false, true, false, visibleItems);

        Inventory itemsFound = room.enter();

        Assertions.assertEquals(1, itemsFound.getItemsSize());
        Assertions.assertEquals("Item0", itemsFound.getItem(item).getName());
        Assertions.assertEquals(5, itemsFound.getItem(item).getQuantity());
        Assertions.assertEquals(5, itemsFound.getNoItems());
    }

    @Test
    void testCopyConstructor() {
        ArrayList<Integer> findingDiffs = new ArrayList<>();
        Item item = new Item("Item0", 5, new ItemProperties());
        findingDiffs.add(1);
        ArrayList<Item> hiddenItems = new ArrayList<>();
        hiddenItems.add(item);
        Inventory roomInventory = new Inventory();
        roomInventory.addItem(item);
        Room room = new Room(false, false, true, false, roomInventory, hiddenItems, findingDiffs);

        Room room2 = new Room(room);

        Inventory foundItems = room2.findHiddenItems(5);
        Assertions.assertEquals(foundItems.getItem(item).getName(),item.getName());
        Assertions.assertEquals(foundItems.getItem(item).getQuantity(),item.getQuantity());
        Assertions.assertEquals(foundItems.getItem(item).getId(),item.getId());

        Item visibleItem = room2.enter().getItems().values().iterator().next();
        Assertions.assertEquals(item, visibleItem);
    }

    @Test
    void testEquals() {
        ArrayList<Integer> findingDiffs = new ArrayList<>();
        Item item = new Item("Item0", 5, new ItemProperties());
        findingDiffs.add(1);
        ArrayList<Item> items = new ArrayList<>();
        items.add(item);
        Inventory roomInventory = new Inventory();
        roomInventory.addItem(item);

        Room room = new Room(false, false, true, false, roomInventory, items, findingDiffs);

        Assertions.assertTrue(room.equals(room));
    }

    @Test
    void testIsEmpty() {
        Room closedEmptyRoom = new Room();
        Item item = new Item("Item0", 2, new ItemProperties());
        Room openEmptyRooom = new Room(true, false, false, false);
        ArrayList<Item> items = new ArrayList<>();
        items.add(item);
        Inventory roomInventory = new Inventory();
        roomInventory.addItem(item);
        Room closedRoomWithItems = new Room (false, false, false, false, roomInventory);

        Assertions.assertTrue(closedEmptyRoom.isEmpty());
        Assertions.assertTrue(openEmptyRooom.isEmpty());
        Assertions.assertFalse(closedRoomWithItems.isEmpty());
    }

    @Test
    void testIsClosed() {
        Room closedEmptyRoom = new Room();
        Room openEmptyRooom = new Room(true, false, false, false);
        Item item = new Item("Item0", 2, new ItemProperties());
        Inventory roomInventory = new Inventory();
        roomInventory.addItem(item);
        Room closedRoomWithItems = new Room (false, false, false, false, roomInventory);

        Assertions.assertTrue(closedEmptyRoom.isClosed());
        Assertions.assertFalse(openEmptyRooom.isClosed());
        Assertions.assertTrue(closedRoomWithItems.isClosed());
    }
}