package com.yyogadev.growthyapplication.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yyogadev.growthyapplication.retrofit.response.DetailProfileResponse

class EditProfileViewModel(token: String, id:Int) : ViewModel() {
    private val _profile = MutableLiveData<DetailProfileResponse>()
    var profile: LiveData<DetailProfileResponse> = _profile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
}