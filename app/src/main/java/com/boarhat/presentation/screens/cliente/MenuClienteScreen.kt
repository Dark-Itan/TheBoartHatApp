package com.boarhat.presentation.screens.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.boarhat.presentation.components.PastelCard
import com.boarhat.presentation.viewmodels.ClienteViewModel
import com.boarhat.ui.theme.* @OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuClienteScreen(
    onNavigateToDetalle: (Int) -> Unit,
    onNavigateToCarrito: () -> Unit,
    onNavigateToPerfil: () -> Unit,
    viewModel: ClienteViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = Boar_FondoCrema,
        topBar = {
            TopAppBar(
                title = { Text("The Boar Hat", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Boar_FondoCrema,
                    titleContentColor = Boar_NegroTexto
                ),
                actions = {
                    BadgedBox(
                        badge = {
                            if (state.carrito.isNotEmpty()) {
                                Badge(containerColor = Color.Red, contentColor = Color.White) {
                                    Text("${state.carrito.size}")
                                }
                            }
                        }
                    ) {
                        IconButton(onClick = onNavigateToCarrito) {
                            Icon(Icons.Default.ShoppingCart, "Carrito", tint = Boar_MarronArcilla)
                        }
                    }

                    IconButton(onClick = onNavigateToPerfil) {
                        Icon(Icons.Default.Person, "Perfil", tint = Boar_MarronArcilla)
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                Box(Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Boar_VerdeOliva)
                }
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.pasteles) { pastel ->
                        PastelCard(
                            pastel = pastel,
                            onClick = { onNavigateToDetalle(pastel.id) },
                            onAgregarAlCarrito = { viewModel.agregarAlCarrito(pastel) }
                        )
                    }
                }
            }
        }
    }
}