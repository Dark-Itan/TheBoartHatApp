package com.boarhat.features.admin.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {
    @Query("SELECT * FROM pedidos ORDER BY fecha DESC")
    fun getAllPedidos(): Flow<List<PedidoEntity>>

    @Query("SELECT * FROM pedidos WHERE estado = 'PENDIENTE' ORDER BY fecha DESC")
    fun getPedidosPendientes(): Flow<List<PedidoEntity>>

    @Query("SELECT * FROM pedidos WHERE id = :id")
    suspend fun getPedidoById(id: Int): PedidoEntity?

    @Insert
    suspend fun insertPedido(pedido: PedidoEntity): Long

    @Query("UPDATE pedidos SET estado = :nuevoEstado WHERE id = :id")
    suspend fun actualizarEstado(id: Int, nuevoEstado: String)

    // FUNCIÓN CORREGIDA: Eliminamos montoRecibido y cambio para evitar errores de compilación
    @Query("UPDATE pedidos SET estado = :nuevoEstado, metodoPago = :metodoPago WHERE id = :id")
    suspend fun actualizarPago(
        id: Int,
        nuevoEstado: String,
        metodoPago: String
    )

    @Delete
    suspend fun deletePedido(pedido: PedidoEntity)
}