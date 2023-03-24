package pl.dswiecki.scoreboard

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ScoreboardTest {

    @Test
    fun `should return empty scoreboard when no games started`() {
        val scoreboard = Scoreboard()

        val summary = scoreboard.getSummary()

        assertTrue(summary.isEmpty())
    }

    @Test
    fun `should start a new game`() {
        val scoreboard = Scoreboard()

        scoreboard.startGame("Team A", "Team B")

        val summary = scoreboard.getSummary()

        assertTrue(summary.isNotEmpty())
    }

}
