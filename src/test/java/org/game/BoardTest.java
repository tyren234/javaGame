package org.game;

import org.game.Items.Item;
import org.game.Items.ItemProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardTest {
    @Test
    void testGetDimensions() {
        Board board = new Board(1, 2);

        Assertions.assertEquals(1, board.getDimensions()[0]);
        Assertions.assertEquals(2, board.getDimensions()[1]);
    }

    @Test
    void testGetRows() {
        Board board = new Board(1, 2);

        Assertions.assertEquals(1, board.getRows());
    }

    @Test
    void testGetCols() {
        Board board = new Board(1, 2);

        Assertions.assertEquals(2, board.getCols());
    }

    @Test
    void testCreatingBoardOfNegativeOrZeroDimensionsThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Board(-1, -2);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Board(0, 0);
        });
    }

    @Test
    void testSetRoomGetRoom() {
        Board board = new Board(2, 2);
        Item item = new Item("Item0", 2, new ItemProperties());
        Inventory itemList = new Inventory();

        itemList.addItem(item);
        Room room = new Room(false, false, false, false, itemList);

        board.setRoom(0,0, room);
        Room gotRoom = board.getRoom(0,0);

        Assertions.assertEquals(room, gotRoom);
    }

    @Test
    void testBoardCreatesEmptyRooms() {
        Board board = new Board(10, 10);

        for (int row = 0; row < board.getRows(); row++)
            for (int col = 0; col < board.getCols(); col++) {
                Assertions.assertFalse(board.getRoom(row, col).isN());
                Assertions.assertFalse(board.getRoom(row, col).isS());
                Assertions.assertFalse(board.getRoom(row, col).isE());
                Assertions.assertFalse(board.getRoom(row, col).isW());
            }
    }

    @Test
    void testEquals() {
        Board board = new Board(2, 2);
        Board board1 = new Board(2, 2);
        Item item = new Item("Item0", 2, new ItemProperties());
        Inventory itemList = new Inventory();
        itemList.addItem(item);

        Room room = new Room(false, false, false, false, itemList);
        Room room1 = new Room(false, false, false, false, itemList);

        board.setRoom(0,0, room);
        board.setRoom(1,1, room);
        board1.setRoom(0,0, room1);
        board1.setRoom(1,1, room1);

        Assertions.assertTrue(board.equals(board1));
    }
}