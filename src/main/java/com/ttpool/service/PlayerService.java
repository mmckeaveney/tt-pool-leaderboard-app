package com.ttpool.service;

import com.ttpool.dao.PlayerDao;
import com.ttpool.dao.PlayerMetricsDao;
import com.ttpool.dto.Challenge;
import com.ttpool.dto.Player;
import com.ttpool.dto.PlayerMetrics;
import com.ttpool.dto.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private PlayerDao playerDao;
    private PlayerMetricsDao playerMetricsDao;

    @Autowired
    protected PlayerService(PlayerDao playerDao, PlayerMetricsDao playerMetricsDao) {
        this.playerDao = playerDao;
        this.playerMetricsDao = playerMetricsDao;
    }

    public Player findPlayerById(String playerId) {
        return playerDao.findOne(playerId);
    }

    public Player createNewPlayer(Player player) {
        Arrays.asList(Sport.values())
                .forEach(
                sport -> playerMetricsDao.save(
                        PlayerMetrics.defaultPlayerMetrics(player.getId(), sport, lastPlace(sport))));

        return playerDao.save(player);
    }

    private Integer lastPlace(Sport sport) {
        return playerMetricsDao.findBySport(sport).size() + 1;
    }

    public void deletePlayer(String playerId) {
        playerDao.delete(playerId);
        List<PlayerMetrics> metricsToDelete = playerMetricsDao.findByPlayerId(playerId);
        playerMetricsDao.delete(metricsToDelete);
    }

    public Player updatePlayerDetails(String playerId, Player updatedPlayer) {
        updatedPlayer.setId(playerId);
        return playerDao.save(updatedPlayer);
    }

    public Optional<PlayerMetrics> getMetricsForPlayerBySport(String playerId, Sport sport) {
       return playerMetricsDao.findByPlayerIdAndSport(playerId, sport);
    }

    public List<Player> getPlayersByRankingForSport(Sport sport) {
        return playerMetricsDao.findBySport(sport)
                .stream()
                .sorted((curr, next) -> curr.getCurrentRanking().compareTo(next.getCurrentRanking()))
                .map(playerMetrics -> playerDao.findOne(playerMetrics.getPlayerId()))
                .collect(Collectors.toList());
    }

    public void challengePlayer(String playerId, Challenge challenge) {
        Player playerChallenged = playerDao.findOne(playerId);
        playerChallenged.recordChallenge(challenge);
        playerDao.save(playerChallenged);
    }

}
