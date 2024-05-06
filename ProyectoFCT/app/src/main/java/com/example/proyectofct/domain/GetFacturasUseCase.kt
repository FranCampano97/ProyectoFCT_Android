package com.example.proyectofct.domain

import android.util.Log
import com.example.proyectofct.data.FacturaRepository
import com.example.proyectofct.data.Repository
import com.example.proyectofct.data.database.entities.toDatabase
import com.example.proyectofct.domain.model.Factura
import javax.inject.Inject


class GetFacturasUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(switch: Boolean): List<Factura> {
        return if (!switch) {
            val facturas = repository.getAllFacturasFromApi()
            if (facturas.isNotEmpty()) {
                Log.i("FRAN", "Cargué de la API")
                repository.clearFacturas()
                repository.insertFacturas(facturas.map { it.toDatabase() })
                repository.getAllFacturasFromDatabase()
            } else {
                Log.i("FRAN", "Cargué de la BBDD")
                repository.getAllFacturasFromDatabase()
            }
        } else {
            Log.i("FRAN", "Cargué del Mock")
            val facturas = repository.getAllFacturasFromMock()
            repository.clearFacturas()
            repository.insertFacturas(facturas.map { it.toDatabase() })
            facturas
        }
    }

    suspend fun getPrecioMayor(): Float {
        return repository.getPrecioMayor()
    }
}