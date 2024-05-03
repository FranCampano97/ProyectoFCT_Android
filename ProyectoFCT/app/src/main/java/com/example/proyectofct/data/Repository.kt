package com.example.proyectofct.data

import com.example.proyectofct.data.database.entities.Entity
import com.example.proyectofct.domain.model.Factura

interface Repository {
    suspend fun getAllFacturasFromApi(): List<Factura>
    suspend fun getAllFacturasFromDatabase(): List<Factura>
    suspend fun getAllFacturasFromMock(): List<Factura>

    suspend fun insertFacturas(facturas: List<Entity>) {
    }

    suspend fun clearFacturas() {
    }
    suspend fun getPrecioMayor():Float
}