package com.example.proyectofct.data.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofct.R
import com.example.proyectofct.data.database.entities.Entity

class FacturaAdapter(
    var facturasLista: List<FacturaModel> = emptyList(),
    //metodo cuando pulsemos en una factura
    private val onItemSelected: () -> Unit

) : RecyclerView.Adapter<FacturasViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturasViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return FacturasViewHolder(
            layoutInflater.inflate(
                R.layout.item_factura,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = facturasLista.size

    override fun onBindViewHolder(holder: FacturasViewHolder, position: Int) {
        val item = facturasLista[position]
        holder.bind(item,onItemSelected)
    }


    //metodo que actualiza una lista de tipo FacturaModel
    fun updateList(list: List<FacturaModel>) {
        facturasLista = list
        //y notifica que los datos han sido cambiados.Esta función ya está creada por defecto.
        notifyDataSetChanged()
    }




}
