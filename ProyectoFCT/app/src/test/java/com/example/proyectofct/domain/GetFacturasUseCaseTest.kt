package com.example.proyectofct.domain

import com.example.proyectofct.data.Repository
import com.example.proyectofct.domain.model.Factura
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetFacturasUseCaseTest {
    @Mock
    lateinit var repository: Repository
    lateinit var facturaUseCase: GetFacturasUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        facturaUseCase = GetFacturasUseCase(repository)
    }

    @Test
    fun `when switch is true, should return facturas from mock`() {
        runBlocking {
            // Arrange
            val facturasFromMock = listOf(
                Factura("pagada", 300.0f, "2024-04-28"),
                Factura("pendiente", 400.0f, "2024-04-27")
            )
            `when`(repository.getAllFacturasFromMock()).thenReturn(facturasFromMock)

            // Act
            val result = facturaUseCase.invoke(true, false)

            // Assert
            assertEquals(facturasFromMock, result)
        }
    }

    @Test
    fun `when no facturas from API, should return facturas from database`() {
        runBlocking {
            // Arrange
            val facturasFromDatabase = listOf(
                Factura("pagada", 50.5f, "2024-04-28"),
                Factura("pendiente", 45.0f, "2024-04-27")
            )
            `when`(repository.getAllFacturasFromApi()).thenReturn(emptyList())
            `when`(repository.getAllFacturasFromDatabase()).thenReturn(facturasFromDatabase)

            // Act
            val result = facturaUseCase.invoke(false, false)

            // Assert
            assertEquals(facturasFromDatabase, result)
        }
    }

    @Test
    fun `when no facturas from API or database, should return empty list`() {
        runBlocking {
            // Arrange
            `when`(repository.getAllFacturasFromApi()).thenReturn(emptyList())
            `when`(repository.getAllFacturasFromDatabase()).thenReturn(emptyList())

            // Act
            val result = facturaUseCase.invoke(false, false)

            // Assert
            assertEquals(emptyList<Factura>(), result)
        }
    }


}