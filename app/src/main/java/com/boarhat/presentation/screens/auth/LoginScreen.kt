package com.boarhat.presentation.screens.auth

import android.content.Context // Importante para la memoria
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.boarhat.R
import com.boarhat.ui.theme.*
import com.boarhat.util.BiometricManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current as? FragmentActivity

    // Accedemos a la memoria local para guardar qué tipo de usuario es
    val sharedPrefs = remember {
        context?.getSharedPreferences("BoarHatPrefs", Context.MODE_PRIVATE)
    }

    val authenticator = remember(context) {
        context?.let { BiometricManager(it) }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_boarhat),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp).clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("The Boar Hat", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Boar_NegroTexto)
        Text("BIENVENIDO", fontSize = 12.sp, color = Color.Gray, letterSpacing = 2.sp)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; errorMsg = null },
            label = { Text("Correo Electrónico o Usuario") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it; errorMsg = null },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        errorMsg?.let {
            Text(it, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // BOTÓN INICIAR SESIÓN (Manual)
        Button(
            onClick = {
                val user = email.lowercase().trim()

                // Definimos el rol según las credenciales
                val role = when {
                    (user == "admin@boarhat.com" || user == "admin") && password == "admin123" -> "admin"
                    (user == "cliente@correo.com" || user == "cliente") && password == "12345" -> "cliente"
                    else -> null
                }

                if (role != null) {
                    // Guardamos el rol en la memoria para que la huella lo sepa
                    sharedPrefs?.edit()?.putString("last_role", role)?.apply()
                    onLoginSuccess(role)
                } else {
                    errorMsg = "Credenciales incorrectas"
                }
            },
            modifier = Modifier.fillMaxWidth().height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Boar_MarronArcilla),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("INICIAR SESIÓN", fontWeight = FontWeight.Bold, color = Color.White)
        }

        // SECCIÓN DE HUELLA
        if (authenticator != null && authenticator.isBiometricAvailable()) {
            Spacer(modifier = Modifier.height(24.dp))

            IconButton(
                onClick = {
                    context?.let {
                        authenticator.promptBiometricAuth(
                            activity = it,
                            onSuccess = {
                                // Recuperamos el último rol guardado (por defecto cliente)
                                val savedRole = sharedPrefs?.getString("last_role", "cliente") ?: "cliente"
                                onLoginSuccess(savedRole)
                            },
                            onError = { errorMsg = "Fallo la autenticación" }
                        )
                    }
                },
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Fingerprint,
                    contentDescription = "Huella",
                    modifier = Modifier.size(50.dp),
                    tint = Boar_MarronArcilla
                )
            }

            // Texto dinámico que indica quién entrará con la huella
            val displayRole = sharedPrefs?.getString("last_role", "...") ?: "..."
            Text("Entrar como: ${displayRole.uppercase()}", fontSize = 10.sp, color = Color.Gray)
        }
    }
}