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
    fecha = Date(fecha),
    estado = EstadoPedido.valueOf(estado),
    metodoPago = MetodoPago.valueOf(metodoPago),
    montoRecibido = montoRecibido,
    cambio = cambio
)

fun Pedido.toEntity(): PedidoEntity = PedidoEntity(
    id = id,
    clienteNombre = clienteNombre,
    clienteTelefono = clienteTelefono,
    itemsJson = Gson().toJson(items),
    total = total,
    fecha = fecha.time,
    estado = estado.name,
    metodoPago = metodoPago.name,
    montoRecibido = montoRecibido,
    cambio = cambio
)