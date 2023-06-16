package com.yyogadev.growthyapplication.ui.home.financial

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yyogadev.growthyapplication.R
import com.yyogadev.growthyapplication.databinding.ActivityFinancialBinding
import com.yyogadev.growthyapplication.retrofit.response.DataItem
import com.yyogadev.growthyapplication.ui.SettingPreferences
import com.yyogadev.growthyapplication.ui.TokenViewModel
import com.yyogadev.growthyapplication.ui.TokenViewModelFactory
import com.yyogadev.growthyapplication.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class FinancialActivity : AppCompatActivity(),  View.OnClickListener {
    private lateinit var binding: ActivityFinancialBinding
    private lateinit var tokenViewModel: TokenViewModel
    private lateinit var transaksiViewModel: TransaksiViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinancialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStories.layoutManager = layoutManager

        val btnPemasukan: Button = findViewById(R.id.btn_pemasukan)
        val btnPengeluaran: Button = findViewById(R.id.btn_pengeluaran)
        btnPemasukan.setOnClickListener(this)
        btnPengeluaran.setOnClickListener(this)

        val pref = SettingPreferences.getInstance(dataStore)

        tokenViewModel = ViewModelProvider(this, TokenViewModelFactory(pref)).get(
            TokenViewModel::class.java
        )

        tokenViewModel.getName().observe(this) { name: String->
            binding.tvLevel.setText(name.uppercase())
        }



        tokenViewModel.getToken().observe(this) { token: String->
            if (token.isEmpty()) {
                val i = Intent(this@FinancialActivity, LoginActivity::class.java)
                startActivity(i)
            }else{
                transaksiViewModel = ViewModelProvider(this, TransaksiViewModelFactory(token))
                    .get(TransaksiViewModel::class.java)

                transaksiViewModel.isLoading.observe(this) {
                    showLoading(it)
                }

                transaksiViewModel.listTransaksi.observe(this) { items ->
                    items?.let {
                        setTransaksiData(items)
                    }
                }


                transaksiViewModel.balace.observe(this) {
                        items -> binding.tvBalance.text= "Rp." + items.toString()
                }




            }
        }
    }
    private fun setTransaksiData(transaksiList: List<DataItem>) {
        val adapter = TransaksiAdapter(transaksiList)
        binding.rvStories.adapter = adapter
        adapter.setOnItemClickCallback(object : TransaksiAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataItem) {
                if(data.type.equals("pemasukan")){
                    showSelectedPemasukan(data)
                }

                if(data.type.equals("pengeluaran")){
                    showSelectedPengeluaran(data)
                }
            }
        })

    }

    private fun showSelectedPemasukan(oneTransaksi: DataItem) {
//        val imgPhoto = binding.rvStories.findViewWithTag<View>("profile_tag")
//        val tvName = binding.rvStories.findViewWithTag<View>("desc_tag")
//
        val moveActivityWithObjectIntent = Intent(this@FinancialActivity, PemasukanAddUpdateActivity::class.java)
        moveActivityWithObjectIntent.putExtra(PemasukanAddUpdateActivity.EXTRA_PEMASUKAN, oneTransaksi.id)

        startActivity(moveActivityWithObjectIntent)

    }

    private fun showSelectedPengeluaran(oneTransaksi: DataItem) {
//        val imgPhoto = binding.rvStories.findViewWithTag<View>("profile_tag")
//        val tvName = binding.rvStories.findViewWithTag<View>("desc_tag")
//
        val moveActivityWithObjectIntent = Intent(this@FinancialActivity, PengeluaranAddUpdateActivity::class.java)
        moveActivityWithObjectIntent.putExtra(PengeluaranAddUpdateActivity.EXTRA_PENGELUARAN, oneTransaksi.id)

        startActivity(moveActivityWithObjectIntent)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.btn_pemasukan) {
                val intent = Intent(this, PemasukanAddUpdateActivity::class.java)
                startActivity(intent)
            }
            if (v.id == R.id.btn_pengeluaran) {

                val intent = Intent(this, PengeluaranAddUpdateActivity::class.java)
                startActivity(intent)
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
}