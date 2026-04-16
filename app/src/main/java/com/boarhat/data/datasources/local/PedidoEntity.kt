package com.boarhat.data.datasources.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pedidos")
data class PedidoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val clienteNombre: String = "",
    val clienteTelefono: String = "",
    val itemsJson: String = "",
    val total: Double = 0.0,
    val anticipo50: Double = 0.0,
    val fecha: Long = 0L,
    val fechaRecoleccion: String = "",
    val estado: String = "PENDIENTE",
    val metodoPago: String = "TRANSFERENCIA",
    val comprobanteUrl: String? = null
)