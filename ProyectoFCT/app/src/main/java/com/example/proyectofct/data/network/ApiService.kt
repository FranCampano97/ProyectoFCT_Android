package com.example.proyectofct.data.network

import com.example.proyectofct.data.model.Results
import retrofit2.Response
import retrofit2.http.GET

interface  ApiService {
    @GET("facturas")
    suspend fun getFacturas(): Response<Results>

}