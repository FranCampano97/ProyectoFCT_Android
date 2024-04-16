package com.example.proyectofct.domain

import com.example.proyectofct.data.FacturaRepository
import com.example.proyectofct.data.database.entities.toDatabase
import com.example.proyectofct.domain.model.Factura
import javax.inject.Inject

class GetFacturasUseCase @Inject constructor(private val repository: FacturaRepository) {
    suspend operator fun invoke(): List<Factura> {
        val facturas = repository.getAllFacturasFromApi()


        return if (facturas.isNotEmpty()) {
            repository.clearFacturas()
            repository.insertFacturas(facturas.map { it.toDatabase() })
            facturas
        } else {
            repository.getAllFacturasFromDatabase()
        }
    }
}