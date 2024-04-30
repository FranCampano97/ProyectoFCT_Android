package com.example.proyectofct.data.model

import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("CAU") val cau: String,
    @SerializedName("Estado solicitud alta autoconsumidor") val estadoSolicitud: String,
    @SerializedName("Tipo autoconsumo") val tipo: String,
    @SerializedName("excedentes") val excedentes: String,
    @SerializedName("potencia") val potencia: String
    )
