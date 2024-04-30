package Model.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;
/**
 * MultiPlayerLogic class to represent the logic of the multiplayer game.
 */
public class MultiPlayerLogic {
    final private int players; // The number of players in the game

    private int currentplayer; // The current player in the game

    HashMap<String, Float> playerTimes; // A map of player names to their times

    /**
     * Constructs a new MultiPlayerLogic instance with the specified number of players.
     *
     * @param players the number of players in the game
     */
    public MultiPlayerLogic(int players) {
        this.players = players;
        playerTimes = new HashMap<>();
        currentplayer = 1;
    }

    public void addPlayerTime(float time) {
        playerTimes.put("Player "+ currentplayer,time);
        currentplayer++;
    }

    public String getCurrentPlayer() {
        return "Player " + currentplayer;
    }

    public boolean playerEqualPlayers() {
        return currentplayer == players;
    }


    // Get a list of player names sorted by time.
    public List<String> getSortedPlayerTimes() {
        // Create a sorted list of entries based on times
        return playerTimes.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.toList());
    }

}
