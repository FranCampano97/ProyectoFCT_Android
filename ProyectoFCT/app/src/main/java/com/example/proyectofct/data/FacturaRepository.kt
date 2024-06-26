package com.example.proyectofct.data

import com.example.proyectofct.data.database.dao.FacturaDao
import com.example.proyectofct.data.database.entities.Entity
import com.example.proyectofct.data.model.FacturaModel
import com.example.proyectofct.data.network.FacturaService
import com.example.proyectofct.data.network.KtorService
import com.example.proyectofct.data.network.MockService
import com.example.proyectofct.domain.model.Factura
import com.example.proyectofct.domain.model.toDomain
import javax.inject.Inject

class FacturaRepository @Inject constructor(
    private val ktor: KtorService,
    private val api: FacturaService,
    private val facturaDao: FacturaDao,
    private val mockService: MockService
) : Repository {
    override suspend fun getAllFacturasFromKtor(): List<Factura> {
        val response: List<FacturaModel> = ktor.CargarFacturas()
        return response.map { it.toDomain() }
    }

    override suspend fun getAllFacturasFromApi(): List<Factura> {
        val response: List<FacturaModel> = api.CargarFacturas()
        return response.map { it.toDomain() }
    }

    override suspend fun getAllFacturasFromDatabase(): List<Factura> {
        val response: List<Entity> = facturaDao.getAllFacturas()
        return response.map { it.toDomain() }
    }

    override suspend fun insertFacturas(facturas: List<Entity>) {
        facturaDao.insertAll(facturas)
    }

    override suspend fun clearFacturas() {
        facturaDao.clearFacturas()
    }


    override suspend fun getAllFacturasFromMock(): List<Factura> {
        val response: List<FacturaModel> = mockService.getFacturasMock()
        return response.map { it.toDomain() }
    }

    override suspend fun getPrecioMayor(): Float? {
        return facturaDao.getPrecioMasAlto()
    }
}