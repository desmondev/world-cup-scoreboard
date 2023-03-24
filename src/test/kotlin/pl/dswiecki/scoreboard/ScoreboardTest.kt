package pl.dswiecki.scoreboard

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

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

    @Test
    fun `should update game score`() {
        val scoreboard = Scoreboard()

        var game = scoreboard.startGame("Team A", "Team B")

        game = scoreboard.updateGame(game.id, 1 to 0)

        assertEquals(1, game.homeTeamScore.score)
    }

    @Test
    fun `should return ordered scoreboard for game with same total score`() {
        val scoreboard = Scoreboard()

        val game1 = scoreboard.startGame("Team A", "Team B")
        val game2 = scoreboard.startGame("Team C", "Team D")
        val game3 = scoreboard.startGame("Team E", "Team F")

        scoreboard.updateGame(game1.id, 1 to 0)
        scoreboard.updateGame(game2.id, 1 to 0)

        val summary = scoreboard.getSummary()
        summary.forEach { println(it) }
        assertEquals("Team C", summary[0].homeTeam)
        assertEquals(1, summary[0].homeTeamScore.score)
        assertEquals("Team D", summary[0].awayTeam)
        assertEquals(0, summary[0].awayTeamScore.score)
        assertEquals("Team A", summary[1].homeTeam)
        assertEquals(1, summary[1].homeTeamScore.score)
        assertEquals("Team B", summary[1].awayTeam)
        assertEquals(0, summary[1].awayTeamScore.score)
        assertEquals("Team E", summary[2].homeTeam)
        assertEquals(0, summary[2].homeTeamScore.score)
        assertEquals("Team F", summary[2].awayTeam)
        assertEquals(0, summary[2].awayTeamScore.score)
    }

    @Test
    fun `should finish a game`() {
        val scoreboard = Scoreboard()
            .apply { startGame("Team A", "Team B") }

        val summary = scoreboard.getSummary()
        assertTrue(summary.isNotEmpty())

        scoreboard.finishGame(summary[0].id)

        val summaryAfterFinished = scoreboard.getSummary()
        assertTrue(summaryAfterFinished.isEmpty())
    }

    @Test
    fun `should throw when trying to finish inactive game`() {
        val scoreboard = Scoreboard()
        val game = scoreboard.startGame("Team A", "Team B")

        scoreboard.finishGame(game.id)

        val summaryAfterFinished = scoreboard.getSummary()
        assertTrue(summaryAfterFinished.isEmpty())

        assertThrows<IllegalArgumentException>("Game already finished") {
            scoreboard.finishGame(game.id)
        }
    }

    @Test
    fun `should throw when trying to update non existing game`() {
        val scoreboard = Scoreboard()

        assertThrows<IllegalArgumentException>("Game not found") {
            scoreboard.updateGame(UUID.randomUUID(), 1 to 0)
        }
    }

    @Test
    fun `should throw when starting a game with already playing team`() {
        val scoreboard = Scoreboard()
        val game1 = scoreboard.startGame("Team A", "Team B")

        assertThrows<IllegalArgumentException>("Team already playing") {
            scoreboard.startGame(game1.homeTeam, "Team C")
        }

    }
}
