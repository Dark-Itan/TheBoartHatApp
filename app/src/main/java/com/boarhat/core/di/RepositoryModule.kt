package com.boarhat.core.di

import com.boarhat.data.datasources.local.PastelDao
import com.boarhat.data.datasources.local.PedidoDao
import com.boarhat.data.repositories.PastelRepositoryImpl
import com.boarhat.data.repositories.PedidoRepositoryImpl
import com.boarhat.domain.repositories.PastelRepository
import com.boarhat.domain.repositories.PedidoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePastelRepository(pastelDao: PastelDao): PastelRepository {
        return PastelRepositoryImpl(pastelDao)
    }

    @Provides
    @Singleton
    fun providePedidoRepository(pedidoDao: PedidoDao): PedidoRepository {
        return PedidoRepositoryImpl(pedidoDao)
    }
}