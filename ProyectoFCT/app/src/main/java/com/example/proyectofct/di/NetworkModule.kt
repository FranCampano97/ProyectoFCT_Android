package com.example.proyectofct.di

import android.app.Application
import android.content.Context
import co.infinum.retromock.Retromock
import co.infinum.retromock.create
import com.example.proyectofct.core.ResourceBodyFactory
import com.example.proyectofct.data.FacturaRepository
import com.example.proyectofct.data.Repository
import com.example.proyectofct.data.database.dao.FacturaDao
import com.example.proyectofct.data.network.ApiService
import com.example.proyectofct.data.network.FacturaService
import com.example.proyectofct.data.network.KtorService
import com.example.proyectofct.data.network.MockService
import com.example.proyectofct.data.network.RetroMockService
import com.example.proyectofct.ui.view.Activity.FacturaListActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.kotlinx.serializer.KotlinxSerializer
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
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
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton  //para solo crear una instancia de retromock.
    @Provides
    fun provideRetroMock(retromock: Retromock): RetroMockService {
        return retromock.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitMock(retrofit: Retrofit, @ApplicationContext context: Context): Retromock {
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


    @Provides
    fun provideRepository(
        ktor: KtorService,
        api: FacturaService,
        facturaDao: FacturaDao,
        mockService: MockService
    ): Repository {
        return FacturaRepository(ktor, api, facturaDao, mockService)
    }

    @Singleton
    @Provides
    fun provideKtorClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.INFO
            }
        }
    }
}

