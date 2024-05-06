package com.example.proyectofct.domain

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.proyectofct.ui.view.Activity.PantallaPrincipalActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterUseCase(private val firebaseAuth: FirebaseAuth) {

    fun registrarUsuario(email: String, password: String, context: Context) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showHome(context)
                        Log.d("TAG", "Registro exitoso")

                    } else {
                        errorAlert(context)
                        Log.d("TAG", "Error al registrar: ${task.exception?.message}")

                    }
                }
        }
    }


    private fun errorAlert(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("Se produjo un error con sus credenciales")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(context: Context) {
        val intent = Intent(context, PantallaPrincipalActivity::class.java)
        context.startActivity(intent)
    }
}