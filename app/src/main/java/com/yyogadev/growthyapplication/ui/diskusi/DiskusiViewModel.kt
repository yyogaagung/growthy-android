package com.yyogadev.growthyapplication.ui.diskusi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiskusiViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Fitur Diskusi akan segera datang"
    }
    val text: LiveData<String> = _text
}