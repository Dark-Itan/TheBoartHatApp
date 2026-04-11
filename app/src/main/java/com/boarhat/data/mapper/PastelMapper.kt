package com.boarhat.data.mapper

import com.boarhat.data.datasources.local.PastelEntity
import com.boarhat.domain.entities.Pastel

fun PastelEntity.toDomain(): Pastel = Pastel(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    precio = precio,
    stock = stock,
    imagenUrl = imagenUrl,
    categoria = categoria,
    ingredientes = ingredientes.split(",").filter { it.isNotBlank() },
    disponible = disponible
)

fun Pastel.toEntity(): PastelEntity = PastelEntity(
    id = id,
    nombre = nombre,
    precio = precio,
    stock = stock,
    imagenUrl = imagenUrl,
    categoria = categoria,
    descripcion = descripcion,
    ingredientes = ingredientes.joinToString(","),
    disponible = disponible
)