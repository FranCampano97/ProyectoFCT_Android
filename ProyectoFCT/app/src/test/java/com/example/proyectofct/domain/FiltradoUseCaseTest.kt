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
import java.text.SimpleDateFormat

class FiltradoUseCaseTest {
    @Mock
    lateinit var repository: Repository
    lateinit var filtradoUseCase: FiltradoUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        filtradoUseCase = FiltradoUseCase(repository)
    }

    @Test
    fun `when no facturas available, should return empty list`() {
        runBlocking {
            // Arrange
            `when`(repository.getAllFacturasFromDatabase()).thenReturn(emptyList())

            // Act
            val result = filtradoUseCase.filtrado(
                importe = 0.0f,
                pagada = true,
                pendiente = false,
                desde = null,
                hasta = null
            )

            // Assert
            assertEquals(emptyList<Factura>(), result)
        }
    }

    @Test //filtrado por importe
    fun `when facturas filtered by importe, should return correct list`() {
        runBlocking {

            val facturasFromDatabase = listOf(
                Factura("pagada", 50.0f, "28/04/2024"),
                Factura("pendiente", 45.0f, "27/04/2024"),
                Factura("pagada", 60.0f, "26/04/2024")
            )

            `when`(repository.getAllFacturasFromDatabase()).thenReturn(facturasFromDatabase)

            // Act
            val result = filtradoUseCase.filtrado(
                importe = 50.0f,
                pagada = false,
                pendiente = false,
                desde = null,
                hasta = null
            )

            // Assert
            val expectedList = listOf(
                Factura("pagada", 50.0f, "28/04/2024"),
                Factura("pendiente", 45.0f, "27/04/2024")
            )
            assertEquals(expectedList, result)
        }
    }

    @Test  //filtrado por estado.
    fun `when facturas filtered by estado, should return the correct list`() {
        runBlocking {

            val facturasFromDatabase = listOf(
                Factura("Pagada", 00.0f, "28/04/2024"),
                Factura("Pendiente de pago", 00.0f, "27/04/2024"),
                Factura("Pagada", 00.0f, "26/04/2024")
            )

            `when`(repository.getAllFacturasFromDatabase()).thenReturn(facturasFromDatabase)


            val result = filtradoUseCase.filtrado(
                importe = 00.0f,
                pagada = true,
                pendiente = false,
                desde = null,
                hasta = null
            )

            val expectedList = listOf(
                Factura("Pagada", 00.0f, "28/04/2024"),
                Factura("Pagada", 00.0f, "26/04/2024")
            )
            assertEquals(expectedList, result)
        }
    }

    @Test  //filtrado por fecha.
    fun `when facturas filtered by fecha, should return the correct list`() {
        runBlocking {
            val facturasFromDatabase = listOf(
                Factura("Pagada", 00.0f, "28/04/2024"),
                Factura("Pendiente de pago", 00.0f, "27/04/2024"),
                Factura("Pagada", 00.0f, "26/04/2024")
            )
            `when`(repository.getAllFacturasFromDatabase()).thenReturn(facturasFromDatabase)

            val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
            val desde = formatoFecha.parse("26/04/2024")
            val hasta = formatoFecha.parse("27/04/2024")
            val result = filtradoUseCase.filtrado(
                importe = 00.0f,
                pagada = false,
                pendiente = false,
                desde = desde,
                hasta = hasta
            )

            val expectedList = listOf(
                Factura("Pendiente de pago", 00.0f, "27/04/2024"),
                Factura("Pagada", 00.0f, "26/04/2024")
            )
            assertEquals(expectedList, result)
        }
    }

    @Test //filtrado por importe estado y fecha.
    fun `when facturas filtered by importe, estado and fecha should return the correct list`() {
        runBlocking {
            val facturasFromDatabase = listOf(
                Factura("Pagada", 15.0f, "28/04/2024"),
                Factura("Pendiente de pago", 22.0f, "27/04/2024"),
                Factura("Pagada", 18.0f, "26/04/2024")
            )

            `when`(repository.getAllFacturasFromDatabase()).thenReturn(facturasFromDatabase)

            val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
            val desde = formatoFecha.parse("26/04/2024")
            val hasta = formatoFecha.parse("28/04/2024")

            val result = filtradoUseCase.filtrado(
                importe = 25.0f,
                pagada = true,
                pendiente = false,
                desde = desde,
                hasta = hasta
            )
            val expectedList = listOf(
                Factura("Pagada", 15.0f, "28/04/2024"),
                Factura("Pagada", 18.0f, "26/04/2024")
            )
            assertEquals(expectedList, result)
        }
    }

    @Test //prueba de rendimiento.
    fun testFiltradoConGranCantidadDeFacturas() {
        runBlocking {

            val facturas = generarFacturas(1000000)
            `when`(repository.getAllFacturasFromDatabase()).thenReturn(facturas)

            val result = filtradoUseCase.filtrado(
                importe = 0f,
                pagada = true,
                pendiente = false,
                desde = null,
                hasta = null,
            )
            assertEquals(1000000, result.size)
        }
    }

    @Test //filtrado por importe y estado.
    fun `when facturas filtered by importe and estado should return the correct list`() {
        runBlocking {
            val facturasFromDatabase = listOf(
                Factura("Pagada", 15.0f, "28/04/2024"),
                Factura("Pendiente de pago", 22.0f, "27/04/2024"),
                Factura("Pagada", 18.0f, "26/04/2024")
            )

            `when`(repository.getAllFacturasFromDatabase()).thenReturn(facturasFromDatabase)

            val result = filtradoUseCase.filtrado(
                importe = 25.0f,
                pagada = true,
                pendiente = false,
                desde = null,
                hasta = null
            )
            val expectedList = listOf(
                Factura("Pagada", 15.0f, "28/04/2024"),
                Factura("Pagada", 18.0f, "26/04/2024")
            )
            assertEquals(expectedList, result)
        }
    }

    @Test //filtrado por importe y fecha.
    fun `when facturas filtered by importe and fecha should return the correct list`() {
        runBlocking {
            val facturasFromDatabase = listOf(
                Factura("Pagada", 15.0f, "28/04/2024"),
                Factura("Pendiente de pago", 22.0f, "27/04/2024"),
                Factura("Pagada", 18.0f, "26/04/2024")
            )
            `when`(repository.getAllFacturasFromDatabase()).thenReturn(facturasFromDatabase)
            val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
            val desde = formatoFecha.parse("26/04/2024")
            val hasta = formatoFecha.parse("28/04/2024")
            val result = filtradoUseCase.filtrado(
                importe = 25.0f,
                pagada = false,
                pendiente = false,
                desde = desde,
                hasta = hasta
            )
            val expectedList = listOf(
                Factura("Pagada", 15.0f, "28/04/2024"),
                Factura("Pendiente de pago", 22.0f, "27/04/2024"),
                Factura("Pagada", 18.0f, "26/04/2024")
            )
            assertEquals(expectedList, result)
        }
    }

    @Test //filtrado por estado y fecha.
    fun `when facturas filtered by estado and fecha should return the correct list`() {
        runBlocking {
            val facturasFromDatabase = listOf(
                Factura("Pendiente de pago", 00.0f, "27/04/2024"),
                Factura("Pendiente de pago", 00.0f, "14/04/2024"),
                Factura("Pagada", 00.0f, "28/04/2024"),
                Factura("Pagada", 00.0f, "26/04/2024"),
                Factura("Pagada", 00.0f, "30/04/2024")
            )
            `when`(repository.getAllFacturasFromDatabase()).thenReturn(facturasFromDatabase)
            val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
            val desde = formatoFecha.parse("14/04/2024")
            val hasta = formatoFecha.parse("27/04/2024")
            val result = filtradoUseCase.filtrado(
                importe = 00.0f,
                pagada = false,
                pendiente = true,
                desde = desde,
                hasta = hasta
            )
            val expectedList = listOf(
                Factura("Pendiente de pago", 00.0f, "27/04/2024"),
                Factura("Pendiente de pago", 00.0f, "14/04/2024")

            )
            assertEquals(expectedList, result)
        }
    }

    @Test //cuando no filtras por nada
    fun `when  do not filter anything`() {
        runBlocking {
            `when`(repository.getAllFacturasFromDatabase()).thenReturn(emptyList())

            val result = filtradoUseCase.filtrado(
                importe = 0.0f,
                pagada = false,
                pendiente = false,
                desde = null,
                hasta = null
            )

            assertEquals(emptyList<Factura>(), result)
        }
    }

    private fun generarFacturas(cantidad: Int): List<Factura> {
        return List(cantidad) {
            Factura("Pagada", 15.0f, "28/04/2024")
        }
    }
}
