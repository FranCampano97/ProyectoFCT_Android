package com.example.proyectofct.domain

import android.util.Log
import com.example.proyectofct.data.Repository
import com.example.proyectofct.data.database.entities.toDatabase
import com.example.proyectofct.domain.model.Factura
import javax.inject.Inject


class GetFacturasUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(mock: Boolean, ktor: Boolean): List<Factura> {
        return when {
            ktor -> {
                Log.i("FRAN", "Cargué de Ktor")
                val facturas = repository.getAllFacturasFromKtor() ?: emptyList()
                if (facturas.isNotEmpty()) {
                    repository.clearFacturas()
                    repository.insertFacturas(facturas.map { it.toDatabase() })
                    facturas
                } else {
                    repository.getAllFacturasFromDatabase()
                }
            }

            mock -> {
                Log.i("FRAN", "Cargué del Mock")
                val facturas = repository.getAllFacturasFromMock()
                repository.clearFacturas()
                repository.insertFacturas(facturas.map { it.toDatabase() })
                facturas
            }

            else -> {
                val facturas = repository.getAllFacturasFromApi() ?: emptyList()
                if (facturas.isNotEmpty()) {
                    Log.i("FRAN", "Cargué de la API")
                    repository.clearFacturas()
                    repository.insertFacturas(facturas.map { it.toDatabase() })
                    repository.getAllFacturasFromDatabase()
                } else {
                    Log.i("FRAN", "Cargué de la BBDD")
                    repository.getAllFacturasFromDatabase()
                }
            }
        }
    }


    suspend fun getPrecioMayor(): Float {
        return repository.getPrecioMayor()
    }
}