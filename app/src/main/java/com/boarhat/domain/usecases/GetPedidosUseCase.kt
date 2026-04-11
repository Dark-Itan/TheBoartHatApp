package com.boarhat.domain.usecases.pedido

import com.boarhat.domain.entities.Pedido
import com.boarhat.domain.repositories.PedidoRepository
import kotlinx.coroutines.flow.Flow

class GetPedidosUseCase(
    private val repository: PedidoRepository
) {
    operator fun invoke(): Flow<List<Pedido>> = repository.getPedidos()
}