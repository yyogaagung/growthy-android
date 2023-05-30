package com.yyogadev.growthyapplication.ui.home.deteksi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yyogadev.growthyapplication.databinding.ActivityLoadingBinding

class LoadingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()
    }

    private fun playAnimation() {

    }
}