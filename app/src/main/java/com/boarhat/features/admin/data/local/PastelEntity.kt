package com.boarhat.features.admin.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pasteles")
data class PastelEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val precio: Double,
    val stock: Int,
    val imagenUrl: String,
    val categoria: String,
    val descripcion: String = "", // Agregamos esto
    val ingredientes: String = "", // Agregamos esto
    val disponible: Boolean = true // Agregamos esto
)