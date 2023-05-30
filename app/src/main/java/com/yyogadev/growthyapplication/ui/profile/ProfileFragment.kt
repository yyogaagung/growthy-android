package com.yyogadev.growthyapplication.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yyogadev.growthyapplication.R
import com.yyogadev.growthyapplication.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(),View.OnClickListener {

    private var _binding: FragmentProfileBinding?= null

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
        return root
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}