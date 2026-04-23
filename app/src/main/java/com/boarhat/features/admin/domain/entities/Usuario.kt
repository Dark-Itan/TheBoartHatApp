package com.boarhat.features.admin.domain.entities

data class Usuario(
    val id: Int = 0,
    val nombre: String,
    val email: String,
    val rol: String, // "cliente", "vendedor", "admin"
    val telefono: String
)