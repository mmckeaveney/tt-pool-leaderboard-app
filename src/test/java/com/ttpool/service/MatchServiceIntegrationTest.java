package com.ttpool.service;

import com.ttpool.LeaderboardApplication;
import com.ttpool.dao.MatchDao;
import com.ttpool.dao.PlayerDao;
import com.ttpool.dao.PlayerMetricsDao;
import com.ttpool.dto.Match;
import com.ttpool.dto.Player;
import com.ttpool.dto.PlayerMetrics;
import com.ttpool.dto.Sport;
import com.ttpool.utils.TestDataUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { LeaderboardApplication.class, EmbeddedMongoAutoConfiguration.class })
@TestPropertySource(locations="classpath:test.properties")
public class MatchServiceIntegrationTest {

    @Autowired
    private MatchService matchService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private MatchDao matchDao;
    @Autowired
    private PlayerDao playerDao;

    private Player player;
    private Player player2;

    @Before
    public void setUp() throws Exception {
        player = TestDataUtils.createDummyPlayer();
        player.setId("1");
        player2 = TestDataUtils.createAnotherDummyPlayer();
        player2.setId("2");
        playerService.createNewPlayer(player);
        playerService.createNewPlayer(player2);
    }

    @After
    public void tearDown() throws Exception {
        playerDao.deleteAll();
        matchDao.deleteAll();
    }

    @Test
    public void recordTtMatch() throws Exception {
        // Given
        Match match = new Match(Sport.TABLE_TENNIS, player2, player, 21, 1);

        // When
        Match savedMatch = matchService.recordMatch(match);

        // Then
        assertThat(savedMatch, is(match));
    }

    @Test
    public void recordPoolMatch() throws Exception {
        // Given
        Match match = new Match(Sport.POOL, player2, player, 21, 1);

        // When
        Match savedMatch = matchService.recordMatch(match);

        // Then
        assertThat(savedMatch, is(match));
    }
}