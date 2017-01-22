package com.ttpool.utils;

import com.ttpool.dto.Match;
import com.ttpool.dto.Player;
import com.ttpool.dto.PlayerMetrics;
import com.ttpool.dto.Sport;

import java.util.Random;

public class TestDataUtils {

    public static Match createDummyMatch(Sport sport) {
        return new Match(sport, createDummyPlayer(), createAnotherDummyPlayer(), 21, 5);
    }

    public static Player createDummyPlayer() {
       return new Player("Joe", "Bloggs");
    }

    public static Player createAnotherDummyPlayer() {
        return new Player("Sam", "Smith");
    }

    public static PlayerMetrics createDummyPlayerMetrics(String playerId, Sport sport) {
        return new PlayerMetrics(playerId, sport, generateRandomNumber(1, 20), generateRandomNumber(1, 20),
                generateRandomNumber(1, 20), generateRandomNumber(1, 20));
    }

    private static Integer generateRandomNumber(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
