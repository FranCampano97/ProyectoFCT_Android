package com.example.proyectofct.ui.viewModel

import com.example.proyectofct.data.Repository
import com.example.proyectofct.domain.FiltradoUseCase
import com.example.proyectofct.domain.GetFacturasUseCase
import com.example.proyectofct.domain.MainDispatcherRule
import com.example.proyectofct.domain.model.Factura
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.Date

@ExperimentalCoroutinesApi
class FacturaListViewModelTest {

    @Mock
    lateinit var repository: Repository
    private lateinit var getFacturasUseCase: GetFacturasUseCase
    private lateinit var filtradoUseCase: FiltradoUseCase
    private lateinit var viewModel: FacturaListViewModel

    @get:Rule
    var mainDispatcherRule: MainDispatcherRule = MainDispatcherRule()

    @Before
    fun onBefore() {
        MockitoAnnotations.openMocks(this)
        getFacturasUseCase = GetFacturasUseCase(repository)
        filtradoUseCase = FiltradoUseCase(repository)
        viewModel = FacturaListViewModel(getFacturasUseCase, filtradoUseCase)
    }


    @Test
    fun `test obtenerFacturas`() = runTest {
        // Given
        val facturas = listOf(
            Factura("pagada", 45.0f, "15/07/2024"),
            Factura("pagada", 50.0f, "28/04/2024")
        )

        `when`(getFacturasUseCase.invoke(false)).thenReturn(facturas)

        // When
        viewModel.obtenerFacturas(false)
        advanceUntilIdle()
        // Then
        assertEquals(facturas, viewModel.facturas.value)
    }

    @Test
    fun `test filtrar`() = runTest {
        // Given
        val importe = 50.0f
        val pagada = true
        val pendiente = false
        val desde: Date? = null
        val hasta: Date? = null
        val facturasOriginales = listOf(
            Factura("Pagada", 45.0f, "15/07/2024"),
            Factura("pendiente", 60.0f, "28/04/2024")
        )
        val facturasFiltradas = listOf(
            Factura("Pagada", 45.0f, "15/07/2024")
        )

        `when`(getFacturasUseCase.invoke(false)).thenReturn(facturasOriginales)
        `when`(filtradoUseCase.filtrado(importe, pagada, pendiente, desde, hasta))
            .thenReturn(facturasFiltradas)

        // When
        viewModel.filtrar(importe, pagada, pendiente, desde, hasta)
        advanceUntilIdle()
        // Then
        assertEquals(facturasFiltradas, viewModel.facturas.value)
    }

    @Test
    fun `when getPrecioMayor`() = runTest {
        val precioMayor = 10.0f
        `when`(getFacturasUseCase.getPrecioMayor()).thenReturn(precioMayor)
        advanceUntilIdle()
        //then
        assertEquals(precioMayor, viewModel.getPrecioMayor())
    }

}