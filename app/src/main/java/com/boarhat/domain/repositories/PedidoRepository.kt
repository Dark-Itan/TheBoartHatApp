package com.boarhat.domain.repositories

import com.boarhat.domain.entities.Pedido
import kotlinx.coroutines.flow.Flow

interface PedidoRepository {
    fun getPedidos(): Flow<List<Pedido>>
    fun getPedidosPendientes(): Flow<List<Pedido>>
    suspend fun getPedidoById(id: Int): Pedido?
    suspend fun crearPedido(pedido: Pedido): Boolean
    suspend fun actualizarEstado(pedidoId: Int, nuevoEstado: String): Boolean
    suspend fun actualizarPago(pedidoId: Int, estado: String, metodoPago: String, montoRecibido: Double, cambio: Double): Boolean
}