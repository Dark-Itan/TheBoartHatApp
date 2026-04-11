package com.boarhat.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boarhat.core.manager.NotificationManager
import com.boarhat.core.manager.VibrationManager
import com.boarhat.data.MockData
import com.boarhat.domain.entities.*
import com.boarhat.domain.usecases.pastel.GetPastelesUseCase
import com.boarhat.domain.usecases.pedido.CrearPedidoUseCase
import com.boarhat.domain.usecases.pedido.GetPedidosPendientesUseCase
import com.boarhat.domain.usecases.pedido.ProcesarPagoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class VendedorUIState(
    val isLoading: Boolean = false,
    val pasteles: List<Pastel> = emptyList(),
    val pedidosPendientes: List<Pedido> = emptyList(),
    val carrito: List<ItemPedido> = emptyList(),
    val totalCarrito: Double = 0.0,
    val productoSeleccionado: Pastel? = null,
    val pedidoActual: Pedido? = null,
    val cambio: Double = 0.0,
    val errorMessage: String? = null
)

@HiltViewModel
class VendedorViewModel @Inject constructor(
    private val getPastelesUseCase: GetPastelesUseCase,
    private val getPedidosPendientesUseCase: GetPedidosPendientesUseCase,
    private val crearPedidoUseCase: CrearPedidoUseCase,
    private val procesarPagoUseCase: ProcesarPagoUseCase,
    private val vibrationManager: VibrationManager,
    private val notificationManager: NotificationManager
) : ViewModel() {

    // Inicializamos con los datos de MockData que acabamos de corregir
    private val _uiState = MutableStateFlow(VendedorUIState(
        isLoading = false,
        pasteles = MockData.listaPasteles
    ))
    val uiState: StateFlow<VendedorUIState> = _uiState.asStateFlow()

    init {
        // Mantenemos cargarPasteles comentado para que no sobreescriba con una DB vacía
        // cargarPasteles()
    }

    // --- FUNCIÓN PARA SELECCIONAR Y DESCONTAR ---
    fun seleccionarProductoYVender(pastel: Pastel) {
        if (pastel.stock > 0) {
            vibrationManager.vibrarClick()

            // 1. Descontamos el stock en la lista local
            val listaActualizada = _uiState.value.pasteles.map { p ->
                if (p.id == pastel.id) p.copy(stock = p.stock - 1) else p
            }

            // 2. Actualizamos el estado
            _uiState.update { it.copy(pasteles = listaActualizada) }

            // 3. Lo agregamos al carrito (con cantidad 1)
            agregarAlCarritoConCantidad(pastel, 1)

        } else {
            vibrationManager.vibrarError()
            _uiState.update { it.copy(errorMessage = "Producto sin existencias") }
        }
    }

    fun agregarAlCarritoConCantidad(pastel: Pastel, cantidad: Int) {
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

        _uiState.update {
            it.copy(
                carrito = carritoActual,
                totalCarrito = carritoActual.sumOf { item -> item.subtotal }
            )
        }
    }

    fun limpiarCarrito() {
        _uiState.update { it.copy(carrito = emptyList(), totalCarrito = 0.0) }
    }

    // Estas funciones se quedan por si necesitas la lógica de DB después
    fun cargarPasteles() {
        viewModelScope.launch {
            getPastelesUseCase().collect { pasteles ->
                _uiState.update { it.copy(pasteles = pasteles) }
            }
        }
    }
}