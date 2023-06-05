package com.yyogadev.growthyapplication.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.camera.core.impl.utils.ContextUtil
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yyogadev.growthyapplication.R
import com.yyogadev.growthyapplication.databinding.FragmentProfileBinding
import com.yyogadev.growthyapplication.ui.SettingPreferences
import com.yyogadev.growthyapplication.ui.TokenViewModel
import com.yyogadev.growthyapplication.ui.TokenViewModelFactory
import com.yyogadev.growthyapplication.ui.login.LoginActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class ProfileFragment : Fragment(),View.OnClickListener {

    private var _binding: FragmentProfileBinding?= null
    private lateinit var tokenViewModel: TokenViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

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
                val i = Intent(this.requireContext(), LoginActivity::class.java)
                startActivity(i)
            }
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.toEdtProfile) {
            val editProfileFragment = EditProfileFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(R.id.nav_host_fragment_activity_main, editProfileFragment, EditProfileFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
        if(v.id == R.id.btn_logout){
            tokenViewModel.saveToken("")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}