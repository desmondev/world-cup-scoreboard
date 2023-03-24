package pl.dswiecki.scoreboard

class Scoreboard {

    var games = mutableListOf<Game>()

    fun getSummary(): List<Game> {
        return games
    }

    fun startGame(homeTeam: String, awayTeam: String) {
        games.add(Game(Score.initial(homeTeam), Score.initial(awayTeam)))
    }
}

data class Game(
    val homeTeamScore: Score,
    val awayTeamScore: Score
)

data class Score(
    val team: String,
    val score: Int
) {
    companion object {
        fun initial(team: String): Score {
            return Score(team, 0)
        }
    }
}
