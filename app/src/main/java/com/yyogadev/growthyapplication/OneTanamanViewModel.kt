package com.yyogadev.growthyapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yyogadev.growthyapplication.retrofit.ApiConfig
import com.yyogadev.growthyapplication.retrofit.response.Data
import com.yyogadev.growthyapplication.retrofit.response.OneFinancialResponse
import com.yyogadev.growthyapplication.retrofit.response.OneTanamanData
import com.yyogadev.growthyapplication.retrofit.response.OneTanamanResponse
import com.yyogadev.growthyapplication.ui.home.financial.OneFinancialViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OneTanamanViewModel(id : Int) : ViewModel() {
    private val _tanaman = MutableLiveData<OneTanamanData>()
    val tanaman: LiveData<OneTanamanData> = _tanaman

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadOneTanaman(id)
    }


    fun loadOneTanaman(id: Int){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getOneTanaman(id)
        client.enqueue(object : Callback<OneTanamanResponse> {
            override fun onResponse(
                call: Call<OneTanamanResponse>,
                response: Response<OneTanamanResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _tanaman.value = responseBody.data
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<OneTanamanResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        private const val TAG = "OneTanamanViewModel"
    }
}