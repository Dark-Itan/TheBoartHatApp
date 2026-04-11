package com.boarhat.data.datasources.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PastelDao {
    // Quitamos "WHERE disponible = 1" porque no existe en la Entity
    @Query("SELECT * FROM pasteles ORDER BY nombre")
    fun getAllPasteles(): Flow<List<PastelEntity>>

    @Query("SELECT * FROM pasteles WHERE id = :id")
    suspend fun getPastelById(id: Int): PastelEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPastel(pastel: PastelEntity): Long

    @Update
    suspend fun updatePastel(pastel: PastelEntity)

    @Delete
    suspend fun deletePastel(pastel: PastelEntity)

    @Query("UPDATE pasteles SET stock = stock - :cantidad WHERE id = :id AND stock >= :cantidad")
    suspend fun reducirStock(id: Int, cantidad: Int): Int

    @Query("SELECT * FROM pasteles WHERE stock <= 5 AND stock > 0")
    fun getPastelesConPocoStock(): Flow<List<PastelEntity>>
}