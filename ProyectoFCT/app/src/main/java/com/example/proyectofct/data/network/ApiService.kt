package com.example.proyectofct.data.network

import android.util.Log
import com.example.proyectofct.data.model.FacturaModel
import com.example.proyectofct.data.model.Results
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

interface ApiService {
    @GET("facturas")
    suspend fun getFacturas(): Response<Results>
}