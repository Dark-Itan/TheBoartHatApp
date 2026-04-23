package com.boarhat.features.admin.domain.entities

data class Pastel(
    val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val imagenUrl: String,
    val categoria: String,  // "Tres Leches", "Chocolate", "Frutas", etc.
    val ingredientes: List<String>,
    val disponible: Boolean = true
)