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
        desde: Date?,
        hasta: Date?
    ) {
        viewModelScope.launch {
            try {
                val facturasFiltradas =
                    filtradoUseCase.filtrado(importe, pagada, pendiente, desde, hasta)
                _facturas.value = facturasFiltradas
            } catch (e: Exception) {
                _error.value = "Error al filtrar las facturas: ${e.message}"
                Log.e("FRAN", "Error al filtrar las facturas: ${e.message}")
            }
        }
    }

    /*
        fun filtrado(importe: Float, pagada: Boolean, pendiente: Boolean, desde: Date?, hasta: Date?) {
            val listaFiltrada = mutableListOf<Entity>()
            val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
            var porImporte = false
            var dentroRango = false
            var porFecha = false
            var porBotones = false
            Log.i("facturas", "desde: $desde, hasta: $hasta")
            Log.i("facturas", "el importe es: $importe")

            for (i in facturasLista) {
                porBotones = if (pagada || pendiente) {
                    true
                } else false
                dentroRango = false
                porImporte = false
                val fechaFactura = formatoFecha.parse(i.fecha)
                porFecha = if (desde != null && hasta != null) {
                    true
                } else false
                val cumpleFechas = if (desde != null && hasta != null) {
                    fechaFactura in desde..hasta
                } else false
                //si se ha modificado el rango de importe.
                porImporte = if (importe > 0f) true
                else false
                //si esta dentro del rango del importe.
                if (i.importe > 0 && i.importe <= importe) {
                    dentroRango = true
                } else false


                val porChecks = if (!pendiente && pagada && i.estado == "Pagada") {
                    Log.i("facturas", "entro en la 1 de pagada")
                    true
                } else if (!pagada && pendiente && i.estado == "Pendiente de pago") {
                    Log.i("facturas", "entro en la 2 de pendiente de pago")
                    true
                } else {
                    false
                }

                //si es por importe solamente.
                if (porImporte && dentroRango && !porChecks && !porBotones && !cumpleFechas) {
                    Log.i("facturas", "tambien entro aqui jejeje")
                    listaFiltrada.add(i)
                }

                //si cumpleFecha solamente.
                if (cumpleFechas && !porChecks && !porImporte && !porBotones) {
                    Log.i("facturas", "pendiente: $pendiente, pagada:$pagada, porChecks: $porChecks")
                    Log.i("facturas", "entro en cumpleFecha solamente.")
                    listaFiltrada.add(i)
                }

                //si cumpleFecha y checks.
                if (cumpleFechas && porChecks && !porImporte && porBotones) {
                    Log.i("facturas", "entro en cumpleFecha y checks.")
                    listaFiltrada.add(i)
                }

                //si cumple porChecks solamente.
                if (porChecks && porBotones && !porImporte && !cumpleFechas && !porFecha) {
                    Log.i("facturas", "entra en cumplechecks solamente")
                    Log.i("facturas", "el porChecks es: $porChecks")
                    listaFiltrada.add(i)
                }

                //filtrado por importe y fecha.
                if (cumpleFechas && porImporte && dentroRango && !porChecks && !porBotones) {
                    Log.i("facturas", "entra en por importe y fecha.")
                    listaFiltrada.add(i)
                }

                //Filtrado por importe y checks.
                if (porImporte && porChecks && porBotones && dentroRango && !cumpleFechas && !porFecha) {
                    Log.i("facturas", "entra en cumplechecks y importe. fecha: $cumpleFechas")
                    Log.i(
                        "facturas",
                        "por importe: $porImporte, por checks: $porChecks,  por botones: $porBotones, dentrorango: $dentroRango"
                    )
                    listaFiltrada.add(i)
                }
                //Filtrado por importe, checks y fecha.
                if (porImporte && porChecks && dentroRango && cumpleFechas) {
                    Log.i("facturas", "entra en cumplechecks, importe y fecha.")
                    listaFiltrada.add(i)
                }

                if (listaFiltrada.isEmpty()) {
                    Log.i("noHay", "no hay facturas.")
                }
            }
            Log.i("facturas", "listado filtrado: $listaFiltrada")
            viewModelScope.launch {
                _facturas.value = listaFiltrada.map { it.toDomain() }
            }
        }
    */

    suspend fun getPrecioMayor(): Float {
        return getFacturasUseCase.getPrecioMayor()
    }
}