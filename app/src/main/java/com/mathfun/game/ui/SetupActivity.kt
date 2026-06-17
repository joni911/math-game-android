package com.mathfun.game.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mathfun.game.R
import com.mathfun.game.model.Player

class SetupActivity : AppCompatActivity() {
    
    private lateinit var player1NameInput: EditText
    private lateinit var player2NameInput: EditText
    private lateinit var startButton: Button
    
    private var player1Color: Int = Color.parseColor("#FF6B6B")
    private var player2Color: Int = Color.parseColor("#4ECDC4")
    
    private val availableColors = listOf(
        "#FF6B6B", "#4ECDC4", "#FFD93D", "#95E1D3", "#A8E6CF"
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)
        
        player1NameInput = findViewById(R.id.player1_name)
        player2NameInput = findViewById(R.id.player2_name)
        startButton = findViewById(R.id.btn_start)
        
        setupTextWatchers()
        setupColorPickers()
        setupStartButton()
    }
    
    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateStartButtonState()
            }
        }
        
        player1NameInput.addTextChangedListener(textWatcher)
        player2NameInput.addTextChangedListener(textWatcher)
    }
    
    private fun setupColorPickers() {
        // Setup color buttons for player 1
        setupColorButton(R.id.player1_color1, 0, true)
        setupColorButton(R.id.player1_color2, 1, true)
        setupColorButton(R.id.player1_color3, 2, true)
        setupColorButton(R.id.player1_color4, 3, true)
        setupColorButton(R.id.player1_color5, 4, true)
        
        // Setup color buttons for player 2
        setupColorButton(R.id.player2_color1, 0, false)
        setupColorButton(R.id.player2_color2, 1, false)
        setupColorButton(R.id.player2_color3, 2, false)
        setupColorButton(R.id.player2_color4, 3, false)
        setupColorButton(R.id.player2_color5, 4, false)
        
        // Select default colors
        findViewById<View>(R.id.player1_color1).performClick()
        findViewById<View>(R.id.player2_color2).performClick()
    }
    
    private fun setupColorButton(buttonId: Int, colorIndex: Int, isPlayer1: Boolean) {
        val button = findViewById<View>(buttonId)
        val color = Color.parseColor(availableColors[colorIndex])
        button.setBackgroundColor(color)
        
        button.setOnClickListener {
            if (isPlayer1) {
                player1Color = color
                clearPlayer1ColorSelection()
            } else {
                player2Color = color
                clearPlayer2ColorSelection()
            }
            button.isSelected = true
        }
    }
    
    private fun clearPlayer1ColorSelection() {
        findViewById<View>(R.id.player1_color1).isSelected = false
        findViewById<View>(R.id.player1_color2).isSelected = false
        findViewById<View>(R.id.player1_color3).isSelected = false
        findViewById<View>(R.id.player1_color4).isSelected = false
        findViewById<View>(R.id.player1_color5).isSelected = false
    }
    
    private fun clearPlayer2ColorSelection() {
        findViewById<View>(R.id.player2_color1).isSelected = false
        findViewById<View>(R.id.player2_color2).isSelected = false
        findViewById<View>(R.id.player2_color3).isSelected = false
        findViewById<View>(R.id.player2_color4).isSelected = false
        findViewById<View>(R.id.player2_color5).isSelected = false
    }
    
    private fun setupStartButton() {
        startButton.isEnabled = false
        startButton.setOnClickListener {
            startGame()
        }
    }
    
    private fun updateStartButtonState() {
        val player1Name = player1NameInput.text.toString().trim()
        val player2Name = player2NameInput.text.toString().trim()
        startButton.isEnabled = player1Name.isNotEmpty() && player2Name.isNotEmpty()
    }
    
    private fun startGame() {
        val intent = Intent(this, GameActivity::class.java).apply {
            putExtra("player1_name", player1NameInput.text.toString().trim())
            putExtra("player1_color", player1Color)
            putExtra("player2_name", player2NameInput.text.toString().trim())
            putExtra("player2_color", player2Color)
        }
        startActivity(intent)
        finish()
    }
}