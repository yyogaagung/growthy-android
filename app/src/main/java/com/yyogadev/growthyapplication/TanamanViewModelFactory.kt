package com.yyogadev.growthyapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TanamanViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TanamanViewModel::class.java)) {
            return TanamanViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}