package com.ttpool.dto;

import lombok.Data;

@Data
public class Match extends BaseDto {
    private final Sport sport;
    private final Player winner;
    private final Player loser;
    private final Integer winnerScore;
    private final Integer loserScore;

    public Match(Sport sport, Player winner, Player loser, Integer winnerScore, Integer loserScore) {
        this.sport = sport;
        this.winner = winner;
        this.loser = loser;
        this.winnerScore = winnerScore;
        this.loserScore = loserScore;
    }
}
