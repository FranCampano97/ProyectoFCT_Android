package com.example.proyectofct.data.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.example.proyectofct.data.model.Details
import com.example.proyectofct.data.model.Results
import retrofit2.Call
import retrofit2.http.GET

interface RetroMockService {
    @Mock
    @MockResponse(body = "nuevo.json")
    @GET("/")
    fun getFacturasMock(): Call<Results?>

    @Mock
    @MockResponse(body = "details.json")
    @GET("/")
    fun getDetails(): Call<Details?>
}