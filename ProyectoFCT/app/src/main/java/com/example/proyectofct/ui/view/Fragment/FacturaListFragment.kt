package com.example.proyectofct.ui.view.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofct.R
import com.example.proyectofct.data.model.FacturaAdapter
import com.example.proyectofct.databinding.FragmentFacturaListBinding
import com.example.proyectofct.databinding.FragmentFiltrarFacturasBinding
import com.example.proyectofct.domain.model.Factura
import com.example.proyectofct.domain.model.toFacturaModel
import com.example.proyectofct.ui.view.Activity.FacturaListActivity
import com.example.proyectofct.ui.view.Activity.PantallaPrincipalActivity
import com.example.proyectofct.ui.view.Activity.SmartSolarActivity
import com.example.proyectofct.ui.viewModel.FacturaListViewModel


class FacturaListFragment : Fragment() {
    private var _binding: FragmentFacturaListBinding? = null
    private var primeravez = true

    val binding get() = _binding!!
    private lateinit var adapter: FacturaAdapter
    val viewModel: FacturaListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFacturaListBinding.inflate(layoutInflater, container, false)
        binding.contenido.visibility = View.VISIBLE
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FacturaAdapter { mostrarPopupInformacion() }
        binding.listaFacturas.layoutManager = LinearLayoutManager(requireContext())
        binding.listaFacturas.adapter = adapter
        binding.progressbar.isVisible = true
        binding.listaFacturas.setHasFixedSize(true)
        binding.icFiltrar.setOnClickListener {
            viajarFragment(FiltrarFacturasFragment())
        }

        binding.btnBack.setOnClickListener {
            viajarActivity(PantallaPrincipalActivity())
        }
        val usarMock =
            arguments?.getBoolean("mock", false) ?: false // Obtener el valor del switch del bundle
        Log.i("mock", "valor del switch: $usarMock")
        if (primeravez) {
            viewModel.obtenerFacturas(usarMock)
            primeravez = false
        }

        // Observa el LiveData en el ViewModel
        viewModel.facturas.observe(requireActivity(), Observer { facturas ->
            // Actualiza la interfaz de usuario con la nueva lista de facturas
            adapter.updateList(facturas.map { it.toFacturaModel() })
            binding.progressbar.isVisible = false
            Log.d("facturas", "Ha entrado en el observer")
            Log.d("facturas", "facturas de viewmodel: ${facturas.toString()}")
            if (facturas.isEmpty()) {
                binding.txtNoResultados.isVisible = true
                Log.d("facturas", "ta vaciaaaaaaaaa")

            }
        })
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

    private fun viajarFragment(lugar: Fragment) {
        val fragment = lugar
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container_view, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun viajarActivity(lugar: Activity) {
        val intent = Intent(requireContext(), lugar::class.java)
        startActivity(intent)
    }
}