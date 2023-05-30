package com.yyogadev.growthyapplication.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.yyogadev.growthyapplication.R
import com.yyogadev.growthyapplication.customview.EmailEditText
import com.yyogadev.growthyapplication.customview.MyButton
import com.yyogadev.growthyapplication.customview.MyEditText
import com.yyogadev.growthyapplication.customview.MyPasswordText
import com.yyogadev.growthyapplication.databinding.ActivityRegisterBinding
import com.yyogadev.growthyapplication.retrofit.ApiConfig
import com.yyogadev.growthyapplication.retrofit.response.SignUpResponse
import com.yyogadev.growthyapplication.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setMyButtonEnable()
        validasiInputan()


        binding.txtMasuk.setOnClickListener { onClickToSignIn() }

        binding.btnDaftar.setOnClickListener{onClickSignUp(
            binding.edtName.text.toString(),
            binding.edtEmail.text.toString(),
            binding.edtPassword.text.toString()
        )}
    }

    private fun validasiInputan(){
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
                if (!binding.edtEmail.isValid()){
                    binding.edtEmail.error = "Pastikan inputan berformat email"
                }
            }
        })
        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        binding.edtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun setMyButtonEnable() {
        val result = binding.edtEmail.text
        val result2 = binding.edtPassword.text
        val result3 = binding.edtName.text
        binding.btnDaftar.isEnabled = result != null && result.toString().isNotEmpty() &&
                result2 != null && result2.isNotEmpty() && result2.length >= 8 &&
                result3 != null && result3.toString().isNotEmpty() &&
                binding.edtEmail.isValid()
    }

    private fun onClickToSignIn() {
        val i = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(i)
    }

    private fun onClickSignUp(name: String, email:String, pasword:String){
        showLoading(true)
        val client = ApiConfig.getApiService().postRegister(name, email, pasword)
        client.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody  = response.body()
                    Toast.makeText(this@RegisterActivity, responseBody?.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    if(response.code().toString().equals("409")){
                        Toast.makeText(this@RegisterActivity, "Akun sudah terdaftar", Toast.LENGTH_SHORT).show()
                    }

                    if (response.code().toString().equals("400")){
                        Toast.makeText(this@RegisterActivity, "Pastikan inputan sudah benar", Toast.LENGTH_SHORT).show()
                    }

                    if (response.code().toString().equals("500")){
                        Toast.makeText(this@RegisterActivity, "Terdapat Kesalahan Server", Toast.LENGTH_SHORT).show()
                    }

                }
            }
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                showLoading(false)
                val responseMessage  = "Terdapat masalah koneksi"
                Toast.makeText(this@RegisterActivity, responseMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "RegistrasiActivity"
    }
}