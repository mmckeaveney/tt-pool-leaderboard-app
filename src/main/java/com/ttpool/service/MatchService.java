package com.ttpool.service;

import com.ttpool.dao.MatchDao;
import com.ttpool.dao.PlayerMetricsDao;
import com.ttpool.dto.Match;
import com.ttpool.dto.PlayerMetrics;
import com.ttpool.dto.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class MatchService {

    private MatchDao matchDao;
    private PlayerMetricsService playerMetricsService;

    @Autowired
    protected MatchService(MatchDao matchDao, PlayerMetricsService playerMetricsService) {
        this.matchDao = matchDao;
        this.playerMetricsService = playerMetricsService;
    }

    public Match recordMatch(Match match) {
        Match savedMatch = matchDao.save(match);
        playerMetricsService.updateRecordsForPlayers(match);

        return savedMatch;
    }
}
