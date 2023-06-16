package com.yyogadev.growthyapplication.ui.home.financial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OneFinancialViewModelFactory(private val token: String,private val id:Int) : ViewModelProvider.NewInstanceFactory()  {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OneFinancialViewModel::class.java)) {
            return OneFinancialViewModel(token, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}