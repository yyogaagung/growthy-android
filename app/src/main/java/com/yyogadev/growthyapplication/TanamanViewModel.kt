package com.yyogadev.growthyapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yyogadev.growthyapplication.retrofit.ApiConfig
import com.yyogadev.growthyapplication.retrofit.response.TanamanItem
import com.yyogadev.growthyapplication.retrofit.response.TanamanResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TanamanViewModel : ViewModel() {
    private val _tanamans = MutableLiveData<List<TanamanItem>>()
    val tanamans: LiveData<List<TanamanItem>> = _tanamans

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadAllStories()
    }

    companion object{
        private const val TAG = "TanamanViewModel"
    }

    fun loadAllStories(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getTanamans()
        client.enqueue(object : Callback<TanamanResponse> {
            override fun onResponse(
                call: Call<TanamanResponse>,
                response: Response<TanamanResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _tanamans.value = responseBody.data
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<TanamanResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}