package com.boarhat.features.admin.data.repositories

import com.boarhat.features.admin.data.local.PedidoDao
import com.boarhat.features.admin.data.mapper.toDomain
import com.boarhat.features.admin.data.mapper.toEntity
import com.boarhat.features.admin.domain.entities.Pedido
import com.boarhat.features.admin.domain.repositories.PedidoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PedidoRepositoryImpl(
    private val pedidoDao: PedidoDao
) : PedidoRepository {

    override fun getPedidos(): Flow<List<Pedido>> =
        pedidoDao.getAllPedidos().map { entities -> entities.map { it.toDomain() } }

    override fun getPedidosPendientes(): Flow<List<Pedido>> =
        pedidoDao.getPedidosPendientes().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getPedidoById(id: Int): Pedido? =
        pedidoDao.getPedidoById(id)?.toDomain()

    override suspend fun crearPedido(pedido: Pedido): Boolean = try {
        pedidoDao.insertPedido(pedido.toEntity()) > 0
    } catch (e: Exception) { false }

    override suspend fun actualizarEstado(pedidoId: Int, nuevoEstado: String): Boolean = try {
        pedidoDao.actualizarEstado(pedidoId, nuevoEstado)
        true
    } catch (e: Exception) { false }

    // Implementación corregida para quitar 'montoRecibido' y 'cambio'
    override suspend fun actualizarPago(pedidoId: Int, estado: String, metodoPago: String): Boolean = try {
        pedidoDao.actualizarPago(pedidoId, estado, metodoPago)
        true
    } catch (e: Exception) { false }
}