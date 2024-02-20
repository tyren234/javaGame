package org.game;

import java.util.ArrayList;
import java.util.List;

public final class Messenger {
    private static Messenger instance;
    private static List<String> messages = new ArrayList<String>();

    private Messenger() {
    }

    public static Messenger getInstance(){
        if (instance == null) {
            instance = new Messenger();
        }
        return instance;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public void print() {
        for (String message : messages) {
            System.out.println(message);
        }
        messages.clear();
    }
}
