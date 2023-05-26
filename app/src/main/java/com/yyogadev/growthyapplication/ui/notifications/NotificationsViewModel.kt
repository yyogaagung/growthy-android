package com.yyogadev.growthyapplication.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
            value = "Fitur Kebun Sendiri akan segera datang"
    }
    val text: LiveData<String> = _text
}