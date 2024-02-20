package org.game;

import org.game.Items.Item;
import org.mapUtils.DeepMapCopy;

import java.util.*;
import java.util.stream.Collectors;

public class Inventory {
    private Map<String, Item> items;
    private int noItems;

    public Inventory() {
        items = new HashMap<>();
        noItems = 0;
    }

    private Inventory(final Map<String, Item> items, final int noItems) {
        this.items = items;
        this.noItems = noItems;
    }

    public Inventory(Inventory inventory){
        this(DeepMapCopy.clone(inventory.getItems()), inventory.getNoItems());
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public int getItemsSize() {
        return items.size();
    }

    public int getNoItems() {
        return noItems;
    }

    public List<String> getItemsNames() {
        List<String> itemsNames = items.values().stream().map(Item::getName).collect(Collectors.toList());
        return itemsNames;
    }

    public boolean addItem(Item item) {
        if (item.getQuantity() <= 0) {
            return false;
        }

        String itemId = item.getId();
        if (items.containsKey(itemId)) {
            items.get(itemId).setQuantity(items.get(itemId).getQuantity() + item.getQuantity());
        } else {
            items.put(item.getId(), item);
        }

        noItems += item.getQuantity();

        return true;
    }

    public boolean addItem(Item item, int quantity) {
        item.setQuantity(quantity);
        return addItem(item);
    }

    public Item getItem(final String id) {
        return items.get(id);
    }

    public Item getItem(final Item item) {
        return items.get(item.getId());
    }

    public boolean useItem(String id, int quantity) {
        if (items.get(id).getQuantity() < quantity
                || quantity <= 0)
            return false;

        items.get(id).setQuantity(items.get(id).getQuantity() - quantity);
        noItems -= quantity;
        return true;
    }

    public Inventory join(Inventory foundInventory) {
        Inventory newInventory = foundInventory.clone();
        for (Item item : items.values()) {
            newInventory.addItem(item);
        }
        return newInventory;
    }

    public Inventory clone() {
        return new Inventory(items, noItems);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Inventory inventory = (Inventory) o;
        return Objects.equals(items, inventory.items)
                && Objects.equals(noItems, inventory.noItems);
    }

    public void clear() {
        items.clear();
        noItems = 0;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public String getPrintString(){
        StringBuilder output = new StringBuilder();
        for (Item item : items.values()){
            if (item.getQuantity() == 0) continue;
            output.append(item.getQuantity()).append(" ").append(item.getName()).append("\n");
        }
        return output.toString();
    }

    //Not recommended. Use getItem instead.
    public Item getItemByName(String name){
        for (Item item: items.values()){
            if (item.getTrueName().equals(name.toLowerCase())){
                return item;
            }
        }
        throw new NoSuchElementException("Inventory doesn't contain item with that name.");
    }
}
