package com.ttpool.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@Data
public class Player {

    private String id;
    private String firstName;
    private String lastName;
    private List<Challenge> currentChallengesBySport;

    @JsonCreator
    public Player(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
        this.id = UUID.randomUUID().toString();
        this.firstName = checkNotNull(firstName, "firstName should not be null");
        this.lastName = lastName;
        this.currentChallengesBySport = new ArrayList<>();
    }

    public void recordChallenge(Challenge challenge) {
        this.currentChallengesBySport.add(challenge);
    }
}
