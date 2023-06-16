package com.yyogadev.growthyapplication.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.auth0.android.jwt.JWT
import com.bumptech.glide.Glide
import com.yyogadev.growthyapplication.BriefTourActivity
import com.yyogadev.growthyapplication.R
import com.yyogadev.growthyapplication.databinding.FragmentProfileBinding
import com.yyogadev.growthyapplication.retrofit.response.DetailProfileResponse
import com.yyogadev.growthyapplication.ui.SettingPreferences
import com.yyogadev.growthyapplication.ui.TokenViewModel
import com.yyogadev.growthyapplication.ui.TokenViewModelFactory
import com.yyogadev.growthyapplication.ui.login.LoginActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class ProfileFragment : Fragment(),View.OnClickListener {

    private var _binding: FragmentProfileBinding?= null
    private lateinit var tokenViewModel: TokenViewModel
    private lateinit var profileViewModel: ProfileViewModel
    var jwtToken = ""
    var signingKey = ""
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textProfile
//        profileViewModel.text.observe(viewLifecycleOwner) {text ->
//            textView.text = text
//        }
        binding.toEdtProfile.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
        return root
    }

    override fun onStart() {
        super.onStart()

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        tokenViewModel = ViewModelProvider(this, TokenViewModelFactory(pref)).get(
            TokenViewModel::class.java
        )

        tokenViewModel.getToken().observe(this) { token: String->
            if (token.isEmpty()) {
                val i = Intent(this.requireContext(), BriefTourActivity::class.java)
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
            }

        }
    }

    private fun setProfileData(user: DetailProfileResponse) {
        if (user.avatar != null){
            Glide.with(this.requireContext())
                .load(user.avatar)
                .into(binding.profileImage)
        }else{
           binding.profileImage.setImageResource(R.drawable.gamer)
        }

        if (user.name != null){
            binding.tvFullname.text = user.name
            binding.idUsername.text = user.name
        }else{
            binding.tvFullname.text = "Tidak ada data"
            binding.idUsername.text ="Tidak ada data"
        }

        if (user.email != null){
            binding.idEmail.text = user.email
        }else{
            binding.idEmail.text = "Tidak ada data"
        }

        if (user.address != null){
            binding.idKota.text = user.address.toString()
        }else{
            binding.idKota.text = "Tidak ada data"
        }

        if (user.phone != null){
            binding.idTelepon.text = user.phone.toString()
        }else{
            binding.idTelepon.text = "Tidak ada data"
        }

    }

    override fun onClick(v: View) {
        if (v.id == R.id.toEdtProfile) {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }
        if(v.id == R.id.btn_logout){
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            tokenViewModel.saveToken("")

        }
    }

//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}