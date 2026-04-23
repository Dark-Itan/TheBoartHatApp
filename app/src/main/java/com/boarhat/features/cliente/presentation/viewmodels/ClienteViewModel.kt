package com.boarhat.features.cliente.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boarhat.features.cliente.domain.usecases.CrearPedidoUseCase
import com.boarhat.domain.usecases.pastel.GetPastelesUseCase
import com.boarhat.features.admin.domain.entities.EstadoPedido
import com.boarhat.features.admin.domain.entities.ItemPedido
import com.boarhat.features.admin.domain.entities.MetodoPago
import com.boarhat.features.admin.domain.entities.Pastel
import com.boarhat.features.admin.domain.entities.Pedido
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class ClienteUIState(
    val pasteles: List<Pastel> = emptyList(),
    val carrito: List<ItemPedido> = emptyList(),
    val totalCarrito: Double = 0.0,
    val misPedidos: List<Pedido> = emptyList()
)

@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val getPastelesUseCase: GetPastelesUseCase,
    private val crearPedidoUseCase: CrearPedidoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClienteUIState())
    val uiState = _uiState.asStateFlow()

    init {
        cargarPasteles()
    }

    private fun cargarPasteles() {
        viewModelScope.launch {
            getPastelesUseCase().collect { lista ->
                _uiState.update { it.copy(pasteles = lista) }
            }
        }
    }

    fun agregarAlCarrito(pastel: Pastel, cantidad: Int, notas: String) {
        val nuevoItem = ItemPedido(
            pastelId = pastel.id,
            nombre = pastel.nombre,
            cantidad = cantidad,
            precioUnitario = pastel.precio,
            subtotal = pastel.precio * cantidad,
            detallesAdicionales = notas
        )
        _uiState.update { state ->
            val nuevoCarrito = state.carrito + nuevoItem
            state.copy(
                carrito = nuevoCarrito,
                totalCarrito = nuevoCarrito.sumOf { it.subtotal }
            )
        }
    }

    fun crearPedido(nombre: String, telefono: String, fechaEntrega: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val pedido = Pedido(
                clienteNombre = nombre,
                clienteTelefono = telefono,
                items = _uiState.value.carrito,
                total = _uiState.value.totalCarrito,
                fecha = Date(),
                fechaRecoleccion = fechaEntrega,
                estado = EstadoPedido.PENDIENTE,
                metodoPago = MetodoPago.TRANSFERENCIA
            )
            val success = crearPedidoUseCase(pedido)
            if (success) {
                // Al tener éxito, limpiamos carrito
                _uiState.update { it.copy(carrito = emptyList(), totalCarrito = 0.0) }
            }
            onResult(success)
        }
    }
}