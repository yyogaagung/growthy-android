package com.yyogadev.growthyapplication.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Fitur Diskusi akan segera datang"
    }
    val text: LiveData<String> = _text
}