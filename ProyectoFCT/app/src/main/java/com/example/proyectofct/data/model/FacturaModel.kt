package com.example.proyectofct.data.model

import com.google.gson.annotations.SerializedName

data class FacturaModel(
    @SerializedName("descEstado") val estado: String,
    @SerializedName("importeOrdenacion") val importe: Float,
    @SerializedName("fecha") val fecha: String
)