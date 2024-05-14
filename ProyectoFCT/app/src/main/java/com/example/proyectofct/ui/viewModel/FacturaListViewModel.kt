package com.example.proyectofct.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofct.domain.FiltradoUseCase
import com.example.proyectofct.domain.GetFacturasUseCase
import com.example.proyectofct.domain.model.Factura
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FacturaListViewModel @Inject constructor(
    private val getFacturasUseCase: GetFacturasUseCase,
    private val filtradoUseCase: FiltradoUseCase
) : ViewModel() {

    /* init{
        Log.d("facturas", "FacturaListViewModel_init")
         viewModelScope.launch {
            val facturasResult  = getFacturasUseCase.invoke(false)
            facturasLista = facturasResult
            withContext(Dispatchers.Main) {
                _facturas.value = facturasResult
            }
            Log.i("facturas", "la primera vez $facturasLista")
        }
    }*/

    private val _facturas = MutableStateFlow<List<Factura>>(emptyList())
    val facturas: StateFlow<List<Factura>> = _facturas
    lateinit var facturasLista: List<Factura>
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun obtenerFacturas(mock: Boolean) {
        Log.d("LISTA", "ENTRA EN OBTENER")
        viewModelScope.launch {
            try {
                runCatching { getFacturasUseCase.invoke(mock) }.onSuccess {
                    facturasLista = it
                    _facturas.value = it
                }.onFailure {
                    _facturas.value = emptyList()
                    facturasLista = emptyList()
                }

            } catch (e: Exception) {
                _error.value = "Error al obtener las facturas: ${e.message}"
                Log.e("FRAN", "Error al obtener las facturas: ${e.message}")
            }
        }
    }

    fun filtrar(
        importe: Float,
        pagada: Boolean,
        pendiente: Boolean,
        anulada: Boolean,
        cuotaFija: Boolean,
        planPago: Boolean,
        desde: Date?,
        hasta: Date?
    ) {
        viewModelScope.launch {
            try {
                val facturasFiltradas =
                    filtradoUseCase.filtrado(
                        importe,
                        pagada,
                        pendiente,
                        anulada,
                        cuotaFija,
                        planPago,
                        desde,
                        hasta
                    )
                _facturas.value = facturasFiltradas
            } catch (e: Exception) {
                _error.value = "Error al filtrar las facturas: ${e.message}"
                Log.e("FRAN", "Error al filtrar las facturas: ${e.message}")
            }
        }
    }

    suspend fun getPrecioMayor(): Float {
        return getFacturasUseCase.getPrecioMayor()
    }
}