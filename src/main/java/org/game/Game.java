package org.game;

import org.game.Items.Item;
import org.game.NPC.NPC;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.game.StringUtils.toTitleCase;
import static org.game.Visualiser.visualise;

//Todo: multi word commands in one line i.e.: equip weapon sword
//Todo: improve finability?

public final class Game {
    public static void main(String... args) {
        Board board = BoardGenerator.firstLevel();
        Hero hero = new Hero("Artur Dent", 6, 10);
        hero.getInventory().join(board.getRoom(0, 0).enter());
        board.setPlayer(hero);
        Messenger messenger = Messenger.getInstance();

        messenger.addMessage("Let the game, begin.");
        messenger.addMessage("Type 'help' or 'h' for help. Type 'exit' to exit.");
        messenger.print();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        ArrayList<String> exitSequences = new ArrayList<>(Arrays.asList("exit", "Exit"));


        visualise(board);
        while (!exitSequences.contains(input)) {
            messenger.print();
            input = scanner.nextLine().trim();

            Direction direction = null;
            try {
                direction = Direction.valueOf(input);
            } catch (IllegalArgumentException ignored) {
            }

            input = input.toLowerCase();

            if (input.equals("help") || input.equals("h")) {
                messenger.addMessage("==========General==========");
                messenger.addMessage("Type 'help' or 'h' to show this menu");
                messenger.addMessage("Type 'exit' to exit");
                messenger.addMessage("Type 'N', 'E', 'S' or 'W' to move");
                messenger.addMessage("Type 'i' or 'inventory' to see your inventory");
                messenger.addMessage("Type 'eq' or 'equipment' to see your equipment");
                messenger.addMessage("Type 'hero' to see info about your hero");
                messenger.addMessage("==========Items==========");
                messenger.addMessage("Type 'equip' to equip an item");
                messenger.addMessage("Type 'describe' to describe an item");
                messenger.addMessage("Type 'use' to use an item");
                messenger.addMessage("Type 'eat' to eat an item");
                messenger.addMessage("Type 'hit' to hit an NPC");
                messenger.addMessage("==========Rooms==========");
                messenger.addMessage("Type 'search' to search the room you are in");
                messenger.addMessage("Type 'visualise' to visualise the board");

            } else if (input.equals("i") || input.equals("inventory")) {
                if (hero.getInventory().isEmpty()) {
                    messenger.addMessage("Your inventory is empty.");
                } else {
                    messenger.addMessage("Your inventory:");
                    messenger.addMessage(hero.getInventory().getPrintString());
                }
            } else if (input.equals("equip") && !board.getGameOver()) {
                messenger.addMessage("What item do you want to equip?");
                messenger.print();
                input = scanner.nextLine();
                try {
                    Item item = hero.getInventory().getItemByName(input);
                    messenger.addMessage("Use it as an 'armour' or as a 'weapon'?");
                    messenger.print();
                    input = scanner.nextLine().toLowerCase();
                    if (input.equals("armour")) {
                        hero.equip(item.getId(), Equipment.Armour);
                        messenger.addMessage("Equipped as an armour.");
                    } else if (input.equals("weapon")) {
                        hero.equip(item.getId(), Equipment.Weapon);
                        messenger.addMessage("Equipped as a weapon.");
                    } else {
                        messenger.addMessage("Type either 'armour' or 'weapon'.");
                    }
                } catch (Exception ignored) {
                    messenger.addMessage("You haven't got that. You have: " + hero.getInventory().getItemsNames());
                }
            } else if (input.equals("eat") && !board.getGameOver()) {
                messenger.addMessage("What do you want to eat?");
                messenger.print();
                input = scanner.nextLine();
                try {
                    Item item = hero.getInventory().getItemByName(input);
                    if (item.getProperties().edible) {
                        messenger.addMessage("You ate " + item.getName() + " and restored " + item.getProperties().healthRestored + " health.");
                        board.getPlayer().addHealth(item.getProperties().healthRestored);
                    } else {
                        messenger.addMessage(item.getName() + " is not edible.");
                    }
                } catch (Exception ignored) {
                    messenger.addMessage("You haven't got that. You have: " + hero.getInventory().getItemsNames());
                }
            } else if (input.equals("use") && !board.getGameOver()) {
                messenger.addMessage("What item do you want to use?");
                messenger.print();
                input = scanner.nextLine();
                try {
                    Item item = hero.getInventory().getItemByName(input);
                    board.useItem(item.getId());
                } catch (KeyException keyException) {
                    messenger.addMessage(toTitleCase(input) + " doesn't do anything.");
                } catch (Exception ignored) {
                    messenger.addMessage("You haven't got that. You have: " + hero.getInventory().getItemsNames());
                }
            } else if (input.equals("equipment") || input.equals("eq")) {
                messenger.addMessage(hero.getEquipmentPrintString());

            } else if (input.equals("search") && !board.getGameOver()) {
                Inventory searchResults = board.search(hero.getFindingAbility());
                if (searchResults.getItemsSize() == 0) {
                    messenger.addMessage("You couldn't find anything.");
                } else {
                    messenger.addMessage("You found: " + searchResults.getPrintString());
                    hero.addItems(searchResults);
                }
            } else if (input.equals("describe")) {
                messenger.addMessage("What item do you want to describe?");
                messenger.print();
                input = scanner.nextLine();
                try {
                    messenger.addMessage(hero.getInventory().getItemByName(input).description());
                } catch (Exception e) {
                    messenger.addMessage("You haven't got that.");
                }
            } else if (input.equals("hero")) {
                messenger.addMessage(hero.info());
            } else if (input.equals("visualise")) {
                visualise((board));
            } else if (input.equals("hit") && !board.getGameOver()) {
                try {
                    messenger.addMessage("What do you want to hit? (type number)");
                    ArrayList<NPC> npcs = board.getCurrentRoom().getNpcs();
                    for (int i = 0; i < npcs.size(); i++) {
                        messenger.addMessage(i + ". " + npcs.get(i).getName() + " " + (npcs.get(i).showHealthOnEnemyList() ? npcs.get(i).getHealth() : ""));
                    }
                    messenger.print();
                    int npcNo = scanner.nextInt();
                    npcs.get(npcNo).addHealth(-1 * hero.getAttack());
                    messenger.addMessage("You dealt " + hero.getAttack() + " points of damage!");
                } catch (Exception e) {
                    messenger.addMessage("No such number on the list.");
                }
            } else if (direction != null && !board.getGameOver()) {
                Inventory foundItems = board.move(direction);
                if (!foundItems.isEmpty()) {
                    messenger.addMessage("You found: ");
                    messenger.addMessage(foundItems.getPrintString());
                }
                messenger.addMessage(board.describeCurrentRoom());
                hero.addItems(foundItems);
            } else {
                messenger.addMessage("Wrong command.");
            }

            for (int row = 0; row < board.getRows(); row++) {
                for (int column = 0; column < board.getCols(); column++) {
                    board.getRoom(row, column).actNPSs(board);
                }
            }

            if (board.isGameLost())
                messenger.addMessage("You lost. This is game over. You can still access some commands.");
            else if (!hero.isAlive()) {
                messenger.addMessage("Unfortunately you died. This is game over. You can still access some commands.");
                board.setGameLost(true);
            } else if (board.isGameWon()) {
                messenger.addMessage("You win!.\nCongratulations!\nAlthough This is game over, you can still access some commands.");
            }

            messenger.addMessage("What to do next?");
        }

        scanner.close();
    }
}
