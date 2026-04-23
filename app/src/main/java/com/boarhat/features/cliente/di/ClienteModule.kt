package com.boarhat.features.cliente.di

import com.boarhat.features.admin.domain.repositories.PedidoRepository
import com.boarhat.features.admin.domain.usecases.UpdatePastelUseCase
import com.boarhat.features.cliente.domain.usecases.CrearPedidoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClienteModule {

    @Provides
    @Singleton
    fun provideCrearPedidoUseCase(
        pedidoRepository: PedidoRepository,
        updateStockUseCase: UpdatePastelUseCase
    ): CrearPedidoUseCase {
        return CrearPedidoUseCase(pedidoRepository, updateStockUseCase)
    }
}