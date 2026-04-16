package com.boarhat.data.mapper

import com.boarhat.data.datasources.local.PedidoEntity
import com.boarhat.domain.entities.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

fun PedidoEntity.toDomain(): Pedido = Pedido(
    id = id,
    clienteNombre = clienteNombre,
    clienteTelefono = clienteTelefono,
    items = Gson().fromJson(itemsJson, object : TypeToken<List<ItemPedido>>() {}.type),
    total = total,
    anticipo50 = anticipo50, // Mapeo nuevo
    fecha = Date(fecha),
    fechaRecoleccion = fechaRecoleccion, // Mapeo nuevo
    estado = EstadoPedido.valueOf(estado),
    metodoPago = MetodoPago.valueOf(metodoPago),
    comprobanteUrl = comprobanteUrl
)

fun Pedido.toEntity(): PedidoEntity = PedidoEntity(
    id = id,
    clienteNombre = clienteNombre,
    clienteTelefono = clienteTelefono,
    itemsJson = Gson().toJson(items),
    total = total,
    anticipo50 = anticipo50, // Mapeo nuevo
    fecha = fecha.time,
    fechaRecoleccion = fechaRecoleccion, // Mapeo nuevo
    estado = estado.name,
    metodoPago = metodoPago.name,
    comprobanteUrl = comprobanteUrl
)