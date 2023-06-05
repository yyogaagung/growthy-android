package com.yyogadev.growthyapplication.ui.home.financial

import android.R
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yyogadev.growthyapplication.databinding.ActivityPengeluaranAddUpdateBinding
import com.yyogadev.growthyapplication.retrofit.ApiConfig
import com.yyogadev.growthyapplication.retrofit.response.CreatePemasukanRequest
import com.yyogadev.growthyapplication.retrofit.response.CreatePengeluaranRequest
import com.yyogadev.growthyapplication.retrofit.response.Data
import com.yyogadev.growthyapplication.retrofit.response.EditFinancialResponse
import com.yyogadev.growthyapplication.ui.SettingPreferences
import com.yyogadev.growthyapplication.ui.TokenViewModel
import com.yyogadev.growthyapplication.ui.TokenViewModelFactory
import com.yyogadev.growthyapplication.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class PengeluaranAddUpdateActivity : AppCompatActivity() {
    private var isEdit = false
    //    private var note: Note? = null
    private var position: Int = 0
    //    private lateinit var noteHelper: NoteHelper
    private lateinit var tokenViewModel: TokenViewModel
    private lateinit var oneFinancialViewModel: OneFinancialViewModel

    private lateinit var binding: ActivityPengeluaranAddUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengeluaranAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val pref = SettingPreferences.getInstance(dataStore)

        tokenViewModel = ViewModelProvider(this, TokenViewModelFactory(pref)).get(
            TokenViewModel::class.java
        )

        tokenViewModel.getToken().observe(this) { token: String->
            if (token.isEmpty()) {
                val i = Intent(this@PengeluaranAddUpdateActivity, LoginActivity::class.java)
                startActivity(i)
            }else{
                oneFinancialViewModel = ViewModelProvider(this, OneFinancialViewModelFactory(token, intent.getIntExtra(
                    EXTRA_PENGELUARAN,0)))
                    .get(OneFinancialViewModel::class.java)

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
        if (data != null) {
            isEdit = true
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"
            data?.let {
                if(it.descPengeluaran == null){
                    binding.edtDes.setText("")
                    binding.edtJumlah.setText("")
                }else{
                    binding.edtDes.setText(it.descPengeluaran.toString())
                    binding.edtJumlah.setText(it.pengeluaran.toString())
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
            val inputNominal: String = binding.edtJumlah.text.toString()
            var number : Int = 0
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

        val apiService = ApiConfig.getAuthApiService(token).updatePengeluaranFinancial(intent.getIntExtra(
            EXTRA_PENGELUARAN, 0,
        ),nominal, description,"pengeluaran")
        apiService.enqueue(object : Callback<EditFinancialResponse> {
            override fun onResponse(
                call: Call<EditFinancialResponse>,
                response: Response<EditFinancialResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val i = Intent(this@PengeluaranAddUpdateActivity, FinancialActivity::class.java)
                        startActivity(i)

                        Toast.makeText(this@PengeluaranAddUpdateActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@PengeluaranAddUpdateActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<EditFinancialResponse>, t: Throwable) {
                Toast.makeText(this@PengeluaranAddUpdateActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun postFinancial(token: String, description: String, nominal:Int) {
        val request = CreatePengeluaranRequest(
            nominal, // Example nominal value
            description,
            "pengeluaran"
        )
        val apiService = ApiConfig.getAuthApiService(token).createPengeluaranFinancial(request)
        apiService.enqueue(object : Callback<EditFinancialResponse> {
            override fun onResponse(
                call: Call<EditFinancialResponse>,
                response: Response<EditFinancialResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val i = Intent(this@PengeluaranAddUpdateActivity, FinancialActivity::class.java)
                        startActivity(i)

                        Toast.makeText(this@PengeluaranAddUpdateActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@PengeluaranAddUpdateActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<EditFinancialResponse>, t: Throwable) {
                Toast.makeText(this@PengeluaranAddUpdateActivity, t.message, Toast.LENGTH_SHORT).show()
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
                        val i = Intent(this@PengeluaranAddUpdateActivity, FinancialActivity::class.java)
                        startActivity(i)

                        Toast.makeText(this@PengeluaranAddUpdateActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@PengeluaranAddUpdateActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<EditFinancialResponse>, t: Throwable) {
                Toast.makeText(this@PengeluaranAddUpdateActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

    }



    override fun onBackPressed() {
        showAlertDialog(PemasukanAddUpdateActivity.ALERT_DIALOG_CLOSE)
    }



    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == PemasukanAddUpdateActivity.ALERT_DIALOG_CLOSE
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

//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }

    companion object {
        const val EXTRA_PENGELUARAN = "extra_pengeluaran"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

}