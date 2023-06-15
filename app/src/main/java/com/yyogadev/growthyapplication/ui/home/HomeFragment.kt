package com.yyogadev.growthyapplication.ui.home

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
import com.yyogadev.growthyapplication.DiseaseSearchActivity
import com.yyogadev.growthyapplication.PlantSearchActivity
import com.yyogadev.growthyapplication.ui.home.financial.FinancialActivity
import com.yyogadev.growthyapplication.ui.home.deteksi.UploadActivity
import com.yyogadev.growthyapplication.databinding.FragmentHomeBinding
import com.yyogadev.growthyapplication.ui.SettingPreferences
import com.yyogadev.growthyapplication.ui.TokenViewModel
import com.yyogadev.growthyapplication.ui.TokenViewModelFactory
import com.yyogadev.growthyapplication.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var tokenViewModel: TokenViewModel
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pref = SettingPreferences.getInstance(requireContext().dataStore)

        tokenViewModel = ViewModelProvider(this, TokenViewModelFactory(pref)).get(
            TokenViewModel::class.java
        )

        tokenViewModel.getName().observe(viewLifecycleOwner) { name: String? ->
                binding.welcomeText.text = "Welcome $name"
        }


        binding.btnTanamanHias.setOnClickListener {
            startFiturTanamanHias()
        }

        binding.btnDeteksi.setOnClickListener {
            startFiturCamera()
        }

        binding.btnFinancial.setOnClickListener {
            startFiturFinancial()
        }

        binding.btnHama.setOnClickListener {
            startFiturHama()
        }

        return root
    }


    private fun startFiturHama() {
        val intent = Intent(requireContext(), DiseaseSearchActivity::class.java)
        startActivity(intent)
    }

    private fun startFiturPrediksi() {
//        val intent = Intent(this, PredictionActivity::class.java)
//        startActivity(intent)
    }

    private fun startFiturTanamanHias(){
        val intent = Intent(requireContext(), PlantSearchActivity::class.java)
        startActivity(intent)
    }

    private fun startFiturCamera() {
        val intent = Intent(requireContext(), UploadActivity::class.java)
        startActivity(intent)
    }

    private fun startFiturFinancial() {
        val intent = Intent(requireContext(), FinancialActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}