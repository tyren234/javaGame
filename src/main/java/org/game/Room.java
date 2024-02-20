package org.game;

import org.game.Items.Item;
import org.game.NPC.Goblin;
import org.game.NPC.NPC;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Objects;

public class Room {
    private boolean N = false;
    private boolean E = false;
    private boolean S = false;
    private boolean W = false;
    private boolean visited = false;
    private ArrayList<Item> hiddenItems;
    private ArrayList<Integer> findingDifficulties;
    private Inventory visibleItems;
    private ArrayList<NPC> npcs = new ArrayList<NPC>();
    private String description = "Just a room. Four walls, looks like it's in a dungeon.";
    ;

    public Room(boolean north, boolean east, boolean south, boolean west, Inventory visibleItems,
                ArrayList<Item> hiddenItems, ArrayList<Integer> findingDifficulties) {
        this.N = north;
        this.E = east;
        this.S = south;
        this.W = west;
        this.visibleItems = visibleItems;
        if (hiddenItems.size() == findingDifficulties.size()) {
            this.hiddenItems = new ArrayList<>(hiddenItems);
            this.findingDifficulties = new ArrayList<>(findingDifficulties);
        }

    }

    public Room(boolean north, boolean east, boolean south, boolean west,
                ArrayList<Item> hiddenItems, ArrayList<Integer> findingDifficulties) {
        this(north, east, south, west, new Inventory(), hiddenItems, findingDifficulties);
    }

    public Room(boolean north, boolean east, boolean south, boolean west) {
        this(north, east, south, west, new Inventory(), new ArrayList<>(), new ArrayList<>());
    }

    public Room(boolean north, boolean east, boolean south, boolean west, Inventory visibleItems) {
        this(north, east, south, west, visibleItems, new ArrayList<>(), new ArrayList<>());
    }

    public Room(Room room) {
        this.N = room.N;
        this.E = room.E;
        this.S = room.S;
        this.W = room.W;
        this.visibleItems = room.visibleItems.clone();
        this.findingDifficulties = new ArrayList<>(room.findingDifficulties);
        this.hiddenItems = new ArrayList<>(room.hiddenItems);
    }

    public Room() {
        this(false, false, false, false, new Inventory(), new ArrayList<>(), new ArrayList<>());
    }

    public boolean isN() {
        return N;
    }

    public boolean isE() {
        return E;
    }

    public boolean isS() {
        return S;
    }

    public boolean isW() {
        return W;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Room room = (Room) o;
        return Objects.equals(N, room.N) && Objects.equals(S, room.S) &&
                Objects.equals(E, room.E) && Objects.equals(W, room.W) &&
                Objects.equals(hiddenItems, room.hiddenItems) && Objects.equals(visibleItems, room.visibleItems) &&
                Objects.equals(findingDifficulties, room.findingDifficulties);
    }

    public Inventory findHiddenItems(int finingAbility) {
        Inventory foundItems = new Inventory();
        for (int i = findingDifficulties.size() - 1; i >= 0; i--) {
            if (finingAbility >= findingDifficulties.get(i)) {
                foundItems.addItem(hiddenItems.get(i));
                hiddenItems.remove(i);
                findingDifficulties.remove(i);
            }
        }

        return foundItems;
    }

    public Inventory enter() {
        Inventory out = new Inventory(visibleItems);
        visibleItems.clear();
        setVisited(true);
        return out;
    }

    public void setVisibleItems(Inventory newVisibleItems) {
        visibleItems = new Inventory(newVisibleItems);
    }

    public boolean isEmpty() {
        return hiddenItems.isEmpty() && findingDifficulties.isEmpty() && visibleItems.isEmpty();
    }

    public boolean isClosed() {
        return !(N || E || S || W);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean newVisited) {
        visited = newVisited;
    }

    public String getDescription() {
        StringBuilder output = new StringBuilder(description);
        for (NPC npc : npcs) {
            if (Objects.equals(npc.getDescription(), "")) continue;
            output.append("\n").append(npc.getDescription());
        }
        return output.toString();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void actNPSs(Board board) {
        int[] position = board.getPlayerPosition();
        ArrayList<NPC> removals = new ArrayList<>();
        for (NPC npc : npcs) {
            if (!npc.getAlive()) {
                removals.add(npc);
                Messenger.getInstance().addMessage(npc.getDeathMessage());
            } else {
                npc.act(board, position[0], position[1]);
            }
        }
        npcs.removeAll(removals);
    }

    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    public boolean containsNPC(NPC npc) {
        return npcs.contains(npc);
    }

    public void addNpc(NPC npc) {
        npcs.add(npc);
    }
}
