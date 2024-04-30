package com.example.proyectofct.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.proyectofct.data.database.facturaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val FACTURA_DATABASE_NAME = "factura_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, facturaDatabase::class.java, FACTURA_DATABASE_NAME).build()


    @Singleton
    @Provides
    fun provideFacturaDao(db: facturaDatabase) = db.getFacturaDao()



}