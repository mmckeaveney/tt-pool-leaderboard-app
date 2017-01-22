package com.ttpool.dao;

import com.ttpool.dto.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchDao extends MongoRepository<Match, String> { }
