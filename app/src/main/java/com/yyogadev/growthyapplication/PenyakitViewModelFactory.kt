package com.yyogadev.growthyapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PenyakitViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PenyakitViewModel::class.java)) {
            return PenyakitViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}