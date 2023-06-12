package com.yyogadev.growthyapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yyogadev.growthyapplication.databinding.ActivityPlantInfomationBinding

class PlantInfomationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantInfomationBinding

    companion object {
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantInfomationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTipsBudidaya.setOnClickListener {
            startTipsBudidaya()
        }
    }

    private fun startTipsBudidaya() {
        val intent = Intent(this, CultivationActivity::class.java)
        startActivity(intent)
    }
}