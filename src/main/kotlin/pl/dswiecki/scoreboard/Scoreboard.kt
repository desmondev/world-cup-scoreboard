package pl.dswiecki.scoreboard

import java.util.UUID

class Scoreboard {

    var games = mutableMapOf<UUID, Game>()

    fun getSummary(): List<Game> {
        return games.values.toList()
    }

    fun startGame(homeTeam: String, awayTeam: String): Game {
        val game = Game(TeamScore.initial(homeTeam), TeamScore.initial(awayTeam))
        games[game.id] = game
        return game
    }

    fun updateGame(game: UUID, team: String, newScore: Int): Game {
        val gameToUpdate = games[game] ?: throw IllegalArgumentException("Game not found")
        val updatedGame = updateSpecificGame(gameToUpdate, team, newScore)
        games[game] = updatedGame
        return updatedGame
    }

    private fun updateSpecificGame(
        gameToUpdate: Game,
        team: String,
        newScore: Int
    ): Game {
        return when {
            gameToUpdate.homeTeamScore.team == team -> gameToUpdate.updateHomeTeamScore(newScore)
            gameToUpdate.awayTeamScore.team == team -> gameToUpdate.updateAwayTeamScore(newScore)
            else -> throw IllegalArgumentException("Team not found")
        }
    }
}

data class Game(
    val homeTeamScore: TeamScore,
    val awayTeamScore: TeamScore
) {
    fun updateHomeTeamScore(newScore: Int) = copy(homeTeamScore = homeTeamScore.updateScore(newScore))

    fun updateAwayTeamScore(newScore: Int) = copy(awayTeamScore = awayTeamScore.updateScore(newScore))

    val id: UUID = UUID.randomUUID()
    val homeTeam: String = homeTeamScore.team
    val awayTeam: String = awayTeamScore.team
}

data class TeamScore(
    val team: String,
    val score: Int
) {
    fun updateScore(newScore: Int): TeamScore {
        assert(newScore > score) {
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
