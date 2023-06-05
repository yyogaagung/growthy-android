package com.yyogadev.growthyapplication.ui.home.financial

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yyogadev.growthyapplication.retrofit.ApiConfig
import com.yyogadev.growthyapplication.retrofit.response.Data
import com.yyogadev.growthyapplication.retrofit.response.OneFinancialResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OneFinancialViewModel(token :String, id : Int) : ViewModel() {
    private val _transaksi = MutableLiveData<Data?>()
    val transaksi: LiveData<Data?> = _transaksi


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadOneTransaksi(token, id)
    }

    companion object{
        private const val TAG = "TransaksiViewModel"
    }

    fun loadOneTransaksi(token: String, id: Int){
        _isLoading.value = true
        val client = ApiConfig.getAuthApiService(token).getOneFinancial(id)
        client.enqueue(object : Callback<OneFinancialResponse> {
            override fun onResponse(
                call: Call<OneFinancialResponse>,
                response: Response<OneFinancialResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _transaksi.value = responseBody.data
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<OneFinancialResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}