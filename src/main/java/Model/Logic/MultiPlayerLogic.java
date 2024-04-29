package Model.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

public class MultiPlayerLogic {
    private int players;

    private int currentplayer;

    HashMap<String, Float> playerTimes;

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



    public List<String> getSortedPlayerTimes() {
        // Create a sorted list of entries based on times
        return playerTimes.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.toList());
    }

}
