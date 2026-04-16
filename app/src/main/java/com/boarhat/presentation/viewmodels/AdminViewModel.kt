package com.boarhat.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boarhat.core.manager.VibrationManager
import com.boarhat.data.MockData
import com.boarhat.domain.entities.Pastel
import com.boarhat.domain.entities.Pedido
import com.boarhat.domain.usecases.pastel.*
import com.boarhat.domain.usecases.pedido.GetPedidosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AdminUIState(
    val isLoading: Boolean = false,
    val pasteles: List<Pastel> = emptyList(),
    val pedidos: List<Pedido> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val getPastelesUseCase: GetPastelesUseCase,
    private val addPastelUseCase: AddPastelUseCase,
    private val updatePastelUseCase: UpdatePastelUseCase,
    private val deletePastelUseCase: DeletePastelUseCase,
    private val getPedidosUseCase: GetPedidosUseCase,
    private val vibrationManager: VibrationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminUIState())
    val uiState: StateFlow<AdminUIState> = _uiState.asStateFlow()

    // Control de notificaciones
    private var conteoPedidosPrevio = -1

    init { refreshAll() }

    fun refreshAll() {
        cargarPasteles()
        cargarPedidos()
    }

    private fun cargarPasteles() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getPastelesUseCase().collect { lista ->
                _uiState.update { it.copy(isLoading = false, pasteles = lista) }
            }
        }
    }

    private fun cargarPedidos() {
        viewModelScope.launch {
            getPedidosUseCase().collect { lista ->
                // DETECTOR DE NUEVO PEDIDO (Flash + Vibración)
                if (conteoPedidosPrevio != -1 && lista.size > conteoPedidosPrevio) {
                    vibrationManager.dispararNotificacionPedidoFlash()
                }

                // Actualizamos el contador local
                conteoPedidosPrevio = lista.size

                _uiState.update { it.copy(pedidos = lista) }
            }
        }
    }

    fun agregarPastel(pastel: Pastel, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val success = addPastelUseCase(pastel)
                if (success) {
                    if (!MockData.listaPasteles.any { it.id == pastel.id }) {
                        MockData.listaPasteles.add(pastel)
                    }
                    vibrationManager.vibrarExito()
                    refreshAll()
                }
                onResult(success)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun actualizarPastel(pastel: Pastel, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val success = updatePastelUseCase(pastel)
                if (success) {
                    val index = MockData.listaPasteles.indexOfFirst { it.id == pastel.id }
                    if (index != -1) { MockData.listaPasteles[index] = pastel }
                    vibrationManager.vibrarExito()
                    refreshAll()
                    onResult(true)
                } else { onResult(false) }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun eliminarPastel(id: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val success = deletePastelUseCase(id)
                if (success) {
                    MockData.listaPasteles.removeAll { it.id == id }
                    vibrationManager.vibrarExito()
                    refreshAll()
                    onResult(true)
                } else { onResult(false) }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }
}