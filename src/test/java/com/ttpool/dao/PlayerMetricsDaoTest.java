package com.ttpool.dao;

import com.ttpool.LeaderboardApplication;
import com.ttpool.dto.Player;
import com.ttpool.dto.PlayerMetrics;
import com.ttpool.dto.Sport;
import com.ttpool.utils.TestDataUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { LeaderboardApplication.class, EmbeddedMongoAutoConfiguration.class })
@TestPropertySource(locations="classpath:test.properties")
public class PlayerMetricsDaoTest {

    @Autowired
    private PlayerMetricsDao playerMetricsDao;

    private PlayerMetrics dummyPlayerMetricsTt;
    private PlayerMetrics dummyPlayerMetricsPool;
    private PlayerMetrics dummyPlayerMetricsTt2;
    private PlayerMetrics dummyPlayerMetricsPool2;

    private static final String PLAYER_ONE_ID = "1";
    private static final String PLAYER_TWO_ID = "2";

    @Before
    public void setUp() throws Exception {
        dummyPlayerMetricsTt =
                TestDataUtils.createDummyPlayerMetrics(PLAYER_ONE_ID, Sport.TABLE_TENNIS);
        dummyPlayerMetricsPool =
                TestDataUtils.createDummyPlayerMetrics(PLAYER_ONE_ID, Sport.POOL);
        dummyPlayerMetricsTt2 =
                TestDataUtils.createDummyPlayerMetrics(PLAYER_TWO_ID, Sport.TABLE_TENNIS);
        dummyPlayerMetricsPool2 =
                TestDataUtils.createDummyPlayerMetrics(PLAYER_TWO_ID, Sport.POOL);

        playerMetricsDao.save(Arrays.asList(
                dummyPlayerMetricsTt, dummyPlayerMetricsPool, dummyPlayerMetricsTt2, dummyPlayerMetricsPool2 ));
    }

    @After
    public void tearDown() throws Exception {
        playerMetricsDao.deleteAll();
    }

    @Test
    public void findTableTennisPlayerMetricsForPlayer() throws Exception {
        // When
        Optional<PlayerMetrics> playerMetrics =
                playerMetricsDao.findByPlayerIdAndSport(PLAYER_ONE_ID, Sport.TABLE_TENNIS);

        // Then
        assertTrue(playerMetrics.isPresent());
        assertThat(playerMetrics.get(), is(dummyPlayerMetricsTt));

    }

    @Test
    public void findTableTennisPlayerMetricsWhenNoneExist() throws Exception {
        // When
        Optional<PlayerMetrics> playerMetrics =
                playerMetricsDao.findByPlayerIdAndSport("JoeBloggs", Sport.TABLE_TENNIS);

        // Then
        assertFalse(playerMetrics.isPresent());
    }

    @Test
    public void findPoolMetricsForPlayer() throws Exception {
        // When
        Optional<PlayerMetrics> playerMetrics1 =
                playerMetricsDao.findByPlayerIdAndSport(PLAYER_ONE_ID, Sport.POOL);
        Optional<PlayerMetrics> playerMetrics2 =
                playerMetricsDao.findByPlayerIdAndSport(PLAYER_TWO_ID, Sport.POOL);

        // Then
        assertTrue(playerMetrics1.isPresent());
        assertThat(playerMetrics1.get(), is(dummyPlayerMetricsPool));
        assertTrue(playerMetrics2.isPresent());
        assertThat(playerMetrics2.get(), is(dummyPlayerMetricsPool2));
    }

    @Test
    public void findPoolPlayerMetricsWhenNoneExist() throws Exception {
        // When
        Optional<PlayerMetrics> playerMetrics =
                playerMetricsDao.findByPlayerIdAndSport("JoeBloggs", Sport.POOL);

        // Then
        assertFalse(playerMetrics.isPresent());
    }

    @Test
    public void getAllTableTennisMetrics() throws Exception {
        // When
        List<PlayerMetrics> allTtMetrics = playerMetricsDao.findBySport(Sport.TABLE_TENNIS);

        // Then
        assertFalse(allTtMetrics.isEmpty());
        assertThat(allTtMetrics, is(Arrays.asList(dummyPlayerMetricsTt, dummyPlayerMetricsTt2)));
    }

    @Test
    public void getAllPoolMetrics() throws Exception {
        // When
        List<PlayerMetrics> allTtMetrics = playerMetricsDao.findBySport(Sport.POOL);

        // Then
        assertFalse(allTtMetrics.isEmpty());
        assertThat(allTtMetrics, is(Arrays.asList(dummyPlayerMetricsPool, dummyPlayerMetricsPool2)));
    }

}