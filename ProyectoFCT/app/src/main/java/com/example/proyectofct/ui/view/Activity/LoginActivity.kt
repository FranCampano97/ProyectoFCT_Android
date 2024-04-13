package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofct.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: String
    private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        this.setContentView(binding.root)

        binding.btnInicioSesion.setOnClickListener {
            //    user = binding.etEmail.text.toString()
            //  pass = binding.etPassword.text.toString()
            //iniciarUsuario(user, pass)
            val intent = Intent(this, PantallaPrincipalActivity::class.java)
            startActivity(intent)

        }

        binding.btnRegistrar.setOnClickListener {

            user = binding.etEmail.text.toString()
            pass = binding.etPassword.text.toString()

            registrarUsuario(user, pass)
        }

        binding.icOjoPass.setOnClickListener {
            showPass(binding.etPassword)
        }

    }

    private fun registrarUsuario(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showHome()
                        Log.d("TAG", "Registro exitoso")

                    } else {
                        errorAlert()
                        Log.d("TAG", "Error al registrar: ${task.exception?.message}")

                    }
                }
        }
    }


    private fun iniciarUsuario(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showHome()
                        Log.d("TAG", "Login exitoso")

                    } else {
                        errorAlert()
                        Log.d("TAG", "Error al logear: ${task.exception?.message}")

                    }
                }
        }
    }

    private fun cerrarSesion() {
        FirebaseAuth.getInstance().signOut()
    }

    private fun errorAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se produjo un error con sus credenciales")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome() {
        val intent = Intent(this, PantallaPrincipalActivity::class.java)
        startActivity(intent)
    }

    private fun showPass(editTextPassword: TextInputEditText) {

        val isPasswordVisible =
            editTextPassword.transformationMethod == HideReturnsTransformationMethod.getInstance()

        if (isPasswordVisible) {
            // Si la contraseña es visible, cambiar a modo de contraseña oculta
            editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {
            // Si la contraseña está oculta, cambiar a modo de contraseña visible
            editTextPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
        }


    }


}