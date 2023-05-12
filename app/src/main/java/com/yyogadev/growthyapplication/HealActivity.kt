package com.yyogadev.growthyapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yyogadev.growthyapplication.databinding.ActivityHealBinding

class HealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnKembali.setOnClickListener {
            kembali()
        }
        binding.arrowBack.setOnClickListener {
            kembali()
        }
    }

    private fun kembali() {
        val intent = Intent(this, PredictionActivity::class.java)
        startActivity(intent)
    }
}
