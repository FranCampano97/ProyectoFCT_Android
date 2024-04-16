package com.example.proyectofct.di

import com.example.proyectofct.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//esta clase es para proveer servicios, en este caso tenemos retrofit.
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    //Retrofit
    @Singleton  //para solo crear una instancia de retrofit.
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://viewnextandroid.wiremockapi.cloud/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton  //para solo crear una instancia de retrofit.
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
