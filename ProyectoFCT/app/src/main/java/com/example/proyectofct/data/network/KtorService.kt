package com.example.proyectofct.data.network

import android.util.Log
import com.example.proyectofct.data.model.FacturaKtor
import com.example.proyectofct.data.model.FacturaModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class KtorService @Inject constructor(private val client: HttpClient) {
    suspend fun CargarFacturas(): List<FacturaModel> {
        try {
            val response: HttpResponse = runBlocking {
                client.get("http://10.0.2.2:8080/facturas")
            }
            Log.i("ktor", "Response status: ${response.status}")
            Log.i("ktor", response.bodyAsText())

            if (response.status == HttpStatusCode.OK) {
                val json =
                    Json.decodeFromString<List<FacturaKtor>>(response.bodyAsText())
                        .map { it.toDomain() }
                return json ?: emptyList()
            } else {
                return emptyList()
            }
        } catch (e: Exception) {
            Log.i("ktor", e.message.toString())
            return emptyList()
        }

    }
}