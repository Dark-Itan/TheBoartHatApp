package com.boarhat.domain.repositories

import com.boarhat.domain.entities.Pastel
import kotlinx.coroutines.flow.Flow

interface PastelRepository {
    fun getPasteles(): Flow<List<Pastel>>
    suspend fun getPastelById(id: Int): Pastel?
    suspend fun addPastel(pastel: Pastel): Boolean
    suspend fun updatePastel(pastel: Pastel): Boolean
    suspend fun deletePastel(id: Int): Boolean
    suspend fun actualizarStock(id: Int, cantidad: Int): Boolean
}