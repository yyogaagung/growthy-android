package com.yyogadev.growthyapplication.ui.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.auth0.android.jwt.JWT
import com.bumptech.glide.Glide
import com.yyogadev.growthyapplication.R
import com.yyogadev.growthyapplication.databinding.ActivityEditProfileBinding
import com.yyogadev.growthyapplication.retrofit.ApiConfig
import com.yyogadev.growthyapplication.retrofit.response.DetailProfileResponse
import com.yyogadev.growthyapplication.retrofit.response.EditFinancialResponse
import com.yyogadev.growthyapplication.ui.MainActivity
import com.yyogadev.growthyapplication.ui.SettingPreferences
import com.yyogadev.growthyapplication.ui.TokenViewModel
import com.yyogadev.growthyapplication.ui.TokenViewModelFactory
import com.yyogadev.growthyapplication.ui.login.LoginActivity
import com.yyogadev.growthyapplication.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var tokenViewModel: TokenViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)

        tokenViewModel = ViewModelProvider(this, TokenViewModelFactory(pref)).get(
            TokenViewModel::class.java
        )

        tokenViewModel.getToken().observe(this) { token: String->
            if (token.isEmpty()) {
                val i = Intent(this@EditProfileActivity, LoginActivity::class.java)
                startActivity(i)
            }else{
                val jwt = JWT(token)

                val issuer = jwt.issuer //get registered claims

                val claim = jwt.getClaim("id").asInt() //get custom claims

                profileViewModel = ViewModelProvider(this, ProfileViewModelFactory(token, claim))
                    .get(ProfileViewModel::class.java)

//            profileViewModel.isLoading.observe(this) {
//                showLoading(it)
//            }

                profileViewModel.profile.observe(this) {
                        items -> setProfileData(items)
                }

                binding.btnGantiPhotoProfile.setOnClickListener { startGallery() }
                binding.btnPost.setOnClickListener {
                    uploadImage(
                        token,
                        claim,
                        binding.edtUsername.text.toString(),
                        binding.edtEmail.text.toString(),
                        binding.edtKota.text.toString(),
                        binding.phone.text.toString()
                        )
                }

            }
        }

    }

    private fun uploadImage(
        token:String, id: Int?,
        name: String, email:String, address:String, phone:String) {
        if (getFile != null) {
            val file = getFile as File

            val name = name.toRequestBody("text/plain".toMediaType())
            val email = email.toRequestBody("text/plain".toMediaType())
            val address = address.toRequestBody("text/plain".toMediaType())
            val phone = phone.toRequestBody("text/plain".toMediaType())



            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "avatar",
                file.name,
                requestImageFile
            )

            val client = ApiConfig.getAuthApiService(token).updateUser(id, name, email, address, phone, imageMultipart)
            client.enqueue(object : Callback<EditFinancialResponse> {
                override fun onResponse(
                    call: Call<EditFinancialResponse>,
                    response: Response<EditFinancialResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            Toast.makeText(this@EditProfileActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                            val i = Intent(this@EditProfileActivity, MainActivity::class.java)
                            startActivity(i)

                        }
                    } else {
                        Toast.makeText(this@EditProfileActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<EditFinancialResponse>, t: Throwable) {
                    Toast.makeText(this@EditProfileActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })

        }
        else{
            Toast.makeText(
                this,
                "Silakan masukkan berkas gambar terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri

            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@EditProfileActivity)
                getFile = myFile
                binding.profileImage.setImageURI(uri)
            }
        }
    }

    private fun setProfileData(user: DetailProfileResponse) {
        if (user.avatar != null){
            Glide.with(this)
                .load(user.avatar)
                .into(binding.profileImage)
        }else{
            binding.profileImage.setImageResource(R.drawable.gamer)
        }

        if (user.name != null){
            binding.edtUsername.setText(user.name)
        }

        if (user.email != null){
            binding.edtEmail.setText(user.email)
        }

        if (user.address != null){
            binding.edtKota.setText(user.address.toString())
        }

        if (user.phone != null){
            binding.phone.setText(user.phone.toString())
        }

    }
}