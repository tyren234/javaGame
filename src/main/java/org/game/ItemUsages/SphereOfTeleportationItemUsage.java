package org.game.ItemUsages;

import org.game.Board;
import org.game.Messenger;

import java.util.Objects;

public class SphereOfTeleportationItemUsage implements ItemUsage{
    @Override
    public void useItem(Board board) {
        Messenger messenger = Messenger.getInstance();

        String finalRoomDescription = new StringBuilder()
                .append("The room is chilly and an aery atmosphere fills the air. In the very center of the room you can see a short column, about 3f high.\n")
                .append("You spot that the column has a very round hole in a shape of a half-sphere\n")
                .append("This room is also dryer, compared to other rooms. Someone made sure it is better prepared for moisture than the other ones.").toString();
        if (Objects.equals(board.getCurrentRoom().getDescription(), finalRoomDescription)){
            board.setGameWon(true);
            return;
        }

        messenger.addMessage("You have used THE GREAT SPHERE OF TELEPORTATION and thereby you are teleported to the start of the dungeon. I hope you remember where you were.");
        for (int row = 0; row < board.getRows(); row++)
            for (int col = 0; col < board.getCols(); col++)
                board.getRoom(row, col).setVisited(false);
        board.setPlayerPosition(0,0);
        board.getRoom(0,0 ).setVisited(true);
    }


}
