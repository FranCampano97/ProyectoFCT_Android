package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.proyectofct.R
import com.example.proyectofct.databinding.ActivityPantallaPrincipalBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class PantallaPrincipalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        remoteConfig()
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.icPractica1.setOnClickListener {
            intent = Intent(this, FacturaListActivity::class.java)
            intent.putExtra("mock", binding.switchMock.isChecked)
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

    private fun remoteConfig() {
        //remote config
        val configSettings: FirebaseRemoteConfigSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 10
        }
        val firebaseConfig = Firebase.remoteConfig
        firebaseConfig.setConfigSettingsAsync(configSettings)
        firebaseConfig.setDefaultsAsync(mapOf("verFacturas" to true))
        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                val valor: Boolean = Firebase.remoteConfig.getBoolean("verFacturas")
                val tema: Boolean = Firebase.remoteConfig.getBoolean("cambiarTema")

                if (!valor) {
                    binding.icPractica1.isVisible = false
                    binding.txtPractica1.setText(getString(R.string.funcion_no_disponible))
                } else binding.icPractica1.isVisible = true


                if (tema) {
                    Log.i("tema", "tema secundario")
                    cambiarEstilos()
                    //setTheme(R.style.Theme_Secundario)
                    Log.d("tema", this.theme.toString())
                } else {
                    setTheme(R.style.Theme_ProyectoFCT)
                    Log.i("tema", "tema normal")
                }
                val rootView = window.decorView.rootView
                rootView.invalidate()

            }
        }

    }

    private fun cambiarEstilos() {
        binding.root.setBackgroundColor(ContextCompat.getColor(this, R.color.oscuro))
        binding.titulo.setTextColor(getResources().getColor(R.color.white))
        binding.txtPractica1.setTextColor(getResources().getColor(R.color.white))
        binding.practica2.setTextColor(getResources().getColor(R.color.white))
        binding.navegacion.setTextColor(getResources().getColor(R.color.white))
        binding.mock.setTextColor(getResources().getColor(R.color.white))
    }
}