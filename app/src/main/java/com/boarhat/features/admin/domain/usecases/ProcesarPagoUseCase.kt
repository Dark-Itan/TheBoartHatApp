package com.boarhat.domain.usecases.pedido

import com.boarhat.features.admin.domain.entities.MetodoPago
import com.boarhat.features.admin.domain.repositories.PedidoRepository
import com.boarhat.features.admin.domain.repositories.PastelRepository
import javax.inject.Inject

class ProcesarPagoUseCase @Inject constructor(
    private val pedidoRepository: PedidoRepository,
    private val pastelRepository: PastelRepository
) {
    suspend operator fun invoke(
        pedidoId: Int,
        metodoPago: MetodoPago,
        montoRecibido: Double
    ): Result<Double> {
        val pedido = pedidoRepository.getPedidoById(pedidoId)

        return if (pedido != null) {
            // 1. Verificación de monto (Liquidación del saldo pendiente)
            // En el modelo nuevo, el cliente ya pagó el 50% (anticipo50)
            val saldoPendiente = pedido.total - pedido.anticipo50

            val cambio = when (metodoPago) {
                MetodoPago.EFECTIVO -> {
                    if (montoRecibido >= saldoPendiente) {
                        montoRecibido - saldoPendiente
                    } else {
                        return Result.failure(Exception("Monto insuficiente para liquidar el saldo ($$saldoPendiente)"))
                    }
                }
                else -> 0.0
            }

            // 2. Lógica de Stock
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

            // 3. Actualizar estado del pedido (CORRECCIÓN DE ARGUMENTOS)
            // Quitamos montoRecibido y cambio de la llamada al repositorio
            val actualizado = pedidoRepository.actualizarPago(
                pedidoId = pedidoId,
                estado = "ENTREGADO", // O el estado final que prefieras
                metodoPago = metodoPago.name
            )

            if (actualizado) {
                Result.success(cambio)
            } else {
                Result.failure(Exception("Error al registrar el pago"))
            }
        } else {
            Result.failure(Exception("Pedido no encontrado"))
        }
    }
}