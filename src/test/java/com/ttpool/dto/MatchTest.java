package com.ttpool.dto;

import com.ttpool.utils.TestDataUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MatchTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testCreateTableTennisMatch() {
        // Given
        Player winner = new Player("John", "Smith");
        Player loser = new Player("Brad", "Pitt");

        // When
        Match tableTennisMatch = new Match(Sport.TABLE_TENNIS, winner, loser, 21, 10);

        // Then
        assertThat(tableTennisMatch.getSport(), is(Sport.TABLE_TENNIS));
        assertThat(tableTennisMatch.getWinner(), is(winner));
        assertThat(tableTennisMatch.getLoser(), is(loser));
        assertThat(tableTennisMatch.getLoserScore(), is(10));
        assertThat(tableTennisMatch.getWinnerScore(), is(21));
    }

    @Test
    public void testCreatePoolMatch() {
        // Given
        Player winner = new Player("John", "Smith");
        Player loser = new Player("Brad", "Pitt");

        // When
        Match tableTennisMatch = new Match(Sport.POOL, winner, loser, null, null);

        // Then
        assertThat(tableTennisMatch.getSport(), is(Sport.POOL));
        assertThat(tableTennisMatch.getWinner(), is(winner));
        assertThat(tableTennisMatch.getLoser(), is(loser));
        assertNull(tableTennisMatch.getLoserScore());
        assertNull(tableTennisMatch.getWinnerScore());
    }
}