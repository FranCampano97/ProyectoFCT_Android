package com.example.proyectofct.data.network

import android.annotation.SuppressLint
import android.util.Log
import com.example.proyectofct.core.RetrofitHelper
import com.example.proyectofct.data.model.FacturaModel
import com.example.proyectofct.data.model.Results
import com.example.proyectofct.domain.model.Factura
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class FacturaService @Inject constructor(private val api: ApiService) {
    suspend fun CargarFacturas(): List<FacturaModel> {
        return withContext(Dispatchers.IO) {
            Log.i("FRAN", "estoy en FacturaService.CargarFacturas()")
            try {
                val response = api.getFacturas()
                Log.i("FRAN", "Response code: ${response.code()}")
                if (response.isSuccessful) {
                    val body = response.body()?.facturas
                    body ?: emptyList()
                } else {
                    Log.e("FRAN", "Response unsuccessful: ${response.code()} - ${response.message()}")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("FRAN", "Exception during API call: ${e.message}", e)
                emptyList()
            }
        }
    }


}