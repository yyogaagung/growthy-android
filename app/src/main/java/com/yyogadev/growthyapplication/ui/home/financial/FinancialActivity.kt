package com.yyogadev.growthyapplication.ui.home.financial

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yyogadev.growthyapplication.R
import com.yyogadev.growthyapplication.databinding.ActivityFinancialBinding
import com.yyogadev.growthyapplication.ui.SettingPreferences
import com.yyogadev.growthyapplication.ui.TokenViewModel
import com.yyogadev.growthyapplication.ui.TokenViewModelFactory
import com.yyogadev.growthyapplication.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class FinancialActivity : AppCompatActivity(),  View.OnClickListener {
    private lateinit var binding: ActivityFinancialBinding
    private lateinit var tokenViewModel: TokenViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinancialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkToken()

        val btnPemasukan: Button = findViewById(R.id.btn_pemasukan)
        val btnPengeluaran: Button = findViewById(R.id.btn_pengeluaran)
        btnPemasukan.setOnClickListener(this)
        btnPengeluaran.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.btn_pemasukan) {
                val intent = Intent(this, PemasukanAddUpdateActivity::class.java)

                // Add any extra data to the intent if needed
    //            intent.putExtra("key", "value")

                // Start the second activity
                startActivity(intent)
            }
            if (v.id == R.id.btn_pengeluaran) {

                val intent = Intent(this, PengeluaranAddUpdateActivity::class.java)

                // Add any extra data to the intent if needed
                //            intent.putExtra("key", "value")

                // Start the second activity
                startActivity(intent)
            }

        }
    }

    fun checkToken(){
        val pref = SettingPreferences.getInstance(dataStore)

        tokenViewModel = ViewModelProvider(this, TokenViewModelFactory(pref)).get(
            TokenViewModel::class.java
        )

        tokenViewModel.getToken().observe(this) { token: String->
            if (token.isEmpty()) {
                val i = Intent(this@FinancialActivity, LoginActivity::class.java)
                startActivity(i)
            }else{


            }
        }
    }
}