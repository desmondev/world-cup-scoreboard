package pl.dswiecki.scoreboard

import java.time.OffsetDateTime
import java.util.UUID

class Scoreboard {

    private var games = mutableMapOf<UUID, Game>()

    fun getSummary(): List<Game> {
        return games.values.toList()
            .filter { it.isActive() }
            .sortedWith(
                compareByDescending<Game> { it.totalScore() }
                    .thenByDescending { it.startedAt }
            )
    }

    fun startGame(homeTeam: String, awayTeam: String): Game {
        val game = Game(TeamScore.initial(homeTeam), TeamScore.initial(awayTeam))
        games[game.id] = game
        return game
    }

    fun updateGame(game: UUID, newScore: Pair<Int, Int>): Game {
        val oldGame = games[game] ?: throw IllegalArgumentException("Game not found")
        val updatedGame = oldGame
            .updateHomeTeamScore(newScore.first)
            .updateAwayTeamScore(newScore.second)
        games[game] = updatedGame
        return updatedGame
    }

    fun finishGame(id: UUID) {
        val oldGame = games[id] ?: throw IllegalArgumentException("Game not found")
        require(oldGame.isActive()) { "Game already finished" }
        val finishedGame = oldGame.finish()
        games[id] = finishedGame
    }

}

data class Game(
    val homeTeamScore: TeamScore,
    val awayTeamScore: TeamScore,
    val startedAt: OffsetDateTime = OffsetDateTime.now(),
    val finishedAt: OffsetDateTime? = null
) {
    fun updateHomeTeamScore(newScore: Int) = copy(homeTeamScore = homeTeamScore.updateScore(newScore))

    fun updateAwayTeamScore(newScore: Int) = copy(awayTeamScore = awayTeamScore.updateScore(newScore))
    fun totalScore(): Int = homeTeamScore.score + awayTeamScore.score
    fun isActive(): Boolean = finishedAt == null
    fun finish(): Game = copy(finishedAt = OffsetDateTime.now())

    val id: UUID = UUID.randomUUID()
    val homeTeam: String = homeTeamScore.team
    val awayTeam: String = awayTeamScore.team
}

data class TeamScore(
    val team: String,
    val score: Int
) {
    fun updateScore(newScore: Int): TeamScore {
        require(newScore >= score) {
            "New score must be greater than current score"
        }
        return copy(score = newScore)
    }

    companion object {
        fun initial(team: String): TeamScore {
            return TeamScore(team, 0)
        }
    }
}
