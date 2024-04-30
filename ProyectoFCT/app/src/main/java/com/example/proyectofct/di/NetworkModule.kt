package com.example.proyectofct.di

import android.app.Application
import android.content.Context
import co.infinum.retromock.Retromock
import co.infinum.retromock.create
import com.example.proyectofct.core.ResourceBodyFactory
import com.example.proyectofct.data.network.ApiService
import com.example.proyectofct.data.network.RetroMockService
import com.example.proyectofct.ui.view.Activity.FacturaListActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
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
            .baseUrl("https://viewnextandroid4.wiremockapi.cloud/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton  //para solo crear una instancia de retrofit.
    @Provides
    fun provideApiService( retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton  //para solo crear una instancia de retromock.
    @Provides
    fun provideRetroMock( retromock: Retromock): RetroMockService {
        return retromock.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitMock(retrofit: Retrofit, @ApplicationContext context:Context): Retromock {
        return Retromock.Builder()
            .retrofit(retrofit)
            .defaultBodyFactory(ResourceBodyFactory(context))
            .build()
    }

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ActivityContext
    @Singleton
    @Provides
    @ActivityContext
    fun provideContext(activity: FacturaListActivity): Context {
        return activity
    }
}
