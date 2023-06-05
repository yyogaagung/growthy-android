package com.yyogadev.growthyapplication.ui.home.financial

import android.R
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yyogadev.growthyapplication.databinding.ActivityPemasukanAddUpdateBinding
import com.yyogadev.growthyapplication.retrofit.ApiConfig
import com.yyogadev.growthyapplication.retrofit.response.CreatePemasukanRequest
import com.yyogadev.growthyapplication.retrofit.response.Data
import com.yyogadev.growthyapplication.retrofit.response.EditFinancialResponse
import com.yyogadev.growthyapplication.retrofit.response.OneFinancialResponse
import com.yyogadev.growthyapplication.ui.SettingPreferences
import com.yyogadev.growthyapplication.ui.TokenViewModel
import com.yyogadev.growthyapplication.ui.TokenViewModelFactory
import com.yyogadev.growthyapplication.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class PemasukanAddUpdateActivity : AppCompatActivity() {
    private var isEdit = false
        private var note: Data? = null
    private var tokenPublic: String = ""
    //    private lateinit var noteHelper: NoteHelper
    private lateinit var tokenViewModel: TokenViewModel
    private lateinit var oneFinancialViewModel: OneFinancialViewModel

    private lateinit var binding: ActivityPemasukanAddUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPemasukanAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val pref = SettingPreferences.getInstance(dataStore)

        tokenViewModel = ViewModelProvider(this, TokenViewModelFactory(pref)).get(
            TokenViewModel::class.java
        )

        tokenViewModel.getToken().observe(this) { token: String->
            if (token.isEmpty()) {
                val i = Intent(this@PemasukanAddUpdateActivity, LoginActivity::class.java)
                startActivity(i)
            }else{
                oneFinancialViewModel = ViewModelProvider(this, OneFinancialViewModelFactory(token, intent.getIntExtra(
                    EXTRA_PEMASUKAN,0)))
                    .get(OneFinancialViewModel::class.java)

                tokenPublic = token
//            oneFinancialViewModel.isLoading.observe(this) {
//                showLoading(it)
//            }

                oneFinancialViewModel.transaksi.observe(this) {
                        items -> setTransaksiData(items, token)
                }

            }
        }


    }



    private fun setTransaksiData(data: Data?, token:String) {
        note= data
        if (data != null) {
            isEdit = true
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"
            data?.let {
                if(it.descPemasukan == null){
                    binding.edtDes.setText("")
                    binding.edtJumlah.setText("")
                }else{
                    binding.edtDes.setText(it.descPemasukan.toString())
                    binding.edtJumlah.setText(it.pemasukan.toString())
                }

            }
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        if (supportActionBar != null) {
            supportActionBar!!.title = actionBarTitle
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        binding.txtSubmit.text = btnTitle

        if(!isEdit){
            binding.btnDelete.setCardBackgroundColor(getColor(R.color.darker_gray))
            binding.btnDelete.isClickable = false
        }else{
            binding.btnDelete.setOnClickListener {
                if (data != null) {
                    deleteFinancial(token, data.id)
                }
            }
        }

        binding.btnSubmit.setOnClickListener { view ->
            val title: String = binding.edtDes.getText().toString().trim()
            val inputNominal: String = binding.edtJumlah.getText().toString().trim()
            var number = 0
            try {
                number = inputNominal.toInt()
            } catch (e: NumberFormatException) {
                // Handle invalid input
                e.toString()
            }

            if (title.isEmpty()) {
                binding.edtDes.setError("Tidak Boleh Kosong")
            } else if (number == 0) {
                binding.edtJumlah.setError("Tidak Boleh Kosong")
            } else {
                if (isEdit) {

                    updateFinancial(token, title, number)
//                    showToast(getString(R.string.changed))
                } else {
//                    note.setDate(DateHelper.getCurrentDate())
//                    noteAddUpdateViewModel.insert(note)
//                    showToast(getString(R.string.added))
                    postFinancial(token, title, number)
                }
                finish()
            }
        }

    }

    private fun updateFinancial(token: String, description: String, nominal:Int) {

        val apiService = ApiConfig.getAuthApiService(token).updatePemasukanFinancial(intent.getIntExtra(
            EXTRA_PEMASUKAN, 0,
        ),nominal, description,"pemasukan")
        apiService.enqueue(object : Callback<EditFinancialResponse> {
            override fun onResponse(
                call: Call<EditFinancialResponse>,
                response: Response<EditFinancialResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val i = Intent(this@PemasukanAddUpdateActivity, FinancialActivity::class.java)
                        startActivity(i)

                        Toast.makeText(this@PemasukanAddUpdateActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@PemasukanAddUpdateActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<EditFinancialResponse>, t: Throwable) {
                Toast.makeText(this@PemasukanAddUpdateActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun postFinancial(token: String, description: String, nominal:Int) {
        val request = CreatePemasukanRequest(
            nominal, // Example nominal value
            description,
            "pemasukan"
        )
        val apiService = ApiConfig.getAuthApiService(token).createPemasukanFinancial(request)
        apiService.enqueue(object : Callback<EditFinancialResponse> {
            override fun onResponse(
                call: Call<EditFinancialResponse>,
                response: Response<EditFinancialResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val i = Intent(this@PemasukanAddUpdateActivity, FinancialActivity::class.java)
                        startActivity(i)

                        Toast.makeText(this@PemasukanAddUpdateActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@PemasukanAddUpdateActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<EditFinancialResponse>, t: Throwable) {
                Toast.makeText(this@PemasukanAddUpdateActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun deleteFinancial(token: String, id:Int) {
        val apiService = ApiConfig.getAuthApiService(token).deleteTransaksi(id)
        apiService.enqueue(object : Callback<EditFinancialResponse> {
            override fun onResponse(
                call: Call<EditFinancialResponse>,
                response: Response<EditFinancialResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val i = Intent(this@PemasukanAddUpdateActivity, FinancialActivity::class.java)
                        startActivity(i)

                        Toast.makeText(this@PemasukanAddUpdateActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@PemasukanAddUpdateActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<EditFinancialResponse>, t: Throwable) {
                Toast.makeText(this@PemasukanAddUpdateActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

    }


//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }


    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(com.yyogadev.growthyapplication.R.string.message_cancel)
        } else {
            dialogMessage = getString(com.yyogadev.growthyapplication.R.string.message_delete)
            dialogTitle = getString(com.yyogadev.growthyapplication.R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { dialog: DialogInterface?, id: Int ->
                if (!isDialogClose) {
                    note?.let { deleteFinancial(tokenPublic, it.id) }
//                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            .setNegativeButton(
                getString(R.string.no)
            ) { dialog: DialogInterface, id: Int -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    companion object {
        const val EXTRA_PEMASUKAN = "extra_pemasukan"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

}