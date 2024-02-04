package com.jorge.lojavirtualandroidmaster.util

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.jorge.lojavirtualandroidmaster.R

class DialogLoad (private val fragment:Fragment) {
    lateinit var dialog: AlertDialog
    fun innitLoadAlertDialog(){
        val builder = AlertDialog.Builder(fragment.requireContext())
        val layout = fragment.layoutInflater

        builder.setView(layout.inflate(R.layout.dialog_load, null))
        builder.setCancelable(false)
        dialog = builder.create()

        dialog.show()
    }

    fun calcelAlertDialog(){
        dialog.dismiss()
    }
}