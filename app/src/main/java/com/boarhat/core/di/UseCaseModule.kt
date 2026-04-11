package com.boarhat.core.di

import com.boarhat.domain.repositories.PastelRepository
import com.boarhat.domain.repositories.PedidoRepository
import com.boarhat.domain.usecases.pastel.*
import com.boarhat.domain.usecases.pedido.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    // --- PASTEL USE CASES ---
    @Provides
    @Singleton
    fun provideGetPastelesUseCase(repository: PastelRepository): GetPastelesUseCase {
        return GetPastelesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddPastelUseCase(repository: PastelRepository): AddPastelUseCase {
        return AddPastelUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdatePastelUseCase(repository: PastelRepository): UpdatePastelUseCase {
        return UpdatePastelUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeletePastelUseCase(repository: PastelRepository): DeletePastelUseCase {
        return DeletePastelUseCase(repository)
    }

    // --- PEDIDO USE CASES ---
    @Provides
    @Singleton
    fun provideCrearPedidoUseCase(
        pedidoRepository: PedidoRepository,
        updateStockUseCase: UpdatePastelUseCase
    ): CrearPedidoUseCase {
        return CrearPedidoUseCase(pedidoRepository, updateStockUseCase)
    }

    @Provides
    @Singleton
    fun provideGetPedidosUseCase(repository: PedidoRepository): GetPedidosUseCase {
        return GetPedidosUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPedidosPendientesUseCase(repository: PedidoRepository): GetPedidosPendientesUseCase {
        return GetPedidosPendientesUseCase(repository)
    }

    // CORRECCIÓN AQUÍ: Ahora pasamos ambos repositorios para cumplir con el MVP 03 (Control de Stock)
    @Provides
    @Singleton
    fun provideProcesarPagoUseCase(
        pedidoRepository: PedidoRepository,
        pastelRepository: PastelRepository
    ): ProcesarPagoUseCase {
        return ProcesarPagoUseCase(pedidoRepository, pastelRepository)
    }
}