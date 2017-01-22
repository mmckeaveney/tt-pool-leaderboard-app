package com.ttpool.dto;

import lombok.Data;

import static com.google.common.base.Preconditions.checkNotNull;

@Data
public class PlayerMetrics extends BaseDto {
    private final String playerId;
    private final Sport sport;
    private Integer currentRanking;
    private Integer highestRanking;
    private Integer currentWinStreak;
    private Integer highestWinStreak;
    private Match biggestVictory;
    private Match biggestDefeat;

    public PlayerMetrics(String playerId, Sport sport, Integer currentRanking, Integer highestRanking, Integer currentWinStreak,
                         Integer highestWinStreak) {
        this.playerId = checkNotNull(playerId, "PlayerId cannot be null");
        this.sport = checkNotNull(sport, "Sport cannot be null");
        this.currentRanking = currentRanking;
        this.highestRanking = highestRanking;
        this.currentWinStreak = currentWinStreak;
        this.highestWinStreak = highestWinStreak;
    }

    /**
     * Static factory method for new players.
     * @param playerId Id of the player to create metrics for.
     * @param sport The sport the metrics are being created for.
     * @param currentRanking The ranking the player will be going in at. This will be last place.
     * @return The newly created player metrics.
     */
    public static PlayerMetrics defaultPlayerMetrics(String playerId, Sport sport, Integer currentRanking) {
        checkNotNull(playerId, "PlayerId cannot be null");
        checkNotNull(sport, "Sport cannot be null");
        return new PlayerMetrics(playerId, sport, currentRanking, 0, 0, 0);
    }
}
