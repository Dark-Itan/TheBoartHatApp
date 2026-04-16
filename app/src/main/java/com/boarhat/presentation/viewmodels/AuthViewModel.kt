package com.boarhat.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// 1. Definimos la estructura de datos fuera de la clase para orden
data class UserData(
    val nombre: String,
    val contrasena: String
)

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _userRole = MutableStateFlow("")
    val userRole: StateFlow<String> = _userRole.asStateFlow()

    // 2. Mapa que usa el CORREO como clave y guarda el NOMBRE y CONTRASEÑA
    private val usuariosRegistrados = mutableMapOf<String, UserData>()

    fun login(correo: String, password: String): String? {
        val emailKey = correo.lowercase().trim()

        return when {
            // Admin por defecto
            emailKey == "admin" && password == "admin123" -> {
                actualizarEstadoAutenticado("admin")
                "admin"
            }
            // Cliente por defecto (para que no pierdas tus pruebas)
            emailKey == "cliente" && password == "12345" -> {
                actualizarEstadoAutenticado("cliente")
                "cliente"
            }
            // Verificación en usuarios que se acaban de registrar
            usuariosRegistrados.containsKey(emailKey) && usuariosRegistrados[emailKey]?.contrasena == password -> {
                actualizarEstadoAutenticado("cliente")
                "cliente"
            }
            else -> null
        }
    }

    private fun actualizarEstadoAutenticado(role: String) {
        _isAuthenticated.value = true
        _userRole.value = role
    }

    fun registrarUsuario(nombre: String, correo: String, contrasena: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val emailKey = correo.lowercase().trim()
            if (!usuariosRegistrados.containsKey(emailKey)) {
                // Guardamos el objeto UserData completo
                usuariosRegistrados[emailKey] = UserData(nombre, contrasena)
                onResult(true)
            } else {
                onResult(false) // El correo ya existe
            }
        }
    }

    fun logout() {
        _isAuthenticated.value = false
        _userRole.value = ""
    }
}