package com.ttpool.dao;

import com.ttpool.dto.PlayerMetrics;
import com.ttpool.dto.Sport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerMetricsDao extends MongoRepository<PlayerMetrics, String> {

    Optional<PlayerMetrics> findByPlayerIdAndSport(String playerId, Sport sport);

    List<PlayerMetrics> findBySport(Sport sport);

    List<PlayerMetrics> findByPlayerId(String playerId);
}
