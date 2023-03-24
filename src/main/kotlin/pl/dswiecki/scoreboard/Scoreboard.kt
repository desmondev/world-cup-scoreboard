package pl.dswiecki.scoreboard

class Scoreboard {

    var games = mutableListOf<Game>()

    fun getSummary(): List<Game> {
        return games
    }
}

data class Game(
    val homeTeamScore: Score,
    val awayTeamScore: Score
)

data class Score(
    val team: String,
    val score: Int
)
