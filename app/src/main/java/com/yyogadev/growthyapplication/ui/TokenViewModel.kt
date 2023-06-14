package com.yyogadev.growthyapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TokenViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getToken(): LiveData<String> {
        return pref.getToken().asLiveData()
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }

    fun getName(): LiveData<String> {
        return pref.getName().asLiveData()
    }

    fun saveName(name: String) {
        viewModelScope.launch {
            pref.saveName(name)
        }
    }
}