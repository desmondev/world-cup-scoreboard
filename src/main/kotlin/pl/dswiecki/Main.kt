package pl.dswiecki

import pl.dswiecki.scoreboard.Scoreboard

fun main(args: Array<String>) {
    println("Hello World Cup Scoreboard!")

    val scoreboard = Scoreboard()
    val game1 = scoreboard.startGame("Mexico", "Canada")
    val game2 = scoreboard.startGame("Spain", "Brazil")
    val game3 = scoreboard.startGame("Germany", "France")
    val game4 = scoreboard.startGame("Uruguay", "Italy")
    val game5 = scoreboard.startGame("Argentina", "Australia")

    scoreboard.updateGame(game1.id, 0 to 5)
    scoreboard.updateGame(game2.id, 10 to 2)
    scoreboard.updateGame(game3.id, 2 to 2)
    scoreboard.updateGame(game4.id, 6 to 6)
    scoreboard.updateGame(game5.id, 3 to 1)

    scoreboard.getSummary().forEach { println(it) }
}
