package com.mathfun.game.game

import com.mathfun.game.model.Level
import com.mathfun.game.model.Operation
import com.mathfun.game.model.Question
import kotlin.random.Random

class QuestionGenerator {
    
    fun generate(questionNumber: Int): Question {
        val level = Level.getLevel(questionNumber)
        return generateForLevel(level)
    }
    
    private fun generateForLevel(level: Level): Question {
        val operation = if (Random.nextBoolean()) Operation.ADD else Operation.SUBTRACT
        
        return when (operation) {
            Operation.ADD -> generateAddition(level)
            Operation.SUBTRACT -> generateSubtraction(level)
        }
    }
    
    private fun generateAddition(level: Level): Question {
        var num1: Int
        var num2: Int
        var result: Int
        
        do {
            num1 = Random.nextInt(level.minNum, level.maxNum + 1)
            num2 = Random.nextInt(level.minNum, level.maxNum + 1)
            result = num1 + num2
        } while (result > level.maxResult)
        
        val choices = generateChoices(result, level.minNum, level.maxResult)
        
        return Question(
            num1 = num1,
            num2 = num2,
            operation = Operation.ADD,
            correctAnswer = result,
            choices = choices.shuffled(),
            level = level.number,
            timeLimit = level.timeLimit
        )
    }
    
    private fun generateSubtraction(level: Level): Question {
        var num1: Int
        var num2: Int
        var result: Int
        
        do {
            num1 = Random.nextInt(level.minNum, level.maxResult + 1)
            num2 = Random.nextInt(level.minNum, num1 + 1) // Ensure positive result
            result = num1 - num2
        } while (result < 0 || result > level.maxResult)
        
        val choices = generateChoices(result, level.minNum, level.maxResult)
        
        return Question(
            num1 = num1,
            num2 = num2,
            operation = Operation.SUBTRACT,
            correctAnswer = result,
            choices = choices.shuffled(),
            level = level.number,
            timeLimit = level.timeLimit
        )
    }
    
    private fun generateChoices(correct: Int, minNum: Int, maxResult: Int): List<Int> {
        val choices = mutableSetOf(correct)
        
        val possibleDistractors = listOf(
            correct - 1,
            correct - 2,
            correct + 1,
            correct + 2
        ).filter { it in minNum..maxResult && it != correct }
        
        while (choices.size < 3 && possibleDistractors.isNotEmpty()) {
            val distractor = possibleDistractors.random()
            choices.add(distractor)
        }
        
        // Fallback if we don't have enough valid distractors
        while (choices.size < 3) {
            val random = Random.nextInt(minNum, maxResult + 1)
            if (random != correct) {
                choices.add(random)
            }
        }
        
        return choices.take(3)
    }
}