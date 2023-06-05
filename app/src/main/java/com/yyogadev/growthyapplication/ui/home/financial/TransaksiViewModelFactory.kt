package com.yyogadev.growthyapplication.ui.home.financial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TransaksiViewModelFactory(private val token: String) : ViewModelProvider.NewInstanceFactory()  {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransaksiViewModel::class.java)) {
            return TransaksiViewModel(token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}