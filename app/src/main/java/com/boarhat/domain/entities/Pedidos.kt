package com.boarhat.domain.entities

import java.util.Date

data class Pedido(
    val id: Int = 0,
    val clienteNombre: String,
    val clienteTelefono: String,
    val items: List<ItemPedido>,
    val total: Double,
    val fecha: Date,
    val estado: EstadoPedido,  // PENDIENTE, PAGADO, PREPARANDO, ENTREGADO
    val metodoPago: MetodoPago,  // EFECTIVO, TARJETA, TRANSFERENCIA, PIX
    val montoRecibido: Double = 0.0,
    val cambio: Double = 0.0
)

data class ItemPedido(
    val pastelId: Int,
    val nombre: String,
    val cantidad: Int,
    val precioUnitario: Double,
    val subtotal: Double
)

enum class EstadoPedido {
    PENDIENTE,
    PAGADO,
    PREPARANDO,
    ENTREGADO,
    CANCELADO
}

enum class MetodoPago {
    EFECTIVO,
    TARJETA,
    TRANSFERENCIA,
    PIX
}