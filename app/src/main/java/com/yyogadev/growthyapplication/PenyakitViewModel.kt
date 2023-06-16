package com.yyogadev.growthyapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yyogadev.growthyapplication.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PenyakitViewModel : ViewModel() {
    private val _penyakits = MutableLiveData<List<DiseaseItem>>()
    val penyakits: LiveData<List<DiseaseItem>> = _penyakits

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadAllStories()
    }

    companion object{
        private const val TAG = "PenyakitViewModel"
    }

    fun loadAllStories(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getPenyakits()
        client.enqueue(object : Callback<DiseaseResponse> {
            override fun onResponse(
                call: Call<DiseaseResponse>,
                response: Response<DiseaseResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _penyakits.value = responseBody.data
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DiseaseResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
