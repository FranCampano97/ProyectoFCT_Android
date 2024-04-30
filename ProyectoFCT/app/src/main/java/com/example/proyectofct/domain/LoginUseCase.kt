package com.example.proyectofct.domain

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.proyectofct.ui.view.Activity.PantallaPrincipalActivity
import com.google.firebase.auth.FirebaseAuth

class LoginUseCase {

    fun iniciarUsuario(email: String, password: String, context: Context) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showHome(context)
                        Log.d("TAG", "Login exitoso")

                    } else {
                        errorAlert(context)
                        Log.d("TAG", "Error al logear: ${task.exception?.message}")

                    }
                }
        }
    }

    fun registrarUsuario(email: String, password: String, context: Context) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
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

fun olvidoContraseña(email:String,context: Context){
    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
        .addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(context,"Correo enviado", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(context,"No se pudo enviar.", Toast.LENGTH_SHORT).show()
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