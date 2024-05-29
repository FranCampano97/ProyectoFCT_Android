package com.example.proyectofct.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyectofct.data.database.entities.Entity

//esta clase almacena las consultas a la bbdd.
@Dao
interface FacturaDao {
    @Query("SELECT * FROM factura_table")
    suspend fun getAllFacturas(): List<Entity>

    //para insertar en la bbdd
    @Insert(onConflict = OnConflictStrategy.REPLACE) //en caso de tener el mismo id, reemplazar
    suspend fun insertAll(facturas: List<Entity>)

    //borrar todas las facturas.
    @Query("DELETE FROM factura_table")
    suspend fun clearFacturas()

    //FILTRADO
    @Query("SELECT * FROM factura_table WHERE descEstado = :estado")
    suspend fun getFacturasPorEstado(estado: String): List<Entity>

    @Query("SELECT * FROM factura_table WHERE importeOrdenacion BETWEEN 0 AND :importe")
    suspend fun getFacturasPorImporte(importe: Int): List<Entity>

    //valor mayor
    @Query("SELECT MAX(importeOrdenacion) FROM factura_table")
    suspend fun getPrecioMasAlto(): Float?


}