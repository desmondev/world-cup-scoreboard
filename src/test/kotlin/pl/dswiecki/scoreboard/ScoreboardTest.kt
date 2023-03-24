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
}
