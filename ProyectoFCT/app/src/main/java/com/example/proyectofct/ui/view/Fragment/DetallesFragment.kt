package com.example.proyectofct.ui.view.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import co.infinum.retromock.meta.Mock
import com.example.proyectofct.R
import com.example.proyectofct.data.model.Details
import com.example.proyectofct.data.network.MockService
import com.example.proyectofct.data.network.RetroMockService
import com.example.proyectofct.databinding.FragmentDetallesBinding
import com.example.proyectofct.databinding.FragmentFiltrarFacturasBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetallesFragment : Fragment() {
    private var _binding: FragmentDetallesBinding? = null
    val binding get() = _binding!!

    @Inject
    lateinit var mockService: MockService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetallesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val response: Details? = mockService.getDetails()
            if (response != null) {
                binding.etCAU.setText(response.cau)
                binding.etestadosolicitud.setText(response.estadoSolicitud)
                binding.tipoAutoconsumo.setText(response.tipo)
                binding.compensacionExcedentes.setText(response.excedentes)
                binding.potenciaInstalacion.setText(response.potencia)
            }
        }

        binding.iconoInfo.setOnClickListener {
            mostrarPopup()
        }


    }

   private fun mostrarPopup() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.popup_details, null)
        val botonAceptar = dialogView.findViewById<Button>(R.id.btnAceptar)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        val dialog = builder.create()

        botonAceptar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}