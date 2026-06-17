package com.mathfun.game.model

data class Level(
    val number: Int,
    val minNum: Int,
    val maxNum: Int,
    val maxResult: Int,
    val timeLimit: Int
) {
    companion object {
        fun getLevel(questionNumber: Int): Level {
            return when {
                questionNumber >= 10 -> Level(4, 1, 12, 12, 8)
                questionNumber >= 7 -> Level(3, 1, 10, 10, 10)
                questionNumber >= 4 -> Level(2, 1, 10, 10, 12)
                else -> Level(1, 1, 5, 5, 15)
            }
        }
        
        val ALL_LEVELS = listOf(
            Level(1, 1, 5, 5, 15),
            Level(2, 1, 10, 10, 12),
            Level(3, 1, 10, 10, 10),
            Level(4, 1, 12, 12, 8)
        )
    }
    
    val displayName: String
        get() = "Level $number"
}