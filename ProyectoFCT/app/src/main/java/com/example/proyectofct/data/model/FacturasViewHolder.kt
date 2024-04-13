package com.example.proyectofct.data.model

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofct.databinding.ItemFacturaBinding

class FacturasViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemFacturaBinding.bind(view)


    fun bind(response: FacturaModel, onItemSelected: () -> Unit) {
        if (response.estado.equals("Pendiente de pago")) {
            binding.tvEstado.text = response.estado
        } else binding.tvEstado.text = ""

        binding.tvFecha.text = response.fecha
        val precio = response.importe
        binding.tvPrecio.text = "$precio €"

        binding.root.setOnClickListener {
            onItemSelected()

        }
    }
}