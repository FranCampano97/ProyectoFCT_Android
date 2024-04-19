package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofct.R
import com.example.proyectofct.databinding.ActivityPantallaPrincipalBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PantallaPrincipalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.icPractica1.setOnClickListener {
            intent = Intent(this, FacturaListActivity::class.java)
            startActivity(intent)

        }
        binding.icPractica2.setOnClickListener {
            intent = Intent(this, SmartSolarActivity::class.java)
            startActivity(intent)
        }

        binding.icNavegacion.setOnClickListener {
            intent = Intent(this, NavegadorActivity::class.java)
            startActivity(intent)
        }

    }

}