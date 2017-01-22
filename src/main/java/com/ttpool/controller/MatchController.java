package com.ttpool.controller;

import com.ttpool.dto.Match;
import com.ttpool.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/match")
public class MatchController {
    private MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    /**
     * Record a match between 2 players.
     * @param match the match object.
     * @return the newly created match object.
     */
    @PostMapping
    public Match recordMatch(@RequestBody Match match) {
        return matchService.recordMatch(match);
    }
}
