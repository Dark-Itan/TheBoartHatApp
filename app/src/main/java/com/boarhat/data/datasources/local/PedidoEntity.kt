package com.boarhat.data.datasources.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pedidos")
data class PedidoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val clienteNombre: String,
    val clienteTelefono: String,
    val itemsJson: String, // JSON string
    val total: Double,
    val fecha: Long,
    val estado: String,
    val metodoPago: String,
    val montoRecibido: Double,
    val cambio: Double
)