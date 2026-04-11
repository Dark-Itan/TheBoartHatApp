package com.boarhat.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boarhat.core.manager.VibrationManager
import com.boarhat.domain.entities.ItemPedido
import com.boarhat.domain.entities.Pastel
import com.boarhat.domain.entities.Pedido
import com.boarhat.domain.usecases.pastel.GetPastelesUseCase
import com.boarhat.domain.usecases.pedido.CrearPedidoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class ClienteUIState(
    val isLoading: Boolean = false,
    val pasteles: List<Pastel> = emptyList(),
    val carrito: List<ItemPedido> = emptyList(),
    val totalCarrito: Double = 0.0,
    val pedidoActual: Pedido? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val getPastelesUseCase: GetPastelesUseCase,
    private val crearPedidoUseCase: CrearPedidoUseCase,
    private val vibrationManager: VibrationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClienteUIState(isLoading = true))
    val uiState: StateFlow<ClienteUIState> = _uiState.asStateFlow()

    init {
        cargarPasteles()
    }

    fun cargarPasteles() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getPastelesUseCase().collect { pasteles ->
                _uiState.update { it.copy(isLoading = false, pasteles = pasteles) }
            }
        }
    }

    fun agregarAlCarrito(pastel: Pastel, cantidad: Int = 1) {
        vibrationManager.vibrarClick()

        val nuevoItem = ItemPedido(
            pastelId = pastel.id,
            nombre = pastel.nombre,
            cantidad = cantidad,
            precioUnitario = pastel.precio,
            subtotal = pastel.precio * cantidad
        )

        val carritoActual = _uiState.value.carrito.toMutableList()
        val indexExistente = carritoActual.indexOfFirst { it.pastelId == pastel.id }

        if (indexExistente != -1) {
            val existente = carritoActual[indexExistente]
            carritoActual[indexExistente] = existente.copy(
                cantidad = existente.cantidad + cantidad,
                subtotal = existente.subtotal + (pastel.precio * cantidad)
            )
        } else {
            carritoActual.add(nuevoItem)
        }

        val nuevoTotal = carritoActual.sumOf { it.subtotal }

        _uiState.update {
            it.copy(
                carrito = carritoActual,
                totalCarrito = nuevoTotal
            )
        }
    }

    fun limpiarCarrito() {
        _uiState.update {
            it.copy(
                carrito = emptyList(),
                totalCarrito = 0.0
            )
        }
    }

    fun crearPedido(clienteNombre: String, clienteTelefono: String, onResult: (Boolean, Int?) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val pedido = Pedido(
                clienteNombre = clienteNombre,
                clienteTelefono = clienteTelefono,
                items = _uiState.value.carrito,
                total = _uiState.value.totalCarrito,
                fecha = Date(),
                estado = com.boarhat.domain.entities.EstadoPedido.PENDIENTE,
                metodoPago = com.boarhat.domain.entities.MetodoPago.EFECTIVO
            )

            val success = crearPedidoUseCase(pedido)

            if (success) {
                vibrationManager.vibrarExito()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        pedidoActual = pedido,
                        carrito = emptyList(),
                        totalCarrito = 0.0
                    )
                }
                onResult(true, pedido.id)
            } else {
                vibrationManager.vibrarError()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error al crear el pedido"
                    )
                }
                onResult(false, null)
            }
        }
    }
}