package com.boarhat.domain.usecases.pedido

import com.boarhat.features.admin.domain.entities.Pedido
import com.boarhat.features.admin.domain.repositories.PedidoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPedidosPendientesUseCase @Inject constructor(
    private val repository: PedidoRepository
) {
    operator fun invoke(): Flow<List<Pedido>> = repository.getPedidosPendientes()
}