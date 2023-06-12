package com.yyogadev.growthyapplication.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yyogadev.growthyapplication.retrofit.ApiConfig
import com.yyogadev.growthyapplication.retrofit.response.DetailProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(token: String, id:Int) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Profile Fragment"
    }
    val text: LiveData<String> = _text

    private val _profile = MutableLiveData<DetailProfileResponse>()
    var profile: LiveData<DetailProfileResponse> = _profile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "StoriesDetailViewModel"
    }

    init {
        loadProfile(token, id)
    }

    fun loadProfile(token: String, id: Int){
        _isLoading.value = true
        val client = ApiConfig.getAuthApiService(token).getProfile(id)
        client.enqueue(object : Callback<DetailProfileResponse> {
            override fun onResponse(
                call: Call<DetailProfileResponse>,
                response: Response<DetailProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _profile.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailProfileResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}