package org.game.Items;

import org.game.ItemUsages.ItemUsage;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static org.game.StringUtils.toTitleCase;

public class Item implements Serializable {
    private String name;
    private int quantity;
    private String id;
    private ItemUsage itemUsage;
    private ItemProperties properties;

    public Item(String name, int quantity, ItemProperties properties, String id) {
        this.name = name.toLowerCase();
        this.properties = properties;
        this.id = id;
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }

    public Item(String name, int quantity, ItemProperties properties) {
        this(name, quantity, properties, UUID.randomUUID().toString());
    }

    public Item(Item item) {
        this(item.getName(), item.getQuantity(), item.getProperties(), item.getId());
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return toTitleCase(name);
    }

    public String getId() {
        return id;
    }

    public ItemProperties getProperties() {
        return properties;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProperties(final ItemProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name)
                && Objects.equals(quantity, item.quantity)
                && Objects.equals(id, item.id);
    }

    public String description() {
        if (properties.description == null) return "";
        return properties.description;
    }

    public String getTrueName() {
        return name;
    }
}
