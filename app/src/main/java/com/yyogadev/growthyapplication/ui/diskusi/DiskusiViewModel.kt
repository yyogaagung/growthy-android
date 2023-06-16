package com.yyogadev.growthyapplication.ui.diskusi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiskusiViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Fitur Diskusi akan segera tiba! Nantikan ya!"
    }
    val text: LiveData<String> = _text
}