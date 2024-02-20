package org.game;

public class StringUtils {
    public static String toTitleCase(final String text) {
        String[] words = text.split(" ");
        StringBuilder output = new StringBuilder();
        for (String word : words) {
            if (word.length() == 1) output.append(word.toLowerCase()).append(" ");
            else output.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
        }
        return output.toString().trim();
    }
}
