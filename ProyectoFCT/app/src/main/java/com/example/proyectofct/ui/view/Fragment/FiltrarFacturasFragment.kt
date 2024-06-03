package com.example.proyectofct.ui.view.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.proyectofct.R
import com.example.proyectofct.data.model.FacturaAdapter
import com.example.proyectofct.databinding.FragmentFiltrarFacturasBinding
import com.example.proyectofct.domain.model.Factura
import com.example.proyectofct.ui.viewModel.FacturaListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date

class FiltrarFacturasFragment : Fragment() {
    private var _binding: FragmentFiltrarFacturasBinding? = null
    private lateinit var listaFacturas: List<Factura>
    val binding get() = _binding!!
    private val viewModel: FacturaListViewModel by activityViewModels()
    private lateinit var adapter: FacturaAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFiltrarFacturasBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var importe: Float = 0f
        var importeMayor: Float = 0f
        binding.btnCancelar.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnEliminarFiltros.setOnClickListener {
            binding.btnDesde.setText(R.string.dia_mes)
            binding.btnHasta.setText(R.string.dia_mes)
            binding.checkPendientes.isChecked = false
            binding.checkPagadas.isChecked = false
            binding.checkAnuladas.isChecked = false
            binding.checkCuotaFija.isChecked = false
            binding.checkPlanPago.isChecked = false
            binding.rangeSlider.setValues(0.0f)
        }
        binding.btnDesde.setOnClickListener {
            showPickerDialog(true)
        }
        binding.btnHasta.setOnClickListener {
            showPickerDialog(false)
        }
        binding.rangeSlider.valueFrom = 0f

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val precioMayor = viewModel.getPrecioMayor()
                val importeMayor = precioMayor?.toFloat() ?: 0.0f
                println(precioMayor)
                withContext(Dispatchers.Main) {
                    binding.rangeSlider.valueTo = importeMayor
                    binding.precioSeleccionado.text = "0 € - $importeMayor €"
                }
            } catch (e: Exception) {
                Log.e("fran", "Error al obtener el precio mayor", e)
            }
        }

        binding.rangeSlider.addOnChangeListener { slider, _, _ ->
            importe = slider.values[0].toFloat()
            val precio = importe.toString()
            binding.precioSeleccionado.setText("$precio € - $importeMayor €")
            Log.i("facturas", "El valor del rangeSlider es: $importe")
        }

        binding.btnAplicar.setOnClickListener {
            val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
            val desdeDate: Date?
            val hastaDate: Date?


            if (binding.btnDesde.text.toString() != getString(R.string.dia_mes)) {
                desdeDate = formatoFecha.parse(binding.btnDesde.text.toString())
            } else desdeDate = null
            if (binding.btnHasta.text.toString() != getString(R.string.dia_mes)) {
                hastaDate = formatoFecha.parse(binding.btnHasta.text.toString())
            } else hastaDate = null

            viewModel.filtrar(
                importe,
                binding.checkPagadas.isChecked,
                binding.checkPendientes.isChecked,
                binding.checkAnuladas.isChecked,
                binding.checkCuotaFija.isChecked,
                binding.checkPlanPago.isChecked,
                desdeDate,
                hastaDate
            )

            requireActivity().onBackPressed()
        }
    }

    private fun showPickerDialog(button: Boolean) {
        val datePicker =
            DatePickerFragment { day, month, year ->
                onDateSelected(day, month, year, button)
            }

        datePicker.show(requireActivity().supportFragmentManager, "datePicker")
    }


    private fun onDateSelected(day: Int, month: Int, year: Int, button: Boolean) {
        if (button) {
            if (month + 1 < 10) {
                if (day < 10) {
                    binding.btnDesde.setText("0$day/0${month + 1}/$year")
                } else {
                    binding.btnDesde.setText("$day/0${month + 1}/$year")
                }
            } else {
                if (day < 10) {
                    binding.btnDesde.setText("0$day/${month + 1}/$year")
                } else {
                    binding.btnDesde.setText("$day/${month + 1}/$year")
                }
            }
        } else {
            if (month + 1 < 10) {
                if (day < 10) {
                    binding.btnHasta.setText("0$day/0${month + 1}/$year")
                } else {
                    binding.btnHasta.setText("$day/0${month + 1}/$year")
                }
            } else {
                if (day < 10) {
                    binding.btnHasta.setText("0$day/${month + 1}/$year")
                } else {
                    binding.btnHasta.setText("$day/${month + 1}/$year")
                }
            }
        }
    }


    private fun mostrarPopupInformacion() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Información")
        builder.setMessage("Esta funcionalidad aún no está disponible")
        builder.setPositiveButton("Cerrar") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}


