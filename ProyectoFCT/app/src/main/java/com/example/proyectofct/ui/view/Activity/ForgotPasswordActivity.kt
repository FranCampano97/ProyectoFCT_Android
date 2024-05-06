package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofct.R
import com.example.proyectofct.databinding.ActivityForgotPasswordBinding
import com.example.proyectofct.domain.LoginUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var loginUseCase: LoginUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val firebaseAuth = FirebaseAuth.getInstance()
        loginUseCase = LoginUseCase(firebaseAuth)

        binding.btnInicio.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnEnviar.setOnClickListener {
            loginUseCase.olvidoContraseña(binding.etEmail.text.toString(), this)
        }

    }
}