package com.yyogadev.growthyapplication.ui.home.deteksi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yyogadev.growthyapplication.databinding.ActivityPredictionBinding
import com.yyogadev.growthyapplication.ui.MainActivity

class PredictionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPredictionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPenyembuhan.setOnClickListener {
            startFiturPenyembuhan()
        }

    }

    private fun startFiturPenyembuhan() {
        val intent = Intent(this, HealActivity::class.java)
        startActivity(intent)
    }

    private fun kembali() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}