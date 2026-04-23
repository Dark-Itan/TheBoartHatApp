package com.boarhat.features.cliente.domain.usecases

import com.boarhat.domain.usecases.pastel.UpdatePastelUseCase
import com.boarhat.features.admin.domain.entities.Pastel
import com.boarhat.features.admin.domain.entities.Pedido
import com.boarhat.features.admin.domain.repositories.PedidoRepository
import javax.inject.Inject

class CrearPedidoUseCase @Inject constructor(
    private val pedidoRepository: PedidoRepository,
    private val updateStockUseCase: UpdatePastelUseCase
) {
    suspend operator fun invoke(pedido: Pedido): Boolean {
        return if (validarPedido(pedido) && actualizarStock(pedido)) {
            pedidoRepository.crearPedido(pedido)
        } else false
    }

    private fun validarPedido(pedido: Pedido): Boolean {
        return pedido.clienteNombre.isNotBlank() &&
                pedido.items.isNotEmpty() &&
                pedido.total > 0
    }

    private suspend fun actualizarStock(pedido: Pedido): Boolean {
        for (item in pedido.items) {
            val pastel = Pastel(
                id = item.pastelId,
                nombre = "",
                descripcion = "",
                precio = 0.0,
                stock = -item.cantidad,
                imagenUrl = "",
                categoria = "",
                ingredientes = emptyList()
            )
            if (!updateStockUseCase(pastel)) return false
        }
        return true
    }
}