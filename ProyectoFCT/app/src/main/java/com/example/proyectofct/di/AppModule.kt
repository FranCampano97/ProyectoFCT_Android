package com.example.proyectofct.di

import com.example.proyectofct.data.FacturaRepository
import com.example.proyectofct.domain.GetFacturasUseCase
import com.example.proyectofct.ui.viewModel.FacturaListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/*
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFacturaListViewModel(repository: FacturaRepository): FacturaListViewModel {
        return FacturaListViewModel(repository)
    }
}
*/