package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofct.R
import com.example.proyectofct.databinding.ActivitySignupBinding
import com.example.proyectofct.domain.LoginUseCase
import com.example.proyectofct.domain.RegisterUseCase
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var user: String
    private lateinit var pass: String
    private lateinit var registerUseCase: RegisterUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val firebaseAuth = FirebaseAuth.getInstance()
        registerUseCase = RegisterUseCase(firebaseAuth)
        binding.btnInicioSesion.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnRegistrar.setOnClickListener {

            user = binding.etEmailNuevo.text.toString()
            pass = binding.etPasswordNuevo.text.toString()
            if (user.isNotEmpty() && pass.isNotEmpty()) {

                registerUseCase.registrarUsuario(user, pass, this)
            } else {
                errorRelleneAmbos()
            }

        }
        binding.icOjoPass.setOnClickListener {
            showPass(binding.etPasswordNuevo)
        }
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

    private fun errorRelleneAmbos() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Debe rellenar ambos campos")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}