package com.mathfun.game.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mathfun.game.R

class MenuActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        
        findViewById<Button>(R.id.btn_start_game).setOnClickListener {
            startActivity(Intent(this, SetupActivity::class.java))
        }
    }
}