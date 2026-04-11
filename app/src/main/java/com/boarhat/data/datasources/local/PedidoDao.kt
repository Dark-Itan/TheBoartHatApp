package com.boarhat.data.datasources.local

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

    @Query("UPDATE pedidos SET estado = :nuevoEstado, metodoPago = :metodoPago, montoRecibido = :montoRecibido, cambio = :cambio WHERE id = :id")
    suspend fun actualizarPago(id: Int, nuevoEstado: String, metodoPago: String, montoRecibido: Double, cambio: Double)
}