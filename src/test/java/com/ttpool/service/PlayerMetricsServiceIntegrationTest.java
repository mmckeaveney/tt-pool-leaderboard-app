package com.ttpool.service;

import com.ttpool.LeaderboardApplication;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.ttpool.dto.Sport.TABLE_TENNIS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { LeaderboardApplication.class, EmbeddedMongoAutoConfiguration.class })
@TestPropertySource(locations="classpath:test.properties")
public class PlayerMetricsServiceIntegrationTest {
    @Autowired
    private PlayerMetricsService playerMetricsService;
    @Autowired
    private PlayerMetricsDao playerMetricsDao;
    @Autowired
    private PlayerService playerService;

    private Match match;

    private static final String PLAYER_ONE_ID = "1";
    private static final String PLAYER_TWO_ID = "2";

    @Before
    public void setUp() {
        Player player = TestDataUtils.createDummyPlayer();
        player.setId(PLAYER_ONE_ID);
        playerService.createNewPlayer(player);

        Player player2 = TestDataUtils.createAnotherDummyPlayer();
        player2.setId(PLAYER_TWO_ID);
        playerService.createNewPlayer(player2);

        // Player2 beat player1 at table tennis
        match = new Match(TABLE_TENNIS, player2, player, 21, 5);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void updateRecordsForPlayers() throws Exception {
        // Given

        // When
        playerMetricsService.updateRecordsForPlayers(match);
        List<PlayerMetrics> playerList = playerMetricsDao.findBySport(TABLE_TENNIS);

        // Then
        assertThat(playerList.get(1).getBiggestVictory(), is(match));
        assertThat(playerList.get(1).getCurrentRanking(), is(1));
        assertThat(playerList.get(1).getHighestRanking(), is(1));
        assertThat(playerList.get(1).getCurrentWinStreak(), is(1));
        assertThat(playerList.get(1).getHighestWinStreak(), is(1));

        assertThat(playerList.get(0).getBiggestDefeat(), is(match));
        assertThat(playerList.get(0).getCurrentRanking(), is(2));
        assertThat(playerList.get(0).getHighestRanking(), is(0));
        assertThat(playerList.get(0).getCurrentWinStreak(), is(0));
        assertThat(playerList.get(0).getHighestWinStreak(), is(0));
    }

}