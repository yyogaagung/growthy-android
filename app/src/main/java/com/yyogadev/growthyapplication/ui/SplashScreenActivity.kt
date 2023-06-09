package com.yyogadev.growthyapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.yyogadev.growthyapplication.BriefTourActivity
import com.yyogadev.growthyapplication.R

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        Handler().postDelayed({
            startActivity(Intent(this, BriefTourActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}