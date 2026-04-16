package com.boarhat.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Estructura para guardar los datos del usuario
data class UserData(val nombre: String, val contrasena: String)

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    // Base de datos temporal en memoria
    private val usuariosRegistrados = mutableMapOf<String, UserData>()

    fun login(correo: String, password: String): String? {
        val emailKey = correo.lowercase().trim()

        return when {
            // Credenciales de Admin
            emailKey == "admin" && password == "admin123" -> "admin"
            // Verificación en usuarios registrados
            usuariosRegistrados[emailKey]?.contrasena == password -> "cliente"
            // Usuario cliente por defecto (para pruebas)
            emailKey == "cliente" && password == "12345" -> "cliente"
            else -> null
        }
    }

    fun registrarUsuario(nombre: String, correo: String, contrasena: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val emailKey = correo.lowercase().trim()
            if (!usuariosRegistrados.containsKey(emailKey)) {
                usuariosRegistrados[emailKey] = UserData(nombre, contrasena)
                onResult(true)
            } else {
                onResult(false) // El correo ya existe
            }
        }
    }

    fun logout() {
        _isAuthenticated.value = false
    }
}