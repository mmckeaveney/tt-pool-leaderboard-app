package com.ttpool.controller;

import com.ttpool.dto.Challenge;
import com.ttpool.dto.Player;
import com.ttpool.dto.PlayerMetrics;
import com.ttpool.dto.Sport;
import com.ttpool.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/player")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Find a player by their ID.
     * @param playerId the playerId to search for.
     * @return the player found or null if none found.
     */
    @GetMapping("/{playerId}")
    public Player findPlayerById(@PathVariable String playerId) {
        return playerService.findPlayerById(playerId);
    }

    /**
     * Create a new player.
     * @param player the player object to create.
     * @return the newly created player.
     */
    @PostMapping("/create")
    public Player createNewPlayer(@RequestBody Player player) {
        return playerService.createNewPlayer(player);
    }

    /**
     * Delete a player.
     * @param playerId the playerId of the player to delete.
     */
    @DeleteMapping("/delete/{playerId}")
    public void deletePlayer(@PathVariable String playerId) {
        playerService.deletePlayer(playerId);
    }

    /**
     * Update a players details.
     * @param playerId the playerId of the player to update.
     * @param updatedPlayer the data to replace the current player data with.
     * @return the updated player entity.
     */
    @PutMapping("/update/{playerId}")
    public Player updatePlayerDetails(@PathVariable String playerId, @RequestBody Player updatedPlayer) {
        return playerService.updatePlayerDetails(playerId, updatedPlayer);
    }

    /**
     * Get Metrics for a certain player by sport.
     * @param playerId the playerID of the player to get metrics for.
     * @param sport the sport to get metrics for.
     * @return the playermetrics for that playerId and sport. Returns an Optional.empty if none exist.
     */
    @GetMapping("/metrics/{playerId}/{sport}")
    public Optional<PlayerMetrics> getMetricsForPlayerBySport(@PathVariable String playerId, @PathVariable Sport sport) {
        return getMetricsForPlayerBySport(playerId, sport);
    }

    /**
     * Get all the players by ranking for a sport.
     * @param sport the sport to get the players for.
     * @return All the players for a certain sport by ranking.
     */
    @GetMapping("/byranking/{sport}")
    public List<Player> getPlayersByRankingForSport(@PathVariable Sport sport) {
        return playerService.getPlayersByRankingForSport(sport);
    }

    /**
     * Challenge a player to a match.
     * @param playerId the playerId to challenge.
     * @param challenge the challenge that has been sent.
     */
    @PostMapping("/challenge/{playerId}")
    public void challengePlayer(@PathVariable String playerId, @RequestBody Challenge challenge) {
        playerService.challengePlayer(playerId, challenge);
    }


}
