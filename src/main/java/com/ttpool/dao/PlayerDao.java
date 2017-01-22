package com.ttpool.dao;

import com.ttpool.dto.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerDao extends MongoRepository<Player, String> { }
