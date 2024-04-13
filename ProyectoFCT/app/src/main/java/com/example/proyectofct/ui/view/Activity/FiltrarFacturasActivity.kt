package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofct.R
import com.example.proyectofct.databinding.ActivityFacturaListBinding
import com.example.proyectofct.databinding.ActivityFiltrarFacturasBinding

class FiltrarFacturasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFiltrarFacturasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFiltrarFacturasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.icCancelar.setOnClickListener {
            intent= Intent(this,FacturaListActivity::class.java)
            startActivity(intent)
        }

    }
}