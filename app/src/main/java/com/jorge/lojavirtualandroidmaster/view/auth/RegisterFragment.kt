package com.jorge.lojavirtualandroidmaster.view.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.jorge.lojavirtualandroidmaster.R
import com.jorge.lojavirtualandroidmaster.databinding.FragmentRegisterBinding
import com.jorge.lojavirtualandroidmaster.model.DB
import com.jorge.lojavirtualandroidmaster.model.User
import com.jorge.lojavirtualandroidmaster.view.activitys.MainActivity
import java.util.UUID
import java.util.UUID.randomUUID

class RegisterFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DB
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
                val nome = binding.etNome.text.toString()
                val password = binding.etPassword.text.toString()
                registerFirebase(nome, email, password)
            }
        }
    }

    private fun registerFirebase(nome: String, email: String, password:String) {
        auth = Firebase.auth
        db = DB()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task ->
            if (task.isSuccessful){
                Snackbar.make(binding.root, "Usuário criado com sucesso" ,Snackbar.LENGTH_LONG).show()
                auth.signInWithEmailAndPassword(email, password)

                db.saveDataUser(nome, email)
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                binding.progressBarTelaCadastro.isVisible = false
            }else{
                Snackbar.make(binding.root, task.exception.toString() ,Snackbar.LENGTH_LONG).show()
                binding.progressBarTelaCadastro.isVisible = false
            }

        }.addOnFailureListener {errorRegister ->

            val msgError = when(errorRegister){
                is FirebaseAuthWeakPasswordException -> "Digite um senha com no minimo 6 caracteres"
                is FirebaseAuthUserCollisionException -> "Email já cadastrado"
                is FirebaseNetworkException -> "Sem conexão com a internet"
                else -> "Erro no cadastro"
            }
            Toast.makeText(requireContext(), msgError, Toast.LENGTH_LONG).show()

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