package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofct.R
import com.example.proyectofct.ViewPagerAdapter
import com.example.proyectofct.core.RetrofitHelper
import com.example.proyectofct.data.model.FacturaAdapter
import com.example.proyectofct.data.network.FacturaService
import com.example.proyectofct.databinding.ActivityFacturaListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

@AndroidEntryPoint
class FacturaListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFacturaListBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: FacturaAdapter
    val facturaService = FacturaService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacturaListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = FacturaAdapter { mostrarPopupInformacion() }
        binding.listaFacturas.layoutManager = LinearLayoutManager(this)
        binding.listaFacturas.adapter = adapter
        binding.listaFacturas.setHasFixedSize(true)
        initUI()

    }


    private fun initUI() {
        binding.progressbar.isVisible = true

        CoroutineScope(Dispatchers.IO).launch {

            val response = facturaService.CargarFacturas()

            runOnUiThread {
                if (response != null) {
                    Log.i("TAG", response.toString())
                    adapter.updateList(response.facturas)
                    binding.progressbar.isVisible = false
                } else {
                    Log.i("TAG", "no funciona ya")
                }
            }
        }

        binding.icFiltrar.setOnClickListener {
            intent = Intent(this, FiltrarFacturasActivity::class.java)
            startActivity(intent)
        }
        binding.icBack.setOnClickListener {
            intent = Intent(this, PantallaPrincipalActivity::class.java)
            startActivity(intent)
        }
    }

    private fun mostrarPopupInformacion() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Información")
        builder.setMessage("Esta funcionalidad aún no está disponible")
        builder.setPositiveButton("Cerrar") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}