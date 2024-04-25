package com.example.proyectofct.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.proyectofct.data.database.dao.FacturaDao
import com.example.proyectofct.data.database.entities.Entity
import com.example.proyectofct.domain.model.DateConverter


//es la base de datos en sí.
@Database(entities = [Entity::class], version = 1) //la version de nuestra bbdd.
@TypeConverters(DateConverter::class)
abstract class facturaDatabase : RoomDatabase() {
    //esto vamos a inyectarlo con dagger
    abstract fun getFacturaDao(): FacturaDao

}