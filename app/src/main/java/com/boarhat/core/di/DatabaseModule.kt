package com.boarhat.core.di

import android.content.Context
import com.boarhat.data.datasources.local.BoarHatDatabase
import com.boarhat.data.datasources.local.PastelDao
import com.boarhat.data.datasources.local.PedidoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BoarHatDatabase {
        return BoarHatDatabase.getDatabase(context)
    }

    @Provides
    fun providePastelDao(database: BoarHatDatabase): PastelDao {
        return database.pastelDao()
    }

    @Provides
    fun providePedidoDao(database: BoarHatDatabase): PedidoDao {
        return database.pedidoDao()
    }
}