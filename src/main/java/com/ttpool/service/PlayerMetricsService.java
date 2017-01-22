package com.ttpool.service;

import com.ttpool.dao.PlayerMetricsDao;
import com.ttpool.dto.Match;
import com.ttpool.dto.PlayerMetrics;
import com.ttpool.dto.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerMetricsService {

    private PlayerMetricsDao playerMetricsDao;

    @Autowired
    public PlayerMetricsService(PlayerMetricsDao playerMetricsDao) {
        this.playerMetricsDao = playerMetricsDao;
    }

    public void updateRecordsForPlayers(Match match) {
        Sport sport = match.getSport();
        String winnerId = match.getWinner().getId();
        String loserId = match.getLoser().getId();

        Optional<PlayerMetrics> winnerMetrics =
                playerMetricsDao.findByPlayerIdAndSport(winnerId, sport);
        Optional<PlayerMetrics> loserMetrics =
                playerMetricsDao.findByPlayerIdAndSport(loserId, sport);

        if (winnerMetrics.isPresent() && loserMetrics.isPresent()) {
            updateRecordsForWinner(match, winnerMetrics.get());
            updateRecordsForLoser(match, loserMetrics.get());
        } else {
            throw new UnsupportedOperationException(); // TODO throw more appropriate error
        }
    }

    private void updateRecordsForWinner(Match match, PlayerMetrics metrics) {
        int newRanking = metrics.getCurrentRanking() == 1 ? 1 : metrics.getCurrentRanking() - 1;

        if (newRanking > metrics.getHighestRanking()) {
            metrics.setHighestRanking(newRanking);
        }

        metrics.setCurrentRanking(newRanking);
        metrics.setCurrentWinStreak(metrics.getCurrentWinStreak() + 1);

        if (metrics.getCurrentWinStreak() > metrics.getHighestWinStreak()) {
            metrics.setHighestWinStreak(metrics.getCurrentWinStreak());
        }

        if (match.getSport() != Sport.POOL) {
            calculateBiggestVictory(match, metrics);
        }

        playerMetricsDao.save(metrics);
    };

    private void calculateBiggestVictory(Match match, PlayerMetrics metrics) {
        Integer currentWinMargin = calculateScoreMargin(metrics.getBiggestVictory());
        Integer matchWinMargin = calculateScoreMargin(match);

        if (matchWinMargin > currentWinMargin) {
            metrics.setBiggestVictory(match);
        }
    }

    private void updateRecordsForLoser(Match match, PlayerMetrics metrics) {
        metrics.setCurrentRanking(metrics.getCurrentRanking() + 1);
        metrics.setCurrentWinStreak(0);

        if (match.getSport() != Sport.POOL) {
            calculateBiggestDefeat(match, metrics);
        }

        playerMetricsDao.save(metrics);
    };

    private void calculateBiggestDefeat(Match match, PlayerMetrics metrics) {
        Integer currentLossMargin = calculateScoreMargin(metrics.getBiggestDefeat());
        Integer matchLossMargin = calculateScoreMargin(match);

        if (matchLossMargin > currentLossMargin) {
            metrics.setBiggestDefeat(match);
        }
    }

    private int calculateScoreMargin(Match match) {
        if (match == null) return 0;

        return match.getWinnerScore() - match.getLoserScore();
    }

}
