package com.mathfun.game.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.mathfun.game.R

class SplashActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        // Navigate to MenuActivity after 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }, 2000)
    }
}