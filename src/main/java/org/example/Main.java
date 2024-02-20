package org.example;

import org.game.Items.ItemLoader;
import org.game.Items.Item;

import java.util.ArrayList;

import static org.game.StringUtils.toTitleCase;

public class Main {
        public static void main(String... args) {
            ArrayList<Integer> integers = new ArrayList<Integer>(9);
            ArrayList<Integer> removals = new ArrayList<Integer>();

            for (int i = 0; i < 9; i++){
                integers.add(i);
            }


            for (int i = 0; i < 9; i++){
                if (i % 2 == 0){
                    removals.add(i);
                }
                System.out.println(i);
            }

            System.out.println();
            integers.removeAll(removals);

            for (Integer i: integers){
                System.out.println(i);
            }
    }
}