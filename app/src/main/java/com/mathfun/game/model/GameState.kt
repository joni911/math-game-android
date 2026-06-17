package com.mathfun.game.model

data class GameState(
    val player1: Player,
    val player2: Player,
    var currentQuestion: Question?,
    var questionNumber: Int = 0,
    var currentLevel: Int = 1,
    var isGameOver: Boolean = false,
    var winner: Player? = null
) {
    fun reset() {
        player1.reset()
        player2.reset()
        currentQuestion = null
        questionNumber = 0
        currentLevel = 1
        isGameOver = false
        winner = null
    }
    
    fun checkGameOver(): Boolean {
        return when {
            player1.isWinner -> {
                isGameOver = true
                winner = player1
                true
            }
            player2.isWinner -> {
                isGameOver = true
                winner = player2
                true
            }
            player1.hasLost -> {
                isGameOver = true
                winner = player2
                true
            }
            player2.hasLost -> {
                isGameOver = true
                winner = player1
                true
            }
            questionNumber >= 10 -> {
                isGameOver = true
                winner = if (player1.score > player2.score) player1 
                        else if (player2.score > player1.score) player2 
                        else null
                true
            }
            else -> false
        }
    }
}