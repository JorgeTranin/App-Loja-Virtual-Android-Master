package com.jorge.lojavirtualandroidmaster.view.activitys

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jorge.lojavirtualandroidmaster.R
import com.jorge.lojavirtualandroidmaster.databinding.FragmentMainBinding
import com.jorge.lojavirtualandroidmaster.view.auth.AuthActivity

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupListeners()
    }

    private fun setupListeners() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {


                R.id.perfil -> {
                    // Handle search icon press
                    true
                }

                R.id.more -> {

                    true
                }
                R.id.logout -> {
                    auth = Firebase.auth
                    auth.signOut()
                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}