package com.example.proyectofct.data.model

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("numFacturas") val numFacturas: String,
    @SerializedName("facturas") val facturas: List<FacturaModel>
)
