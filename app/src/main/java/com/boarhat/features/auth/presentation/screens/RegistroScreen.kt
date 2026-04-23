package com.boarhat.features.auth.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.boarhat.R
import com.boarhat.features.auth.presentation.viewmodels.AuthViewModel
import com.boarhat.ui.theme.Boar_MarronArcilla
import com.boarhat.ui.theme.Boar_NegroTexto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onRegistroExitoso: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    // Variables de estado para los campos
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // LOGO DE LA APLICACIÓN
        Image(
            painter = painterResource(id = R.drawable.logo_boarhat),
            contentDescription = "Logo de The Boar Hat",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Crear Cuenta",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Boar_NegroTexto
        )

        Text(
            text = "Completa tus datos para empezar",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // CAMPO NOMBRE
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre Completo") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // CAMPO CORREO
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo Electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // CAMPO CONTRASEÑA
        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        // BOTÓN DE REGISTRO
        Button(
            onClick = {
                if (nombre.isNotBlank() && correo.contains("@") && contrasena.length >= 6) {
                    viewModel.registrarUsuario(nombre, correo, contrasena) { success ->
                        if (success) {
                            Toast.makeText(context, "¡Bienvenido, $nombre!", Toast.LENGTH_SHORT).show()
                            onRegistroExitoso()
                        } else {
                            Toast.makeText(context, "Este correo ya está registrado", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Revisa los datos (Contraseña mín. 6 caracteres)", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Boar_MarronArcilla),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "REGISTRARSE",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // BOTÓN PARA VOLVER AL LOGIN
        TextButton(
            onClick = onNavigateToLogin,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "¿Ya tienes cuenta? Inicia Sesión",
                color = Color.Gray
            )
        }
    }
}
