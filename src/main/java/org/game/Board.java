package org.game;

import org.game.ItemUsages.ItemUsage;

import java.security.KeyException;
import java.util.HashMap;
import java.util.Objects;

public final class Board {
    private int rows, cols;
    private int playerX;
    private int playerY;
    private Room[][] rooms;
    private HashMap<String, ItemUsage> itemUsages;
    private Hero player;

    private boolean gameLost;
    private boolean gameWon;


    public Board(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Board dimensions not valid");
        }
        this.rows = rows;
        this.cols = cols;
        rooms = new Room[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                rooms[i][j] = new Room();
            }
        }
        this.itemUsages = new HashMap<>();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int[] getDimensions() {
        return new int[]{rows, cols};
    }

    public Room getRoom(int row, int col) {

        return rooms[row][col];
    }

    public void setRoom(int row, int col, Room room) {
        rooms[row][col] = new Room(room);
    }

    public int[] getPlayerPosition() {
        return new int[]{playerX, playerY};
    }

    public void setPlayerPosition(int x, int y) {
        if (0 <= x && x < cols) {
            this.playerX = x;
        }
        if (0 <= y && y < rows) {
            this.playerY = y;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Board board = (Board) o;

        if (!(Objects.equals(rows, board.rows) &&
                Objects.equals(cols, board.cols))) {
            return false;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!Objects.equals(rooms[i][j], board.rooms[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

    public Inventory move(Direction direction) {
        Room currentRoom = getRoom(playerY, playerX);
        Room newRoom = null;
        if (currentRoom.isClosed()) {
            return new Inventory();
        } else if (direction == Direction.N && currentRoom.isN()) {
            setPlayerPosition(playerX, playerY - 1);
            newRoom = getRoom(playerY, playerX);
        } else if (direction == Direction.S && currentRoom.isS()) {
            setPlayerPosition(playerX, playerY + 1);
            newRoom = getRoom(playerY, playerX);
        } else if (direction == Direction.E && currentRoom.isE()) {
            setPlayerPosition(playerX + 1, playerY);
            newRoom = getRoom(playerY, playerX);
        } else if (direction == Direction.W && currentRoom.isW()) {
            setPlayerPosition(playerX - 1, playerY);
            newRoom = getRoom(playerY, playerX);
        }

        if (newRoom != null && newRoom !=currentRoom) {
            return newRoom.enter();
        }
        return new Inventory();
    }

    public Inventory search(int finingAbility) {
        return getRoom(playerY, playerX).findHiddenItems(finingAbility);
    }

    public String describeCurrentRoom() {
        return getRoom(playerY, playerX).getDescription();
    }

    public void addItemUsage(String id, ItemUsage itemUsage) {
        itemUsages.put(id, itemUsage);
    }

    public void useItem(String id) throws KeyException {
        if (!itemUsages.containsKey(id)) throw new KeyException("No item usage with this id");
        itemUsages.get(id).useItem(this);
    }

    public Room getCurrentRoom() {
        return getRoom(playerY, playerX);
    }

    public Hero getPlayer() {
        return player;
    }

    public void setPlayer(Hero player) {
        this.player = player;
    }

    public boolean isGameLost() {
        return gameLost;
    }

    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public boolean getGameOver() {
        return gameWon || gameLost;
    }
}
