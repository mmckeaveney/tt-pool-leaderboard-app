package com.ttpool.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Match {

    @Id
    private String id;
    private final Sport sport;
    private final Player winner;
    private final Player loser;
    private final Integer winnerScore;
    private final Integer loserScore;

    @JsonCreator
    public Match(@JsonProperty("sport") Sport sport,
                 @JsonProperty("winner") Player winner,
                 @JsonProperty("loser") Player loser,
                 @JsonProperty("winnerScore") Integer winnerScore,
                 @JsonProperty("loserScore") Integer loserScore) {
        this.sport = sport;
        this.winner = winner;
        this.loser = loser;
        this.winnerScore = winnerScore;
        this.loserScore = loserScore;
    }
}
