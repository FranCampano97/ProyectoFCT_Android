package com.example.proyectofct.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FacturaKtor(
    val estado: String,
    val importe: Float,
    val fecha: String
) {
    fun toDomain(): FacturaModel {
        return FacturaModel(estado = this.estado, importe = this.importe, fecha = this.fecha)

    }
}