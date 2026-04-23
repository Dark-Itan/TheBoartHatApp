package com.boarhat.features.auth.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.boarhat.R // Asegúrate de que este import esté presente
import com.boarhat.features.auth.presentation.viewmodels.AuthViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    onNavigateToRegistro: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- AQUÍ ESTÁ TU LOGO ---
        Image(
            painter = painterResource(id = R.drawable.logo_boarhat),
            contentDescription = "Logo de The Boar Hat",
            modifier = Modifier
                .size(150.dp) // Tamaño del logo
                .padding(bottom = 24.dp)
        )

        Text("Bienvenido a The Boar Hat", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        errorMsg?.let {
            Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }

        Button(
            onClick = {
                val rol = viewModel.login(correo, password)
                if (rol != null) onLoginSuccess(rol) else errorMsg = "Credenciales incorrectas"
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Entrar")
        }

        TextButton(onClick = onNavigateToRegistro) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}