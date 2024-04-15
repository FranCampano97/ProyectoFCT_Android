package com.example.proyectofct.data.network

import android.annotation.SuppressLint
import android.util.Log
import com.example.proyectofct.core.RetrofitHelper
import com.example.proyectofct.data.model.Results
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit

class FacturaService {
    private val retrofit = RetrofitHelper.getRetrofit()


    suspend fun CargarFacturas(): Results? {
        val myResponse: Response<Results> =
            retrofit.create(ApiService::class.java).getFacturas()

        if (myResponse.isSuccessful) {
            val response: Results? = myResponse.body()
            //si no es null, en el hilo principal actualizamos la lista y quitamos la barra de cargando.
            if (response != null) {

                return response

            }

        }
        return null
    }

}