package com.mathfun.game.model

enum class Operation {
    ADD, SUBTRACT;
    
    override fun toString(): String = when (this) {
        ADD -> "+"
        SUBTRACT -> "-"
    }
}

data class Question(
    val num1: Int,
    val num2: Int,
    val operation: Operation,
    val correctAnswer: Int,
    val choices: List<Int>,
    val level: Int,
    val timeLimit: Int
) {
    val questionText: String
        get() = "$num1 $operation $num2"
    
    init {
        require(choices.size == 3) { "Question must have exactly 3 choices" }
        require(correctAnswer in choices) { "Correct answer must be in choices" }
    }
}