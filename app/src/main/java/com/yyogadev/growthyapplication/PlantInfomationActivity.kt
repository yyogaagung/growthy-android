package com.yyogadev.growthyapplication

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.yyogadev.growthyapplication.databinding.ActivityPlantInfomationBinding
import com.yyogadev.growthyapplication.retrofit.response.Data
import com.yyogadev.growthyapplication.retrofit.response.OneTanamanData
import com.yyogadev.growthyapplication.ui.home.financial.OneFinancialViewModel
import com.yyogadev.growthyapplication.ui.home.financial.OneFinancialViewModelFactory
import com.yyogadev.growthyapplication.ui.home.financial.PemasukanAddUpdateActivity

class PlantInfomationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantInfomationBinding
    private lateinit var oneTanamanViewModel: OneTanamanViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantInfomationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        oneTanamanViewModel = ViewModelProvider(this, OneTanamanViewModelFactory(intent.getIntExtra(
            ID,0)))
            .get(OneTanamanViewModel::class.java)

        oneTanamanViewModel.tanaman.observe(this) {
                items -> setTransaksiData(items)
        }


        binding.btnTipsBudidaya.setOnClickListener {
            startTipsBudidaya()
        }
    }

    private fun setTransaksiData(data: OneTanamanData) {
        Glide.with(this)
            .load(data.plantImgNormal)
            .apply { centerCrop() }
            .into(binding.imgPenyakit3)

        binding.txtNamaTradional.text = data.localName
        binding.txtNamaLatin.text = data.species
        binding.txtNamaKeluarga.text = data.genus
        binding.txtPlantDesc.text = data.plantDesc
        binding.txtHarga.text = data.price.toString()  + " per tanaman"
        binding.txtKesulitan.text = data.difficulty

    }

    private fun startTipsBudidaya() {
        val moveActivityWithObjectIntent = Intent(this@PlantInfomationActivity, CultivationActivity::class.java)
        moveActivityWithObjectIntent.putExtra(CultivationActivity.ID,   intent.getIntExtra(
            ID,0))

        startActivity(moveActivityWithObjectIntent)
    }

    companion object {
        const val ID = "id"
    }
}