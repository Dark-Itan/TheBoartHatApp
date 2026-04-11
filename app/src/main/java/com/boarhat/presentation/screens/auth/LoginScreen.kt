package com.boarhat.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boarhat.ui.theme.* // Importamos tus nuevos colores
import com.boarhat.R // Importamos R para el logo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit
) {
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- LOGO CIRCULAR (Como en tu imagen) ---
        // Asegúrate de tener tu imagen en res/drawable con el nombre 'logo_boarhat'
        Image(
            painter = painterResource(id = R.drawable.logo_boarhat),
            contentDescription = "Logo Boar Hat",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape) // Lo hace circular
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "The Boar Hat",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Boar_NegroTexto
        )

        Text(
            text = "PUNTO DE VENTA OFICIAL",
            fontSize = 12.sp,
            color = Color.Gray,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        // --- CAMPOS DE TEXTO ---
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            isError = error,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Boar_MarronArcilla,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            isError = error,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Boar_MarronArcilla,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        if (error) {
            Text(
                text = "Credenciales incorrectas",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- BOTÓN INGRESAR (Color Marrón Arcilla de tu diseño) ---
        Button(
            onClick = {
                when (usuario.lowercase()) {
                    "cliente" -> onLoginSuccess("cliente")
                    "vendedor" -> onLoginSuccess("vendedor")
                    "admin" -> onLoginSuccess("admin")
                    else -> error = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Boar_MarronArcilla // Color de tu imagen
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("INGRESAR", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}