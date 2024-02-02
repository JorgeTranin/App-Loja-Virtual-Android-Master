package com.jorge.lojavirtualandroidmaster.view.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jorge.lojavirtualandroidmaster.R
import com.jorge.lojavirtualandroidmaster.databinding.FragmentRegisterBinding
import com.jorge.lojavirtualandroidmaster.view.activitys.MainActivity

class RegisterFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
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

            binding.progressBarTelaCadastro.isVisible = true
            val check = checkFields()
            if (check){
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                registerFirebase(email, password)
            }
        }
    }

    private fun registerFirebase(email: String, password:String) {
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task ->
            if (task.isSuccessful){
                Snackbar.make(binding.root, "Usuário criado com sucesso" ,Snackbar.LENGTH_LONG).show()
                auth.signInWithEmailAndPassword(email, password)
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                binding.progressBarTelaCadastro.isVisible = false
            }else{
                Snackbar.make(binding.root, task.exception.toString() ,Snackbar.LENGTH_LONG).show()
                binding.progressBarTelaCadastro.isVisible = false
            }

        }
    }

    private fun checkFields(): Boolean {
        if (binding.etEmail.text.isNullOrEmpty() || binding.etNome.text.isNullOrEmpty() || binding.etPassword.text.isNullOrEmpty()){
            Snackbar.make(binding.root, "Preencha todos os campos" ,Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED) .show()
            return false
        }else{
            return true
        }
    }


}