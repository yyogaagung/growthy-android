package com.yyogadev.growthyapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OneDiseaseViewModelFactory (private val id: Int) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OneDiseaseViewModel::class.java)) {
            return OneDiseaseViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}