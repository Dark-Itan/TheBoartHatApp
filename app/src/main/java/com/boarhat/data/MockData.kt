package com.boarhat.data

import com.boarhat.domain.entities.*
import java.util.Date

object MockData {
    // UNICA declaración de listaPasteles
    val listaPasteles = mutableListOf(
        Pastel(
            id = 1,
            nombre = "Pastel de Chocolate",
            precio = 350.0,
            stock = 10,
            descripcion = "Chocolate amargo 70% cacao",
            imagenUrl = "https://images.unsplash.com/photo-1578985545062-69928b1d9587",
            categoria = "Chocolates",
            ingredientes = listOf("Cacao", "Leche", "Harina")
        ),
        Pastel(
            id = 2,
            nombre = "Red Velvet",
            precio = 400.0,
            stock = 5,
            descripcion = "Clásico con betún de queso crema",
            imagenUrl = "https://images.unsplash.com/photo-1586788680434-30d3246718d0",
            categoria = "Especiales",
            ingredientes = listOf("Vainilla", "Queso Crema", "Colorante natural")
        )
    )

    val listaPedidos = mutableListOf<Pedido>()

    fun agregarPedidoDePrueba() {
        listaPedidos.add(
            Pedido(
                id = 101,
                clienteNombre = "Usuario Prueba",
                clienteTelefono = "123456789",
                items = emptyList(),
                total = 350.0,
                fecha = Date(),
                estado = EstadoPedido.PENDIENTE,
                metodoPago = MetodoPago.TRANSFERENCIA,
                comprobanteUrl = null
            )
        )
    }
}