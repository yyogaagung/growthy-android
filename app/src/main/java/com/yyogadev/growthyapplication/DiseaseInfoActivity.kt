package com.yyogadev.growthyapplication

import android.content.Intent
import android.os.Build.ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.yyogadev.growthyapplication.databinding.ActivityDiseaseInfoBinding
import com.yyogadev.growthyapplication.retrofit.response.OneDiseaseData

class DiseaseInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiseaseInfoBinding
    private lateinit var oneDiseaseViewModel: OneDiseaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        oneDiseaseViewModel = ViewModelProvider(this, OneDiseaseViewModelFactory(intent.getIntExtra(
            ID,0)))
            .get(OneDiseaseViewModel::class.java)

        oneDiseaseViewModel.penyakit.observe(this) {
                items -> setPenyakitData(items)
        }

    }

    private fun setPenyakitData(items: OneDiseaseData) {
        Glide.with(this)
            .load(items.diseaseImgNormal)
            .apply { centerCrop() }
            .into(binding.imgPenyakit3)

        binding.txtNamaTradional.text = items.diseaseLocalName
        binding.txtNamaLatin.text = items.scienticName
        binding.txtPlantDesc.text = items.diseaseDesc
        binding.txGejala.text = items.symptoms
        binding.txtKesulitan.text = items.prevention
        binding.txtPenyebab.text = items.causes
        binding.txtPenemuan.text = items.potention
        binding.txtpengobatan.text = items.treatment
    }

    companion object {
        const val ID = "id"
    }

}