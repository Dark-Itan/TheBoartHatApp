package com.boarhat.features.admin.domain.entities

import java.util.Date

data class Pedido(
    val id: Int = 0,
    val clienteNombre: String,
    val clienteTelefono: String,
    val items: List<ItemPedido>,
    val total: Double,
    val anticipo50: Double = total / 2,
    val fecha: Date = Date(),
    val fechaRecoleccion: String,
    val estado: EstadoPedido = EstadoPedido.PENDIENTE,
    val metodoPago: MetodoPago = MetodoPago.TRANSFERENCIA,
    val comprobanteUrl: String? = null
)

data class ItemPedido(
    val pastelId: Int,
    val nombre: String,
    val cantidad: Int,
    val precioUnitario: Double,
    val subtotal: Double,
    val detallesAdicionales: String = ""
)

enum class EstadoPedido { PENDIENTE, PAGADO, PREPARANDO, ENTREGADO, CANCELADO }
enum class MetodoPago { EFECTIVO, TARJETA, TRANSFERENCIA }