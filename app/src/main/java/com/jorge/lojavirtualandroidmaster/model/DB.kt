package com.jorge.lojavirtualandroidmaster.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class DB {
    fun saveDataUser(nome: String, email: String) {
        val db = FirebaseFirestore.getInstance()

        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val user = User(id, nome, email)
        val documentReference: DocumentReference = db.collection("users").document(id)

        documentReference.set(user).addOnSuccessListener {
            Log.d("DB", "Salvo com sucesso")
        }.addOnFailureListener {
            Log.d("DB_ERRO", it.printStackTrace().toString())
        }

    }
}