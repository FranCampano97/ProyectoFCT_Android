package com.example.proyectofct.ui.view.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofct.R
import com.example.proyectofct.databinding.ActivityLoginBinding
import com.example.proyectofct.domain.LoginUseCase
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: String
    private lateinit var pass: String
    private lateinit var loginUseCase: LoginUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        loginUseCase = LoginUseCase()

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        this.setContentView(binding.root)
        session()
        binding.olvidadoDatos.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.btnInicioSesion.setOnClickListener {
            user = binding.etEmail.text.toString()
            pass = binding.etPassword.text.toString()

            loginUseCase.iniciarUsuario(user, pass, this)
        }
        binding.olvidadoDatos.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.btnRegistrar.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.icOjoPass.setOnClickListener {
            showPass(binding.etPassword)
        }
        binding.rememberPass.setOnClickListener {
            if (binding.rememberPass.isChecked) {
                Log.i("PASS", "------esta true")
                user = binding.etEmail.text.toString()
                pass = binding.etPassword.text.toString()
                if (user.isNotEmpty() && pass.isNotEmpty()) {
                    rememberPass(user, pass, binding.rememberPass.isChecked)
                } else {
                    errorRelleneAmbos()
                    binding.rememberPass.isChecked = false
                }
            } else if (!binding.rememberPass.isChecked) {
                borrarCredenciales()
                Log.i("PASS", "No esta true")
            }
        }

    }

    private fun borrarCredenciales() {
        FirebaseAuth.getInstance().signOut()
        val prefs: SharedPreferences.Editor? =
            getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
            ).edit()
        if (prefs != null) {
            prefs.clear()
            prefs.apply()
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

    private fun session() {
        val prefs: SharedPreferences? =
            getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
            )
        val email = prefs?.getString("email", null)
        val pass = prefs?.getString("pass", null)
        val isChecked = prefs?.getBoolean("isChecked", false)
        if (email != null && pass != null && isChecked != null) {
            binding.etEmail.setText(email)
            binding.etPassword.setText(pass)
            binding.rememberPass.isChecked = isChecked
            Log.i("PASS", "El email: $email y la pass: $pass")
        } else {
            Log.i("PASS", "El email: $email y la pass: $pass")
        }
    }

    private fun rememberPass(email: String, pass: String, isChecked: Boolean) {
        val prefs: SharedPreferences.Editor? =
            getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
            ).edit()
        if (prefs != null) {
            prefs.putString("email", email)
            prefs.putString("pass", pass)
            prefs.putBoolean("isChecked", isChecked)
            prefs.apply()
        }
    }

}