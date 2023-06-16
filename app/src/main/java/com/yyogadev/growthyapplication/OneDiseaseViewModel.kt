package com.yyogadev.growthyapplication

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yyogadev.growthyapplication.retrofit.ApiConfig
import com.yyogadev.growthyapplication.retrofit.response.OneDiseaseData
import com.yyogadev.growthyapplication.retrofit.response.OneDiseaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OneDiseaseViewModel (id : Int) : ViewModel() {
    private val _penyakit = MutableLiveData<OneDiseaseData>()
    val penyakit: LiveData<OneDiseaseData> = _penyakit

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadOnePenyakit(id)
    }

    fun loadOnePenyakit(id: Int){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getOnePenyakit(id)
        client.enqueue(object : Callback<OneDiseaseResponse> {
            @SuppressLint("LongLogTag")
            override fun onResponse(
                call: Call<OneDiseaseResponse>,
                response: Response<OneDiseaseResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _penyakit.value = responseBody.data
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<OneDiseaseResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        private const val TAG = "OneDiseaseViewModel"
    }
}