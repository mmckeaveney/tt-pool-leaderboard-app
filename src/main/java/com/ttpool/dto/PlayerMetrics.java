package com.ttpool.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import static com.google.common.base.Preconditions.checkNotNull;

@Data
public class PlayerMetrics {

    @Id
    private String id;
    private final String playerId;
    private final Sport sport;
    private Integer currentRanking;
    private Integer highestRanking;
    private Integer currentWinStreak;
    private Integer highestWinStreak;
    private Match biggestVictory;
    private Match biggestDefeat;

    @JsonCreator
    public PlayerMetrics(@JsonProperty("playerId") String playerId,
                         @JsonProperty("sport") Sport sport,
                         @JsonProperty("currentRanking") Integer currentRanking,
                         @JsonProperty("highestRanking") Integer highestRanking,
                         @JsonProperty("currentWinStreak") Integer currentWinStreak,
                         @JsonProperty("highestWinStreak") Integer highestWinStreak) {
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
