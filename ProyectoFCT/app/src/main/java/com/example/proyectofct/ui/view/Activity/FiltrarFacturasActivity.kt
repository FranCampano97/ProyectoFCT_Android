package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofct.R
import com.example.proyectofct.data.model.FacturaAdapter
import com.example.proyectofct.databinding.ActivityFacturaListBinding
import com.example.proyectofct.databinding.ActivityFiltrarFacturasBinding
import com.example.proyectofct.domain.GetFacturasUseCase
import com.example.proyectofct.domain.model.toFacturaModel
import com.example.proyectofct.ui.view.Fragment.DatePickerFragment
import com.example.proyectofct.ui.viewModel.FacturaListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FiltrarFacturasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFiltrarFacturasBinding
    private lateinit var adapter: FacturaAdapter

    @Inject
    lateinit var getFacturasUseCase: GetFacturasUseCase
    private val viewModel: FacturaListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiltrarFacturasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancelar.setOnClickListener {
            finish()
        }


        binding.btnDesde.setOnClickListener {
            showPickerDialog(binding.btnDesde)
        }
        binding.btnHasta.setOnClickListener {
            showPickerDialog(binding.btnHasta)
        }

        binding.btnAplicar.setOnClickListener {

            try {
                viewModel.getFacturasPorEstado("Pendiente de pago")
                Log.i("facturas", "desde filtrar: ${viewModel.facturas.value}")
                intent = Intent(this, FacturaListActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Error al obtener las facturas por estado", Toast.LENGTH_SHORT)
                    .show()
                Log.e("FILTRADO", "Error al obtener las facturas por estado", e)
            }
        }

    }


    private fun showPickerDialog(button: Button) {
        val datePicker =
            DatePickerFragment { day, month, year ->
                onDateSelected(day, month, year, button)
            }

        datePicker.show(supportFragmentManager, "datePicker")
    }


    private fun onDateSelected(day: Int, month: Int, year: Int, button: Button) {
        button.setText("$day-$month-$year")
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d("facturas", "FiltrarFacturasActivity_destroy")
    }
}