package com.boarhat.data

import com.boarhat.domain.entities.Pastel

object MockData {
    val listaPasteles = listOf(
        Pastel(
            id = 1,
            nombre = "Pastel Tres Leches",
            precio = 450.0,
            stock = 15,
            descripcion = "El favorito de la casa",
            imagenUrl = "",
            categoria = "Clásicos",      // <--- Agregado para quitar el error
            ingredientes = listOf("Leche", "Harina") // <--- Agregado para quitar el error
        ),
        Pastel(
            id = 2,
            nombre = "Chocolate Real",
            precio = 380.0,
            stock = 3,
            descripcion = "Cacao 70%",
            imagenUrl = "",
            categoria = "Especiales",
            ingredientes = listOf("Cacao", "Azúcar")
        ),
        Pastel(
            id = 3,
            nombre = "Pay de Limón",
            precio = 250.0,
            stock = 0,
            descripcion = "Cremoso y ácido",
            imagenUrl = "",
            categoria = "Pays",
            ingredientes = listOf("Limón", "Galleta")
        )
    )
}