package com.example.proyectofct.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofct.data.FacturaRepository
import com.example.proyectofct.domain.GetFacturasUseCase
import com.example.proyectofct.domain.model.Factura
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class FacturaListViewModel  @Inject constructor(
    private val getFacturasUseCase: GetFacturasUseCase,
    private val repository: FacturaRepository
) : ViewModel() {

    init {
            Log.d("facturas", "FacturaListViewModel_init")
    }

    private val _facturas = MutableLiveData<List<Factura>>()
    val facturas: LiveData<List<Factura>> = _facturas

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun obtenerFacturas() {
        Log.d("LISTA", "ENTRA EN OBTENER")
        viewModelScope.launch {
            try {
                val facturasResult = getFacturasUseCase.invoke()
                _facturas.value = facturasResult
            } catch (e: Exception) {
                _error.value = "Error al obtener las facturas: ${e.message}"
            }
        }
    }

    fun getFacturasPorEstado(estado: String) {
        viewModelScope.launch {
            try {
                val facturas = repository.getFacturasPorEstado(estado)
                _facturas.value=facturas
                Log.i("facturas","desde getFacturas $facturas")
            } catch (e: Exception) {
                _error.postValue("Error al obtener las facturas por estado: ${e.message}")
            }
        }
    }

}