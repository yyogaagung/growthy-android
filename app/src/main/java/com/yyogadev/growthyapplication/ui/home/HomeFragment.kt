package com.yyogadev.growthyapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yyogadev.growthyapplication.PlantSearchActivity
import com.yyogadev.growthyapplication.ui.home.financial.FinancialActivity
import com.yyogadev.growthyapplication.ui.home.deteksi.UploadActivity
import com.yyogadev.growthyapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnTanamanHias.setOnClickListener {
            startFiturTanamanHias()
        }

        binding.btnDeteksi.setOnClickListener {
            startFiturCamera()
        }

        binding.btnFinancial.setOnClickListener {
            startFiturFinancial()
        }


        return root
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