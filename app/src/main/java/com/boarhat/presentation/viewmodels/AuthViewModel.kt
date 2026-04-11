package com.boarhat.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _userRole = MutableStateFlow("")
    val userRole: StateFlow<String> = _userRole.asStateFlow()

    fun login(usuario: String, password: String): String? {
        return when (usuario.lowercase()) {
            "cliente" -> {
                _isAuthenticated.value = true
                _userRole.value = "cliente"
                "cliente"
            }
            "vendedor" -> {
                _isAuthenticated.value = true
                _userRole.value = "vendedor"
                "vendedor"
            }
            "admin" -> {
                _isAuthenticated.value = true
                _userRole.value = "admin"
                "admin"
            }
            else -> null
        }
    }

    fun logout() {
        _isAuthenticated.value = false
        _userRole.value = ""
    }
}