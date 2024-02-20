package org.game.NPC;

import org.game.Board;
import org.game.Messenger;

import static org.game.StringUtils.toTitleCase;

public class Goblin implements NPC {
    String name;
    String description = "Ugly goblin is lurking from the corner of the room. It can't even speak, but unfortunately it can hit you with it's club.";
    int attack;
    int health;
    static Messenger messenger = Messenger.getInstance();
    private boolean skipFirst;
    private boolean alive = true;

    public Goblin(int attack, int health) {
        this.attack = attack;
        this.name = "Goblin " + attack;
        this.health = health;
        skipFirst = true;
    }

    @Override
    public boolean showHealthOnEnemyList() {
        return true;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    // Goblin hits only player in the same room
    @Override
    public void act(Board board, int playerX, int playerY) {
        if (!alive
                || !board.getPlayer().isAlive()
                || !board.getRoom(playerY, playerX).containsNPC(this)) return;
        if (skipFirst) {
            skipFirst = false;
            return;
        }
        int damage = attack - board.getPlayer().getDefence();
        board.getPlayer().addHealth(-1 * Math.max(damage, 0));
        messenger.addMessage(toTitleCase(name) + " hits you with his club for " + attack + " points of damage.");
    }

    @Override
    public void addHealth(int value) {
        health += value;
        if (health <= 0) {
            alive = false;
        }
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public boolean getAlive() {
        return alive;
    }

    @Override
    public String getDeathMessage() {
        return "Goblin died.";
    }
}
