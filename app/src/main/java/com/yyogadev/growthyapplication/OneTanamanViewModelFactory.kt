package com.yyogadev.growthyapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OneTanamanViewModelFactory (private val id: Int) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OneTanamanViewModel::class.java)) {
            return OneTanamanViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}