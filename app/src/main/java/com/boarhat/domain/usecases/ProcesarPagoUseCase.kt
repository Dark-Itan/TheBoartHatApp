package com.boarhat.domain.usecases.pedido

import com.boarhat.domain.entities.EstadoPedido
import com.boarhat.domain.entities.MetodoPago
import com.boarhat.domain.repositories.PedidoRepository
import com.boarhat.domain.repositories.PastelRepository // Necesario para el stock
import javax.inject.Inject

class ProcesarPagoUseCase @Inject constructor(
    private val pedidoRepository: PedidoRepository,
    private val pastelRepository: PastelRepository // Inyectamos el repositorio de pasteles
) {
    suspend operator fun invoke(
        pedidoId: Int,
        metodoPago: MetodoPago,
        montoRecibido: Double
    ): Result<Double> {
        val pedido = pedidoRepository.getPedidoById(pedidoId)

        return if (pedido != null) {
            // 1. Verificación de monto (solo para efectivo)
            val cambio = when (metodoPago) {
                MetodoPago.EFECTIVO -> {
                    if (montoRecibido >= pedido.total) {
                        montoRecibido - pedido.total
                    } else {
                        return Result.failure(Exception("Monto recibido insuficiente"))
                    }
                }
                else -> 0.0
            }

            // 2. LÓGICA DE STOCK (MVP 03): Descontar productos del inventario
            // Iteramos sobre los items del pedido para actualizar el stock real
            pedido.items.forEach { item ->
                val pastel = pastelRepository.getPastelById(item.pastelId)
                if (pastel != null) {
                    val nuevoStock = pastel.stock - item.cantidad
                    if (nuevoStock < 0) {
                        return Result.failure(Exception("Stock insuficiente para ${pastel.nombre}"))
                    }
                    pastelRepository.updatePastel(pastel.copy(stock = nuevoStock))
                }
            }

            // 3. Actualizar estado del pedido a PAGADO
            val actualizado = pedidoRepository.actualizarPago(
                pedidoId,
                EstadoPedido.PAGADO.name,
                metodoPago.name,
                montoRecibido,
                cambio
            )

            if (actualizado) {
                Result.success(cambio)
            } else {
                Result.failure(Exception("Error al registrar el pago en la base de datos"))
            }
        } else {
            Result.failure(Exception("Pedido no encontrado"))
        }
    }
}