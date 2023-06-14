package com.yyogadev.growthyapplication.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yyogadev.growthyapplication.R
import com.yyogadev.growthyapplication.databinding.ActivityLoginBinding
import com.yyogadev.growthyapplication.retrofit.ApiConfig
import com.yyogadev.growthyapplication.retrofit.response.LoginResponse
import com.yyogadev.growthyapplication.ui.*
import com.yyogadev.growthyapplication.ui.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var tokenViewModel: TokenViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        checkToken()

        binding.btnToDaftar.setOnClickListener { onClickToSignUp() }
        binding.btnLogin.setOnClickListener { onClickLogin(
            binding.edtEmail.text.toString(),
            binding.edtPassword.text.toString()
        ) }
    }

    private fun onClickToSignUp() {
        val i = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(i)
    }

    private fun onClickLogin(email:String, password:String){
        showLoading(true)
        val client = ApiConfig.getApiService().postLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                showLoading(false)
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    tokenViewModel.saveToken(responseBody.token)
                    tokenViewModel.saveName(responseBody.name)
                    val i = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(i)
                    finish();
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Email atau password salah",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@LoginActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    override fun onBackPressed() {
        // Disable going back to the LoginActivity
        moveTaskToBack(true)
    }

    fun checkToken(){
        val pref = SettingPreferences.getInstance(dataStore)

        tokenViewModel = ViewModelProvider(this, TokenViewModelFactory(pref)).get(
            TokenViewModel::class.java
        )

        tokenViewModel.getToken().observe(this) { token: String->
            if (token.isNotEmpty()) {
                val i = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(i)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}