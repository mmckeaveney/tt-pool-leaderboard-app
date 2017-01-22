package com.ttpool.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class Challenge {
    private final Sport sport;
    private final Player challenger;
    private final Long challengeTimestamp;

    public Challenge(Sport sport, Player challenger) {
        this.sport = sport;
        this.challenger = challenger;
        this.challengeTimestamp = Instant.now().toEpochMilli();
    }
}
