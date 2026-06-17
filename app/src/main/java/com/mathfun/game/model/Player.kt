package com.mathfun.game.model

import android.graphics.Color

data class Player(
    val id: Int,
    var name: String,
    var color: Int = Color.parseColor("#FF6B6B"),
    var score: Int = 5,
    var correctAnswers: Int = 0,
    var wrongAnswers: Int = 0,
    var hasAnswered: Boolean = false,
    var selectedAnswer: Int? = null
) {
    fun reset() {
        score = 5
        correctAnswers = 0
        wrongAnswers = 0
        hasAnswered = false
        selectedAnswer = null
    }
    
    fun resetForNextQuestion() {
        hasAnswered = false
        selectedAnswer = null
    }
    
    val isWinner: Boolean
        get() = score >= 15
    
    val hasLost: Boolean
        get() = score <= 0
}