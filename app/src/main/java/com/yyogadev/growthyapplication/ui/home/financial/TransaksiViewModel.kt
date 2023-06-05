package com.yyogadev.growthyapplication.ui.home.financial

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yyogadev.growthyapplication.retrofit.ApiConfig
import com.yyogadev.growthyapplication.retrofit.response.DataItem
import com.yyogadev.growthyapplication.retrofit.response.FinancialResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransaksiViewModel(token :String) : ViewModel() {

    private val _listTransaksi = MutableLiveData<List<DataItem>>()
    val listTransaksi: LiveData<List<DataItem>> = _listTransaksi

    private val _balance = MutableLiveData<Int>()
    val balace: LiveData<Int> = _balance

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading



    init {
        loadAllTransaksi(token)
    }

    companion object{
        private const val TAG = "TransaksiViewModel"
    }

    fun loadAllTransaksi(token: String){
        _isLoading.value = true
        val client = ApiConfig.getAuthApiService(token).getTransaksies()
        client.enqueue(object : Callback<FinancialResponse> {
            override fun onResponse(
                call: Call<FinancialResponse>,
                response: Response<FinancialResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listTransaksi.value = responseBody.data
                        _balance.value = responseBody.balance
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<FinancialResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}