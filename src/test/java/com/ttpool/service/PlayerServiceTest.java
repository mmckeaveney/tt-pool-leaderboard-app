package com.ttpool.service;

import com.ttpool.dao.PlayerDao;
import com.ttpool.dao.PlayerMetricsDao;
import com.ttpool.dto.Challenge;
import com.ttpool.dto.Player;
import com.ttpool.dto.PlayerMetrics;
import com.ttpool.dto.Sport;
import com.ttpool.utils.TestDataUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {
    @Mock
    private PlayerDao playerDao;
    @Mock
    private PlayerMetricsDao playerMetricsDao;
    @InjectMocks
    private PlayerService playerService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void createNewPlayer() throws Exception {
        // Given
        Player dummyPlayer = new Player("Joe", "Bloggs");
        dummyPlayer.setId("1");
        when(playerDao.save(dummyPlayer)).thenReturn(dummyPlayer);

        // When
        playerService.createNewPlayer(dummyPlayer);

        // Then
        verify(playerDao, times(1)).save(dummyPlayer);
    }

    @Test
    public void findPlayerById() throws Exception {
        // Given
        String playerId = "1234";
        Player dummyPlayer = TestDataUtils.createDummyPlayer();
        when(playerDao.findOne(playerId)).thenReturn(dummyPlayer);

        // When
        Player foundPlayer = playerService.findPlayerById(playerId);

        // Then
        assertThat(foundPlayer.getFirstName(), is(dummyPlayer.getFirstName()));
        assertThat(foundPlayer.getLastName(), is(dummyPlayer.getLastName()));
    }

    @Test
    public void deletePlayer() throws Exception {
        // Given
        String playerId = "1234";

        // When
        playerService.deletePlayer(playerId);

        // Then
        verify(playerDao, times(1)).delete(playerId);
    }

    @Test
    public void updatePlayerDetails() throws Exception {
        // Given
        String playerId = "1234";
        when(playerDao.findOne(playerId)).thenReturn(TestDataUtils.createDummyPlayer());

        // When
        Player updatedPlayer = new Player("Testibald", "Testwood");
        playerService.updatePlayerDetails(playerId, updatedPlayer);

        // Then
        assertThat(updatedPlayer.getId(), is(playerId));
        verify(playerDao, times(1)).save(updatedPlayer);
    }

    @Test
    public void getTableTennisMetricsForPlayer() throws Exception {
        // Given
        Player dummyPlayer = new Player("Testy", "McTesterson");
        dummyPlayer.setId("1234");
        PlayerMetrics playerMetrics = new PlayerMetrics(dummyPlayer.getId(), Sport.TABLE_TENNIS, 1, 1, 5, 10);
        when(playerMetricsDao.findByPlayerIdAndSport(dummyPlayer.getId(), Sport.TABLE_TENNIS))
                .thenReturn(Optional.of(playerMetrics));

        // When
        Optional<PlayerMetrics> foundMetrics = playerService.getMetricsForPlayerBySport("1234", Sport.TABLE_TENNIS);

        // Then
        assertThat(foundMetrics.get().getSport(), is(Sport.TABLE_TENNIS));
        assertThat(foundMetrics.get().getCurrentRanking(), is(1));
        assertThat(foundMetrics.get().getCurrentWinStreak(), is(5));
        assertThat(foundMetrics.get().getHighestWinStreak(), is(10));
    }

    @Test
    public void getTableTennisMetricsForPlayerWhenNonePresent() throws Exception {
        // Given
        when(playerMetricsDao.findByPlayerIdAndSport(anyString(), eq(Sport.TABLE_TENNIS))).thenReturn(Optional.empty());

        // When
        Optional<PlayerMetrics> foundMetrics = playerService.getMetricsForPlayerBySport("1234", Sport.TABLE_TENNIS);

        // Then
        assertThat(foundMetrics, is(Optional.empty()));
    }

    @Test
    public void getPoolMetricsForPlayer() throws Exception {
        // Given
        Player dummyPlayer = new Player("Testy", "McTesterson");
        dummyPlayer.setId("1234");
        PlayerMetrics playerMetrics = new PlayerMetrics(dummyPlayer.getId(), Sport.POOL, 1, 1, 5, 10);
        when(playerMetricsDao.findByPlayerIdAndSport(dummyPlayer.getId(), Sport.POOL))
                .thenReturn(Optional.of(playerMetrics));

        // When
        Optional<PlayerMetrics> foundMetrics = playerService.getMetricsForPlayerBySport("1234", Sport.POOL);

        // Then
        assertThat(foundMetrics.get().getSport(), is(Sport.POOL));
        assertThat(foundMetrics.get().getCurrentRanking(), is(1));
        assertThat(foundMetrics.get().getCurrentWinStreak(), is(5));
        assertThat(foundMetrics.get().getHighestWinStreak(), is(10));
    }

    @Test
    public void getPoolMetricsForPlayerWhenNonePresent() throws Exception {
        // Given
        when(playerMetricsDao.findByPlayerIdAndSport(anyString(), eq(Sport.POOL))).thenReturn(Optional.empty());

        // When
        Optional<PlayerMetrics> foundMetrics = playerService.getMetricsForPlayerBySport("1234", Sport.POOL);

        // Then
        assertThat(foundMetrics, is(Optional.empty()));
    }

    @Test
    public void getTableTennisPlayersByRanking() {
        // Given
        Player topPlayer = new Player("Testy", "McTesterson");
        topPlayer.setId("1234");
        PlayerMetrics topPlayerMetrics = new PlayerMetrics(topPlayer.getId(), Sport.TABLE_TENNIS, 1, 1, 5, 10);
        Player secondPlayer = new Player("Testo", "El Testico");
        secondPlayer.setId("12345");
        PlayerMetrics secondPlayerMetrics = new PlayerMetrics(secondPlayer.getId(), Sport.TABLE_TENNIS, 2, 1, 1, 12);
        List<PlayerMetrics> ttMetrics = Arrays.asList(topPlayerMetrics, secondPlayerMetrics);
        when(playerMetricsDao.findBySport(Sport.TABLE_TENNIS)).thenReturn(ttMetrics);
        when(playerDao.findOne(topPlayer.getId())).thenReturn(topPlayer);
        when(playerDao.findOne(secondPlayer.getId())).thenReturn(secondPlayer);

        // When
        List<Player> playersByRank = playerService.getPlayersByRankingForSport(Sport.TABLE_TENNIS);

        // Then
        assertThat(playersByRank.size(), is(2));
        assertThat(playersByRank.get(0), is(topPlayer));
        assertThat(playersByRank.get(1), is(secondPlayer));
    }

    @Test
    public void getPoolPlayersByRanking() {
        // Given
        Player topPlayer = new Player("Testy", "McTesterson");
        topPlayer.setId("1234");
        PlayerMetrics topPlayerMetrics = new PlayerMetrics(topPlayer.getId(), Sport.POOL, 1, 1, 5, 10);
        Player secondPlayer = new Player("Testo", "El Testico");
        secondPlayer.setId("2345");
        PlayerMetrics secondPlayerMetrics = new PlayerMetrics(secondPlayer.getId(), Sport.POOL, 2, 1, 1, 12);
        List<PlayerMetrics> allPoolMetrics = Arrays.asList(topPlayerMetrics, secondPlayerMetrics);
        when(playerMetricsDao.findBySport(Sport.POOL)).thenReturn(allPoolMetrics);
        when(playerDao.findOne(topPlayer.getId())).thenReturn(topPlayer);
        when(playerDao.findOne(secondPlayer.getId())).thenReturn(secondPlayer);

        // When
        List<Player> playersByRank = playerService.getPlayersByRankingForSport(Sport.POOL);

        // Then
        assertThat(playersByRank.size(), is(2));
        assertThat(playersByRank.get(0), is(topPlayer));
        assertThat(playersByRank.get(1), is(secondPlayer));
    }

    @Test
    public void createChallengeAgainstPlayer() {
        // Given
        Player player = new Player("Testy", "McTesterson");
        player.setId("1234");
        Challenge challenge = new Challenge(Sport.TABLE_TENNIS, player);
        when(playerDao.findOne(anyString())).thenReturn(player);

        // When
        playerService.challengePlayer("1234", challenge);

        // Then
        assertFalse(playerService.findPlayerById("1234").getCurrentChallengesBySport().isEmpty());
        assertThat(playerService.findPlayerById("1234").getCurrentChallengesBySport().get(0),
                is(challenge));

    }
}