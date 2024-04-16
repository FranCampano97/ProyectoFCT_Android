package com.example.proyectofct.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.proyectofct.domain.model.Factura
import com.google.gson.annotations.SerializedName

@Entity(tableName = "factura_table")
data class Entity(
    @PrimaryKey(autoGenerate = true) //ponemos un id a cada factura.
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "descEstado") val estado: String,
    @ColumnInfo(name = "importeOrdenacion") val importe: Float,
    @ColumnInfo(name = "fecha") val fecha: String
)

fun Factura.toDatabase() = Entity(estado = estado, importe = importe, fecha = fecha)