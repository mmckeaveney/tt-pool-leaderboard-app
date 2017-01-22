package com.ttpool.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Data
public class Player extends BaseDto {
    private final String firstName;
    private final String lastName;
    private List<Challenge> currentChallengesBySport;

    public Player(String firstName, String lastName) {
        this.firstName = checkNotNull(firstName, "firstName should not be null");
        this.lastName = lastName;
        this.currentChallengesBySport = new ArrayList<>();
    }

    public void recordChallenge(Challenge challenge) {
        this.currentChallengesBySport.add(challenge);
    }
}
