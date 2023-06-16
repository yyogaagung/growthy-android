package com.yyogadev.growthyapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.yyogadev.growthyapplication.databinding.ActivityCultivationBinding
import com.yyogadev.growthyapplication.databinding.ActivityPlantInfomationBinding
import com.yyogadev.growthyapplication.retrofit.response.OneTanamanData

class CultivationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCultivationBinding
    private lateinit var oneTanamanViewModel: OneTanamanViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCultivationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        oneTanamanViewModel = ViewModelProvider(this, OneTanamanViewModelFactory(intent.getIntExtra(
            PlantInfomationActivity.ID,0)))
            .get(OneTanamanViewModel::class.java)

        oneTanamanViewModel.tanaman.observe(this) {
                items -> setTransaksiData(items)
        }
    }

    private fun setTransaksiData(data: OneTanamanData) {
        binding.txtPenyakitSering.text =  data.plantDisease
        binding.txtTindakPencegahan.text = data.prevention
        binding.txtcaraTanam.text = data.cultivation

    }

    companion object {
        const val ID = "id"
    }
}