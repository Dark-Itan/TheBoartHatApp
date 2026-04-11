package com.boarhat.domain.usecases.pedido

import com.boarhat.domain.entities.Pedido
import com.boarhat.domain.repositories.PedidoRepository
import com.boarhat.domain.usecases.pastel.UpdatePastelUseCase

class CrearPedidoUseCase(
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
            val pastel = com.boarhat.domain.entities.Pastel(
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