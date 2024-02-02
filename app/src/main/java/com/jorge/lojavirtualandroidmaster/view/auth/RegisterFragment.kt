package com.jorge.lojavirtualandroidmaster.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jorge.lojavirtualandroidmaster.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnCadastrar.setOnClickListener {

            val check = checkFields()
            if (check){
                registerFirebase()
            }
        }
    }

    private fun registerFirebase() {

    }

    private fun checkFields(): Boolean {
        if (binding.etEmail.text.isNullOrEmpty() || binding.etNome.text.isNullOrEmpty() || binding.etPassword.text.isNullOrEmpty()){
            Snackbar.make(binding.root, "Preencha todos os campos" ,Snackbar.LENGTH_LONG).show()
            return false
        }else{
            return true
        }
    }


}