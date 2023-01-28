package connectfour

import java.lang.NumberFormatException

class Player(val name: String, val sign: Char, var score: Int = 0)
class Settings(val firstPlayer: Player, val secondPlayer: Player, val numberOfGames: Int)

fun main() {
    val (firstPlayer, secondPlayer) = greetings()
    val (rows, columns) = collectDimensions()
    val numberOfGames = setNumberOfGames()

    println(
        "${firstPlayer.name} VS ${secondPlayer.name}\n" +
                "$rows X $columns board"
    )

    if (numberOfGames > 1) println("Total $numberOfGames games")

    val settings = Settings(firstPlayer, secondPlayer, numberOfGames)

    repeat(numberOfGames) {
        game(it, settings, rows, columns)
    }

    println("Game over!")
}

fun printBoard(board: MutableList<MutableList<Char>>, rows: Int, columns: Int) {
    for (i in 1..columns) print(" $i")
    print("\n")
    for (j in rows - 1 downTo 0) {
        print("║")
        for (k in 0 until columns) {
            print("${board[k][j]}║")
        }
        print("\n")
    }
    print("╚")
    for (l in 1 until columns) print("═╩")
    print("═╝\n")
}

fun greetings(): Pair<Player, Player> {
    println("Connect Four")
    println("First player's name:")
    val firstPlayer = Player(readln(), 'o')
    println("Second player's name:")
    val secondPlayer = Player(readln(), '*')

    return Pair(firstPlayer, secondPlayer)
}

fun collectDimensions(): Pair<Int, Int> {
    val pattern = Regex("""\d+\s*[xX]\s*\d+""")

    while (true) {
        println(
            "Set the board dimensions (Rows x Columns)\n" +
                    "Press Enter for default (6 x 7)"
        )
        val input = readln().uppercase().trim().replace("\t", "").replace(" ", "")
        if (input != "") {
            if (!input.matches(pattern)) {
                println("Invalid input")
                collectDimensions()
            } else {
                val a = input.substringBefore('X').toInt()
                val b = input.substringAfter('X').toInt()
                if (a < 5 || a > 9) {
                    println("Board rows should be from 5 to 9")
                    collectDimensions()
                } else if (b < 5 || b > 9) {
                    println("Board columns should be from 5 to 9")
                    collectDimensions()
                } else return Pair(a, b)
            }
        } else return Pair(6, 7)
    }
}

fun setNumberOfGames(): Int {
    while (true) {
        println("Do you want to play single or multiple games?\n" +
                "For a single game, input 1 or press Enter\n" +
                "Input a number of games:")
        val input = readln()
        if (input == "") {
            return 1
        }
        if (!input.matches(Regex("\\d+"))) {
            println("Invalid input")
            continue
        }
        if (input.toInt() < 1) {
            println("Invalid input")
            continue
        }
        return input.toInt()
    }
}

fun game(gameNumber: Int, settings: Settings, rows: Int, columns: Int) {
    val board = MutableList(columns) { MutableList(rows) { ' ' } }

    if (settings.numberOfGames > 1) {
        println("Game #${gameNumber + 1}")
    } else println("Single game")

    printBoard(board, rows, columns)

    var player = if (gameNumber % 2 == 0) settings.firstPlayer else settings.secondPlayer

    while (true) {
        println("${player.name}'s turn:")
        val input = readln()

        try {
            if (input == "end") {
                println("Game over!")
                return
            } else {
                val chosenColumn = input.toInt() - 1
                var count = 0
                for (row in board[chosenColumn].indices) {
                    if (board[chosenColumn][row] == ' ') {
                        board[chosenColumn][row] = player.sign
                        break
                    } else count++
                }
                if (count == board[chosenColumn].size) {
                    println("Column $input is full")
                    continue
                }
            }
        } catch (e: IndexOutOfBoundsException) {
            println("The column number is out of range (1 - $columns)")
            continue
        } catch (e: NumberFormatException) {
            println("Incorrect column number")
            continue
        }

        printBoard(board, rows, columns)

        if (checkWin(player, board)) {
            player.score += 2
            println("Player ${player.name} won")
            println("Score")
            println(
                "${settings.firstPlayer.name}: ${settings.firstPlayer.score}" +
                        " ${settings.secondPlayer.name}: ${settings.secondPlayer.score}"
            )
            return
        }

        if (checkDraw(board)) {
            settings.firstPlayer.score++
            settings.secondPlayer.score++
            println("It is a draw")
            println("Score")
            println(
                "${settings.firstPlayer.name}: ${settings.firstPlayer.score}" +
                        " ${settings.secondPlayer.name}: ${settings.secondPlayer.score}"
            )
            return
        }

        player = changePlayers(player, settings)
    }
}

fun checkWin(player: Player, board: MutableList<MutableList<Char>>): Boolean {
    if (
        checkRow(player.sign, board) || checkColumn(player.sign, board) ||
        checkDiagonal(player.sign, board) || checkDiagonalReversed(player.sign, board)
    ) {
        return true
    }
    return false
}

fun checkRow(sign: Char, board: MutableList<MutableList<Char>>): Boolean {
    for (c in 0 .. board.size - 4) {
        val column = board[c]
        for (r in column.indices) {
            if (
                board[c][r] == sign &&
                board[c + 1][r] == sign &&
                board[c + 2][r] == sign &&
                board[c + 3][r] == sign
                ) {
                return true
            }
        }
    }
    return false
}

fun checkColumn(sign: Char, board: MutableList<MutableList<Char>>): Boolean {
    for (c in board.indices) {
        val column = board[c]
        for (r in 0..column.size - 4) {
            if (
                board[c][r] == sign &&
                board[c][r + 1] == sign &&
                board[c][r + 2] == sign &&
                board[c][r + 3] == sign
                ) {
                return true
            }
        }
    }
    return false
}

fun checkDiagonal(sign: Char, board: MutableList<MutableList<Char>>): Boolean {
    for (c in 0 .. board.size - 4) {
        val column = board[c]
        for (r in 0 .. column.size - 4) {
            if (
                board[c][r] == sign &&
                board[c + 1][r + 1] == sign &&
                board[c + 2][r + 2] == sign &&
                board[c + 3][r + 3] == sign
                ) {
                return true
            }
        }
    }
    return false
}

fun checkDiagonalReversed(sign: Char, board: MutableList<MutableList<Char>>): Boolean {
    for (c in 0 .. board.size - 4) {
        val column = board[c]
        for (r in 3 until column.size) {
            if (
                board[c][r] == sign &&
                board[c + 1][r - 1] == sign &&
                board[c + 2][r - 2] == sign &&
                board[c + 3][r - 3] == sign
                ) {
                return true
            }
        }
    }
    return false
}

fun checkDraw(board: MutableList<MutableList<Char>>): Boolean {
    var count = 0
    for (column in board.indices) {
        if (' ' in board[column]) count++
    }
    if (count == 0) {
        return true
    }
    return false
}

fun changePlayers(player: Player, settings: Settings): Player {
    return if (player == settings.firstPlayer) settings.secondPlayer else settings.firstPlayer
}