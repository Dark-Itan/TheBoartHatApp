package com.boarhat.data.repositories

import com.boarhat.data.datasources.local.PastelDao
import com.boarhat.data.mapper.toDomain
import com.boarhat.data.mapper.toEntity
import com.boarhat.domain.entities.Pastel
import com.boarhat.domain.repositories.PastelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PastelRepositoryImpl(
    private val pastelDao: PastelDao
) : PastelRepository {

    override fun getPasteles(): Flow<List<Pastel>> {
        return pastelDao.getAllPasteles().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getPastelById(id: Int): Pastel? {
        return pastelDao.getPastelById(id)?.toDomain()
    }

    override suspend fun addPastel(pastel: Pastel): Boolean = try {
        pastelDao.insertPastel(pastel.toEntity()) > 0
    } catch (e: Exception) {
        false
    }

    override suspend fun updatePastel(pastel: Pastel): Boolean = try {
        pastelDao.updatePastel(pastel.toEntity())
        true
    } catch (e: Exception) {
        false
    }

    override suspend fun deletePastel(id: Int): Boolean = try {
        val pastel = pastelDao.getPastelById(id)
        pastel?.let { pastelDao.deletePastel(it) }
        true
    } catch (e: Exception) {
        false
    }

    override suspend fun actualizarStock(id: Int, cantidad: Int): Boolean = try {
        pastelDao.reducirStock(id, cantidad) > 0
    } catch (e: Exception) {
        false
    }
}