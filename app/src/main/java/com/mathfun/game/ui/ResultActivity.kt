package com.mathfun.game.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mathfun.game.R

class ResultActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        
        displayResults()
        setupButtons()
    }
    
    private fun displayResults() {
        val winnerId = intent.getIntExtra("winner_id", 0)
        
        // Player 1 data
        val p1Name = intent.getStringExtra("player1_name") ?: "Player 1"
        val p1Score = intent.getIntExtra("player1_score", 0)
        val p1Correct = intent.getIntExtra("player1_correct", 0)
        val p1Wrong = intent.getIntExtra("player1_wrong", 0)
        
        // Player 2 data
        val p2Name = intent.getStringExtra("player2_name") ?: "Player 2"
        val p2Score = intent.getIntExtra("player2_score", 0)
        val p2Correct = intent.getIntExtra("player2_correct", 0)
        val p2Wrong = intent.getIntExtra("player2_wrong", 0)
        
        // Title
        val title = findViewById<TextView>(R.id.result_title)
        title.text = when (winnerId) {
            0 -> "🤝 Seri! 🤝"
            else -> "🎉 Selesai! 🎉"
        }
        
        // Player 1 card
        findViewById<TextView>(R.id.p1_name).text = p1Name
        findViewById<TextView>(R.id.p1_score).text = "Skor Akhir: $p1Score"
        findViewById<TextView>(R.id.p1_stats).text = "✅ Benar: $p1Correct\n❌ Salah: $p1Wrong"
        findViewById<TextView>(R.id.p1_encouragement).text = if (winnerId == 1) {
            "🏆 Kamu Juara! Hebat sekali!"
        } else {
            "👍 Bagus! Coba lagi ya!"
        }
        
        // Player 2 card
        findViewById<TextView>(R.id.p2_name).text = p2Name
        findViewById<TextView>(R.id.p2_score).text = "Skor Akhir: $p2Score"
        findViewById<TextView>(R.id.p2_stats).text = "✅ Benar: $p2Correct\n❌ Salah: $p2Wrong"
        findViewById<TextView>(R.id.p2_encouragement).text = if (winnerId == 2) {
            "🏆 Kamu Juara! Hebat sekali!"
        } else {
            "👍 Bagus! Coba lagi ya!"
        }
    }
    
    private fun setupButtons() {
        findViewById<Button>(R.id.btn_play_again).setOnClickListener {
            startActivity(Intent(this, SetupActivity::class.java))
            finish()
        }
        
        findViewById<Button>(R.id.btn_exit).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }
}