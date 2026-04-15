package com.boarhat.presentation.screens.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.boarhat.ui.theme.Boar_FondoCrema
import com.boarhat.ui.theme.Boar_NegroTexto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen() {
    Scaffold(
        containerColor = Boar_FondoCrema,
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Boar_FondoCrema,
                    titleContentColor = Boar_NegroTexto
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("¡Bienvenido!", style = MaterialTheme.typography.headlineMedium)
            Text("Aquí verás tus pedidos próximamente.")
        }
    }
}