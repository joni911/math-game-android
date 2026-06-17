package com.mathfun.game.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mathfun.game.R
import com.mathfun.game.game.GameController
import com.mathfun.game.model.GameState
import com.mathfun.game.model.Player
import com.mathfun.game.model.Question

class GameActivity : AppCompatActivity(), GameController.GameCallback {
    
    private lateinit var gameController: GameController
    private lateinit var gameState: GameState
    
    // Player 1 views
    private lateinit var player1Name: TextView
    private lateinit var player1Score: TextView
    private lateinit var player1Avatar: View
    private lateinit var player1Btn1: Button
    private lateinit var player1Btn2: Button
    private lateinit var player1Btn3: Button
    
    // Player 2 views
    private lateinit var player2Name: TextView
    private lateinit var player2Score: TextView
    private lateinit var player2Avatar: View
    private lateinit var player2Btn1: Button
    private lateinit var player2Btn2: Button
    private lateinit var player2Btn3: Button
    
    // Question views
    private lateinit var levelInfo: TextView
    private lateinit var questionText: TextView
    private lateinit var timerText: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        
        initializePlayers()
        initializeViews()
        setupGame()
    }
    
    private fun initializePlayers() {
        val player1 = Player(
            id = 1,
            name = intent.getStringExtra("player1_name") ?: "Player 1",
            color = intent.getIntExtra("player1_color", Color.parseColor("#FF6B6B"))
        )
        
        val player2 = Player(
            id = 2,
            name = intent.getStringExtra("player2_name") ?: "Player 2",
            color = intent.getIntExtra("player2_color", Color.parseColor("#4ECDC4"))
        )
        
        gameState = GameState(player1, player2, null)
    }
    
    private fun initializeViews() {
        // Player 1 views
        player1Name = findViewById(R.id.player1_name)
        player1Score = findViewById(R.id.player1_score)
        player1Avatar = findViewById(R.id.player1_avatar)
        player1Btn1 = findViewById(R.id.player1_btn1)
        player1Btn2 = findViewById(R.id.player1_btn2)
        player1Btn3 = findViewById(R.id.player1_btn3)
        
        // Player 2 views
        player2Name = findViewById(R.id.player2_name)
        player2Score = findViewById(R.id.player2_score)
        player2Avatar = findViewById(R.id.player2_avatar)
        player2Btn1 = findViewById(R.id.player2_btn1)
        player2Btn2 = findViewById(R.id.player2_btn2)
        player2Btn3 = findViewById(R.id.player2_btn3)
        
        // Question views
        levelInfo = findViewById(R.id.level_info)
        questionText = findViewById(R.id.question_text)
        timerText = findViewById(R.id.timer_text)
        
        // Setup player UI
        player1Name.text = gameState.player1.name
        player1Avatar.setBackgroundColor(gameState.player1.color)
        player2Name.text = gameState.player2.name
        player2Avatar.setBackgroundColor(gameState.player2.color)
        
        updateScores()
    }
    
    private fun setupGame() {
        gameController = GameController(gameState, this)
        gameController.startGame()
    }
    
    private fun setupAnswerButtons(question: Question) {
        val player1Buttons = listOf(player1Btn1, player1Btn2, player1Btn3)
        val player2Buttons = listOf(player2Btn1, player2Btn2, player2Btn3)
        
        question.choices.forEachIndexed { index, answer ->
            player1Buttons[index].apply {
                text = answer.toString()
                isEnabled = true
                setBackgroundColor(Color.WHITE)
                setOnClickListener { onAnswerClicked(gameState.player1, answer, this) }
            }
            
            player2Buttons[index].apply {
                text = answer.toString()
                isEnabled = true
                setBackgroundColor(Color.WHITE)
                setOnClickListener { onAnswerClicked(gameState.player2, answer, this) }
            }
        }
    }
    
    private fun onAnswerClicked(player: Player, answer: Int, button: Button) {
        gameController.onPlayerAnswer(player, answer)
        
        // Lock all buttons for this player
        val buttons = if (player.id == 1) {
            listOf(player1Btn1, player1Btn2, player1Btn3)
        } else {
            listOf(player2Btn1, player2Btn2, player2Btn3)
        }
        buttons.forEach { it.isEnabled = false }
    }
    
    private fun updateScores() {
        player1Score.text = gameState.player1.score.toString()
        player2Score.text = gameState.player2.score.toString()
    }
    
    // GameController.GameCallback implementations
    
    override fun onNewQuestion(question: Question, questionNumber: Int, level: Int) {
        runOnUiThread {
            levelInfo.text = "Level $level • Soal $questionNumber"
            questionText.text = question.questionText
            setupAnswerButtons(question)
        }
    }
    
    override fun onTimerTick(secondsRemaining: Int) {
        runOnUiThread {
            timerText.text = "⏱️ $secondsRemaining"
            
            if (secondsRemaining <= 5) {
                timerText.setTextColor(Color.RED)
            } else {
                timerText.setTextColor(ContextCompat.getColor(this, R.color.timer_normal))
            }
        }
    }
    
    override fun onTimerWarning() {
        // Play warning sound
    }
    
    override fun onCorrectAnswer(player: Player) {
        runOnUiThread {
            val buttons = if (player.id == 1) {
                listOf(player1Btn1, player1Btn2, player1Btn3)
            } else {
                listOf(player2Btn1, player2Btn2, player2Btn3)
            }
            
            buttons.forEach { button ->
                if (button.text.toString().toInt() == player.selectedAnswer) {
                    button.setBackgroundColor(Color.GREEN)
                }
            }
        }
    }
    
    override fun onWrongAnswer(player: Player) {
        runOnUiThread {
            val buttons = if (player.id == 1) {
                listOf(player1Btn1, player1Btn2, player1Btn3)
            } else {
                listOf(player2Btn1, player2Btn2, player2Btn3)
            }
            
            buttons.forEach { button ->
                if (button.text.toString().toInt() == player.selectedAnswer) {
                    button.setBackgroundColor(Color.parseColor("#F56565"))
                }
            }
        }
    }
    
    override fun onBothTimeout() {
        // Show timeout message
    }
    
    override fun onLevelUp(level: Int) {
        // Show level up animation
    }
    
    override fun onGameOver(winner: Player?, player1: Player, player2: Player) {
        runOnUiThread {
            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("winner_id", winner?.id ?: 0)
                putExtra("player1_name", player1.name)
                putExtra("player1_score", player1.score)
                putExtra("player1_correct", player1.correctAnswers)
                putExtra("player1_wrong", player1.wrongAnswers)
                putExtra("player1_color", player1.color)
                putExtra("player2_name", player2.name)
                putExtra("player2_score", player2.score)
                putExtra("player2_correct", player2.correctAnswers)
                putExtra("player2_wrong", player2.wrongAnswers)
                putExtra("player2_color", player2.color)
            }
            startActivity(intent)
            finish()
        }
    }
    
    override fun onScoreUpdate(player1Score: Int, player2Score: Int) {
        runOnUiThread {
            updateScores()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        gameController.cleanup()
    }
}