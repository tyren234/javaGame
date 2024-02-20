package org.game.NPC;

import org.game.Board;

public interface NPC
{
    String name = null;
    String description = null;

    boolean alive = true;
    public boolean showHealthOnEnemyList();
    String getDescription();
    void act(Board board, int playerX, int playerY);
    String getName();

    void addHealth(int value);

    int getHealth();

    boolean getAlive();
    public String getDeathMessage();
}
