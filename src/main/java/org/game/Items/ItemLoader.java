package org.game.Items;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;

public final class ItemLoader {
    public static Item[] loadItems(final String itemsConfigFileName) {
        try {
            String filePath = ItemLoader.class.getClassLoader().getResource(itemsConfigFileName).getPath();
            JsonReader reader = new JsonReader(new FileReader(filePath));
            Gson gson = new Gson();
            Item[] items = gson.fromJson(reader, Item[].class);
            return items;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
