package com.example.proyectofct.data

import com.example.proyectofct.data.database.dao.FacturaDao
import com.example.proyectofct.data.database.entities.Entity
import com.example.proyectofct.data.model.FacturaModel
import com.example.proyectofct.data.network.FacturaService
import com.example.proyectofct.domain.model.Factura
import com.example.proyectofct.domain.model.toDomain
import javax.inject.Inject

class FacturaRepository @Inject constructor(

    private val api: FacturaService,
    private val facturaDao: FacturaDao
) {
    suspend fun getAllFacturasFromApi(): List<Factura> {
        val response: List<FacturaModel> = api.CargarFacturas()
        return response.map { it.toDomain() }
    }

    suspend fun getAllFacturasFromDatabase(): List<Factura> {
        val response: List<Entity> = facturaDao.getAllFacturas()
        return response.map { it.toDomain() }
    }

    suspend fun insertFacturas(facturas: List<Entity>) {
        facturaDao.insertAll(facturas)
    }

    suspend fun clearFacturas() {
        facturaDao.clearFacturas()
    }

    //FILTRADO
    suspend fun getFacturasPorEstado(estado: String): List<Factura> {
        val response: List<Entity> = facturaDao.getFacturasPorEstado(estado)
        return response.map { it.toDomain() }
    }

}