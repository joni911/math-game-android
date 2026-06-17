package com.mathfun.game.game

import android.os.CountDownTimer
import com.mathfun.game.model.GameState
import com.mathfun.game.model.Player
import com.mathfun.game.model.Question

class GameController(
    private val gameState: GameState,
    private val callback: GameCallback
) {
    private val questionGenerator = QuestionGenerator()
    private var timer: CountDownTimer? = null
    private var timeRemaining: Int = 0
    
    interface GameCallback {
        fun onNewQuestion(question: Question, questionNumber: Int, level: Int)
        fun onTimerTick(secondsRemaining: Int)
        fun onTimerWarning() // At 5 seconds
        fun onCorrectAnswer(player: Player)
        fun onWrongAnswer(player: Player)
        fun onBothTimeout()
        fun onLevelUp(level: Int)
        fun onGameOver(winner: Player?, player1: Player, player2: Player)
        fun onScoreUpdate(player1Score: Int, player2Score: Int)
    }
    
    fun startGame() {
        gameState.reset()
        nextQuestion()
    }
    
    fun nextQuestion() {
        if (gameState.checkGameOver()) {
            callback.onGameOver(gameState.winner, gameState.player1, gameState.player2)
            return
        }
        
        gameState.questionNumber++
        updateLevel()
        
        val question = questionGenerator.generate(gameState.questionNumber)
        gameState.currentQuestion = question
        
        gameState.player1.resetForNextQuestion()
        gameState.player2.resetForNextQuestion()
        
        callback.onNewQuestion(question, gameState.questionNumber, gameState.currentLevel)
        startTimer(question.timeLimit)
    }
    
    private fun updateLevel() {
        val oldLevel = gameState.currentLevel
        gameState.currentLevel = when {
            gameState.questionNumber >= 10 -> 4
            gameState.questionNumber >= 7 -> 3
            gameState.questionNumber >= 4 -> 2
            else -> 1
        }
        
        if (gameState.currentLevel > oldLevel) {
            callback.onLevelUp(gameState.currentLevel)
        }
    }
    
    private fun startTimer(seconds: Int) {
        timer?.cancel()
        timeRemaining = seconds
        
        timer = object : CountDownTimer((seconds * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = (millisUntilFinished / 1000).toInt() + 1
                callback.onTimerTick(timeRemaining)
                
                if (timeRemaining == 5) {
                    callback.onTimerWarning()
                }
            }
            
            override fun onFinish() {
                onTimeout()
            }
        }.start()
    }
    
    fun onPlayerAnswer(player: Player, answer: Int) {
        if (player.hasAnswered) return // First-tap lock
        
        player.hasAnswered = true
        player.selectedAnswer = answer
        
        val isCorrect = answer == gameState.currentQuestion?.correctAnswer
        
        if (isCorrect) {
            player.score += 2
            player.correctAnswers++
            callback.onCorrectAnswer(player)
        } else {
            player.wrongAnswers++
            callback.onWrongAnswer(player)
        }
        
        callback.onScoreUpdate(gameState.player1.score, gameState.player2.score)
        
        // Check if both players answered
        if (gameState.player1.hasAnswered && gameState.player2.hasAnswered) {
            timer?.cancel()
            // Delay before next question
            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                nextQuestion()
            }, 1500)
        }
    }
    
    private fun onTimeout() {
        if (!gameState.player1.hasAnswered && !gameState.player2.hasAnswered) {
            gameState.player1.score -= 1
            gameState.player2.score -= 1
            callback.onBothTimeout()
            callback.onScoreUpdate(gameState.player1.score, gameState.player2.score)
        }
        
        // Delay before next question
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            nextQuestion()
        }, 1500)
    }
    
    fun cleanup() {
        timer?.cancel()
        timer = null
    }
}