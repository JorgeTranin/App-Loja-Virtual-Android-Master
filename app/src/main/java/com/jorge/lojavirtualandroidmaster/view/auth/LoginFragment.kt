package com.jorge.lojavirtualandroidmaster.view.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jorge.lojavirtualandroidmaster.R
import com.jorge.lojavirtualandroidmaster.databinding.FragmentLoginBinding
import com.jorge.lojavirtualandroidmaster.util.DialogLoad
import com.jorge.lojavirtualandroidmaster.view.activitys.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.handleCoroutineException

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialogLoad: DialogLoad

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val view = binding.root
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser


        if (currentUser != null) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        setupListeners()
    }


    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                Snackbar.make(binding.cardView, "Preencha todos os campos", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.RED)
                    .show()
            } else {

                checkLogin(email, password)
            }
        }
    }

    private fun checkLogin(email: String, password: String) {
        dialogLoad = DialogLoad(this)
        dialogLoad.innitLoadAlertDialog()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    dialogLoad.calcelAlertDialog()
                }, 2000)


            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    Snackbar.make(binding.cardView, task.exception.toString(), Snackbar.LENGTH_LONG)
                        .show()
                    dialogLoad.calcelAlertDialog()
                }, 2000)


            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}