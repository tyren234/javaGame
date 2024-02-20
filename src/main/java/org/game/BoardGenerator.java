package org.game;

import org.game.ItemUsages.SphereOfTeleportationItemUsage;
import org.game.Items.Item;
import org.game.Items.ItemLoader;
import org.game.NPC.Goblin;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static org.game.Items.RandomItemsListGenerator.getRandomItems;

public final class BoardGenerator {
    private static Random generator = new Random();

    public static Board createRandomBoard(final int rows, final int columns) {
        Item[] garbageItems = ItemLoader.loadItems("garbageItems.json");
        Item[] normalItems = ItemLoader.loadItems("items.json");
        Item[] specialItems = ItemLoader.loadItems("specialItems.json");
        Board board = new Board(rows, columns);

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int itemsNo = generator.nextInt(2);

                Inventory roomInventory = getRandomItems(5, garbageItems);
                roomInventory = roomInventory.join(getRandomItems(1, specialItems));

                ArrayList<Item> hiddenItems = new ArrayList<>();
                ArrayList<Integer> findingDifficulties = new ArrayList<>();
                int maxFindingDifficulty = 5;
                int minFindingDifficulty = 1;
                int maxHiddenItemsNo = 2;
                int hiddenItemsQuantity = 1;
                int hiddenItemsNo = generator.nextInt(maxHiddenItemsNo);
                for (int i = 0; i < hiddenItemsNo; i++) {
                    hiddenItems.add(normalItems[generator.nextInt(normalItems.length)]);
                    hiddenItems.get(i).setQuantity(hiddenItemsQuantity);
                    findingDifficulties.add(generator.nextInt(maxFindingDifficulty - minFindingDifficulty) + minFindingDifficulty);
                }


                Room room = new Room((row != 0), (column != columns - 1), (row != rows - 1), (column != 0), roomInventory, hiddenItems, findingDifficulties);
                board.setRoom(row, column, room);

            }
        }

        String sphereOfTeleportationId = "2h098ser-ba03-0692-4693-jh30487hq3gq";
        board.addItemUsage(sphereOfTeleportationId, new SphereOfTeleportationItemUsage());

        // Add goblin to some rooms
        for (int row = 1; row < rows; row+=2) {
            for (int column = 1; column < columns; column+=2) {
                Room room = board.getRoom(row, column);
                room.addNpc(new Goblin(1, 3));
            }
        }
        board.setPlayerPosition(0, 0);
        return board;
    }

    public static Board firstLevel(){
        String finalRoomDescription = new StringBuilder()
                .append("The room is chilly and an aery atmosphere fills the air. In the very center of the room you can see a short column, about 3f high.\n")
                .append("You spot that the column has a very round hole in a shape of a half-sphere\n")
                .append("This room is also dryer, compared to other rooms. Someone made sure it is better prepared for moisture than the other ones.").toString();

        Item[] garbageItems = ItemLoader.loadItems("garbageItems.json");
        Item[] normalItems = ItemLoader.loadItems("items.json");
        Item[] specialItems = ItemLoader.loadItems("specialItems.json");


        String sphereOfTeleportationId = "2h098ser-ba03-0692-4693-jh30487hq3gq";
        String swordId = "3fdd507a-bad7-4189-8352-db2d1358b7b9";
        Item sphereOfTeleportation = null;
        Item sword = null;
        for (Item item: specialItems){
            if (Objects.equals(item.getId(), sphereOfTeleportationId)){
                sphereOfTeleportation = item;
                sphereOfTeleportation.setQuantity(1);
                continue;
            }
        }

        for (Item item: normalItems){
            if (Objects.equals(item.getId(), swordId)){
                sword = item;
                sword.setQuantity(1);
                continue;
            }
        }
        if (sphereOfTeleportation == null) throw new RuntimeException();
        if (sword == null) throw new RuntimeException();


        final int rows = 5;
        final int columns = 5;

        Board board = new Board(rows, columns);

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int itemsNo = generator.nextInt(2);

                Inventory roomInventory = (getRandomItems(itemsNo, garbageItems));

                ArrayList<Item> hiddenItems = new ArrayList<>();
                ArrayList<Integer> findingDifficulties = new ArrayList<>();
                int maxFindingDifficulty = 15;
                int minFindingDifficulty = 1;
                int maxHiddenItemsNo = 1;
                int hiddenItemsQuantity = 1;
                int hiddenItemsNo = generator.nextInt(maxHiddenItemsNo);
                for (int i = 0; i < hiddenItemsNo; i++) {
                    hiddenItems.add(normalItems[generator.nextInt(normalItems.length)]);
                    hiddenItems.get(i).setQuantity(hiddenItemsQuantity);
                    findingDifficulties.add(generator.nextInt(maxFindingDifficulty - minFindingDifficulty) + minFindingDifficulty);
                }
                if (row == 0 && column == 0){
                    hiddenItems.clear();
                    findingDifficulties.clear();
                    hiddenItems.add(sword);
                    findingDifficulties.add(1);
                }


                Room room = new Room((row != 0), (column != columns - 1), (row != rows - 1), (column != 0), roomInventory, hiddenItems, findingDifficulties);
                board.setRoom(row, column, room);

            }
        }



        board.addItemUsage(sphereOfTeleportationId, new SphereOfTeleportationItemUsage());
        board.getRoom(rows-1, columns-1).setDescription(finalRoomDescription);
        Inventory sphreInventory = new Inventory();
        sphreInventory.addItem(sphereOfTeleportation);
        board.getRoom(1,0).setVisibleItems(sphreInventory);
        board.getRoom(rows-1, columns-1).setVisibleItems(new Inventory());
        int[][] goblinsCoordinates = new int[4][2];
        goblinsCoordinates[0] = new int[]{2,2};
        goblinsCoordinates[1] = new int[]{2,3};
        goblinsCoordinates[2] = new int[]{3,3};
        goblinsCoordinates[3] = new int[]{3,0};
        // Add goblin to some rooms
        for (int i = 0; i < goblinsCoordinates.length; i++) {
                Room room = board.getRoom(goblinsCoordinates[i][0], goblinsCoordinates[i][1]);
                room.addNpc(new Goblin(2 + i, 3 + i));
        }
        board.setPlayerPosition(0, 0);
        return board;
    }
}
