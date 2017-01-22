package com.ttpool.service;

import com.ttpool.LeaderboardApplication;
import com.ttpool.dao.PlayerDao;
import com.ttpool.dao.PlayerMetricsDao;
import com.ttpool.dto.Challenge;
import com.ttpool.dto.Player;
import com.ttpool.dto.PlayerMetrics;
import com.ttpool.dto.Sport;
import com.ttpool.utils.TestDataUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ttpool.dto.Sport.TABLE_TENNIS;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { LeaderboardApplication.class, EmbeddedMongoAutoConfiguration.class })
@TestPropertySource(locations="classpath:test.properties")
public class PlayerServiceIntegrationTest {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private PlayerMetricsDao playerMetricsDao;

    private static final String PLAYER_ID = "1234";

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        playerDao.deleteAll();
        playerMetricsDao.deleteAll();
    }

    @Test
    public void findPlayerById() throws Exception {
        // Given
        Player player = TestDataUtils.createDummyPlayer();
        player.setId(PLAYER_ID);

        // When
        playerService.createNewPlayer(player);
        Player found = playerService.findPlayerById(player.getId());

        // Then
        assertThat(found, is(player));
        assertThat(found.getCurrentChallengesBySport(), is(Collections.EMPTY_LIST));
    }

    @Test
    public void deletePlayer() throws Exception {
        // Given
        Player player = TestDataUtils.createDummyPlayer();
        player.setId(PLAYER_ID);

        // When
        playerService.createNewPlayer(player);
        playerService.deletePlayer(player.getId());
        Player found = playerService.findPlayerById(player.getId());

        // Then
        assertNull(found);
    }

    @Test
    public void updatePlayerDetails() throws Exception {
        // Given
        Player player = TestDataUtils.createDummyPlayer();
        player.setId(PLAYER_ID);
        Player updatedPlayer = TestDataUtils.createAnotherDummyPlayer();

        // When
        playerService.createNewPlayer(player);
        playerService.updatePlayerDetails(PLAYER_ID, updatedPlayer);
        Player found = playerService.findPlayerById(player.getId());

        // Then
        assertThat(found, is(updatedPlayer));
    }

    @Test
    public void getMetricsForPlayerBySport() throws Exception {
        // Given
        Player player = TestDataUtils.createDummyPlayer();
        player.setId(PLAYER_ID);

        // When
        playerService.createNewPlayer(player);
        Optional<PlayerMetrics> foundMetrics =
                playerService.getMetricsForPlayerBySport(player.getId(), TABLE_TENNIS);

        // Then
        assertTrue(foundMetrics.isPresent());
        assertThat(foundMetrics.get().getPlayerId(), is(PLAYER_ID));
        assertThat(foundMetrics.get().getSport(), is(TABLE_TENNIS));
    }

    @Test
    public void getPlayersByRankingForSport() throws Exception {
        // Given
        Player player = TestDataUtils.createDummyPlayer();
        Player player2 = TestDataUtils.createAnotherDummyPlayer();
        player.setId(PLAYER_ID);
        player2.setId("12345");

        // When
        playerService.createNewPlayer(player);
        playerService.createNewPlayer(player2);
        List<Player> rankedPlayers = playerService.getPlayersByRankingForSport(TABLE_TENNIS);

        // Then
        assertTrue(rankedPlayers.size() == 2);
        assertThat(rankedPlayers.get(0), is(player));
        assertThat(rankedPlayers.get(1), is(player2));
    }

    @Test
    public void challengePlayer() throws Exception {
        // Given
        Player player = TestDataUtils.createDummyPlayer();
        Player player2 = TestDataUtils.createAnotherDummyPlayer();
        player.setId(PLAYER_ID);
        player2.setId("12345");
        Challenge challenge = new Challenge(TABLE_TENNIS, player2);
        // When
        playerService.createNewPlayer(player);
        playerService.createNewPlayer(player2);
        playerService.challengePlayer(player.getId(), challenge);
        Player challengedPlayer = playerService.findPlayerById(PLAYER_ID);

        // Then
        assertNotNull(challengedPlayer);
        assertThat(challengedPlayer.getCurrentChallengesBySport().size(), is(1));
        assertThat(challengedPlayer.getCurrentChallengesBySport().get(0), is(challenge));
    }

}