package com.example.proyectofct.domain.model

import com.example.proyectofct.data.database.entities.Entity
import com.example.proyectofct.data.model.FacturaModel
import com.google.gson.annotations.SerializedName

//este sera el modelo para el domain.
data class Factura(
    val estado: String,
    val importe: Float,
    val fecha: String
)

//voy a mapear el modelo FacturaModel al modelo Factura que usará el domain.
fun FacturaModel.toDomain() = Factura(estado, importe, fecha)

fun Entity.toDomain() = Factura(estado, importe, fecha)

fun Factura.toFacturaModel() = FacturaModel(estado, importe, fecha)