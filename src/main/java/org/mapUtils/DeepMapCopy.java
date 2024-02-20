package org.mapUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.game.Items.Item;

import java.util.HashMap;
import java.util.Map;

public class DeepMapCopy {
    public static Map<String, Item> clone(Map<String, Item> map) {
        String json = new Gson().toJson(map);
        Map<String, Item> mapCopy = new Gson().fromJson(
                json, new TypeToken<Map<String, Item>>() {}.getType());
        return mapCopy;
    }
}
