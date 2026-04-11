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

    private val _uiState = MutableStateFlow(AdminUIState(
        isLoading = false,
        pasteles = MockData.listaPasteles
    ))
    val uiState: StateFlow<AdminUIState> = _uiState.asStateFlow()

    init {
        cargarPasteles()
        cargarPedidos()
    }

    fun cargarPasteles() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getPastelesUseCase().collect { pasteles ->
                _uiState.update { it.copy(isLoading = false, pasteles = pasteles) }
            }
        }
    }

    fun cargarPedidos() {
        viewModelScope.launch {
            getPedidosUseCase().collect { pedidos ->
                _uiState.update { it.copy(pedidos = pedidos) }
            }
        }
    }

    fun agregarPastel(pastel: Pastel, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = addPastelUseCase(pastel)
            if (success) {
                vibrationManager.vibrarExito()
                cargarPasteles()
            } else {
                vibrationManager.vibrarError()
            }
            onResult(success)
        }
    }

    fun actualizarPastel(pastel: Pastel, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = updatePastelUseCase(pastel)
            if (success) {
                vibrationManager.vibrarExito()
                cargarPasteles()
            } else {
                vibrationManager.vibrarError()
            }
            onResult(success)
        }
    }

    fun eliminarPastel(id: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = deletePastelUseCase(id)
            if (success) {
                vibrationManager.vibrarExito()
                cargarPasteles()
            } else {
                vibrationManager.vibrarError()
            }
            onResult(success)
        }
    }
}