package org.game;

import java.util.ArrayList;

public final class Visualiser {
    private static String wallString = "■";
    private static String emptyString = " ";
    private static String openDoorString = "o";
    private static String closedDoorString = "x";
    private static String playerString = "o";

    private static String[] getRoomVisualisation(final Room room, boolean drawPlayer) {
        // Should be odd.
        int roomWidth = 7;

        int halfRoomSize = roomWidth / 2;
        int emptySize = (roomWidth - 2) / 2;

        if (!room.isVisited()) {
            return new String[]{emptyString.repeat(roomWidth),
                    emptyString.repeat(roomWidth),
                    emptyString.repeat(roomWidth)};
        }
        String Nwall, Ewall, Swall, Wwall;
        Nwall = room.isN() ? closedDoorString : wallString;
        Ewall = room.isE() ? closedDoorString : wallString;
        Swall = room.isS() ? closedDoorString : wallString;
        Wwall = room.isW() ? closedDoorString : wallString;


        String playerPlace = drawPlayer ? playerString : emptyString;
        return new String[]{wallString.repeat(halfRoomSize) + Nwall + wallString.repeat(halfRoomSize),
                Wwall + emptyString.repeat(emptySize) + playerPlace + emptyString.repeat(emptySize) + Ewall,
                wallString.repeat(halfRoomSize) + Swall + wallString.repeat(halfRoomSize)};
    }

    public static void visualise(final Board board) {
        for (int row = 0; row < board.getRows(); row++) {
            String[] printRows = new String[]{"", "", ""};
            for (int column = 0; column < board.getCols(); column++) {
                boolean isPlayerHere = (board.getPlayerPosition()[0] == column && board.getPlayerPosition()[1] == row);
                String[] roomVisualisation = getRoomVisualisation(board.getRoom(row, column), isPlayerHere);
                for (int i = 0; i < printRows.length; i++) {
                    printRows[i] += roomVisualisation[i];
                }
            }
            for (String printRow : printRows) {
                System.out.println(printRow);
            }
        }
    }
}
