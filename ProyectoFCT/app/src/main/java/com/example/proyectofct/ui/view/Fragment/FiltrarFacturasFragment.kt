package com.example.proyectofct.ui.view.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.proyectofct.R
import com.example.proyectofct.databinding.FragmentFiltrarFacturasBinding
import com.example.proyectofct.ui.view.Activity.FacturaListActivity
import com.example.proyectofct.ui.viewModel.FacturaListViewModel

class FiltrarFacturasFragment : Fragment() {
    private var _binding: FragmentFiltrarFacturasBinding? = null
    val binding get() = _binding!!
    private val viewModel: FacturaListViewModel by activityViewModels()

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

        binding.btnCancelar.setOnClickListener {
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
                requireActivity().supportFragmentManager.popBackStack()

            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error al obtener las facturas por estado",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("FILTRADO", "Error al obtener las facturas por estado", e)
            }
          /*  val b= requireActivity().findViewById<FragmentContainerView>(R.id.container_view)
            val c= requireActivity().findViewById<LinearLayout>(R.id.contenido)
            b.visibility=View.GONE
            c.visibility=View.VISIBLE
*/

        }

    }


    private fun showPickerDialog(button: Button) {
        val datePicker =
            DatePickerFragment { day, month, year ->
                onDateSelected(day, month, year, button)
            }

        datePicker.show(requireActivity().supportFragmentManager, "datePicker")
    }


    private fun onDateSelected(day: Int, month: Int, year: Int, button: Button) {
        button.setText("$day-$month-$year")
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


