package com.boarhat.presentation.screens.vendedor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.boarhat.presentation.components.VendedorMenuItem
import com.boarhat.presentation.viewmodels.VendedorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuVendedorScreen(
    onNavigateToTomarPedido: () -> Unit,
    onNavigateToListaPedidos: () -> Unit,
    onNavigateToNotificaciones: () -> Unit,
    onNavigateToPerfil: () -> Unit,
    viewModel: VendedorViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    val menuItems = listOf(
        VendedorMenuItemData(
            icon = Icons.Default.AddShoppingCart,
            title = "Tomar Pedido",
            subtitle = "Registrar un nuevo pedido",
            color = Color(0xFF8B5CF6),
            onClick = onNavigateToTomarPedido
        ),
        VendedorMenuItemData(
            icon = Icons.Default.List,
            title = "Historial de Pedidos",
            subtitle = "Ver todos los pedidos realizados",
            color = Color(0xFF10B981),
            onClick = onNavigateToListaPedidos
        ),
        VendedorMenuItemData(
            icon = Icons.Default.Notifications,
            title = "Notificaciones",
            subtitle = "${state.pedidosPendientes.size} pedidos pendientes",
            color = Color(0xFFF59E0B),
            badge = if (state.pedidosPendientes.isNotEmpty()) state.pedidosPendientes.size.toString() else null,
            onClick = onNavigateToNotificaciones
        ),
        VendedorMenuItemData(
            icon = Icons.Default.Person,
            title = "Mi Perfil",
            subtitle = "Datos del vendedor",
            color = Color(0xFF6B7280),
            onClick = onNavigateToPerfil
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel del Vendedor") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF8B5CF6),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { /* Logout */ }) {
                        Icon(Icons.Default.Logout, contentDescription = "Salir", tint = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E8FF))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("¡Hola, Vendedor!", style = MaterialTheme.typography.titleMedium)
                            Text("Listo para atender pedidos", style = MaterialTheme.typography.bodySmall)
                        }
                        Badge(
                            containerColor = Color(0xFF8B5CF6),
                            contentColor = Color.White
                        ) {
                            Text("${state.pedidosPendientes.size} pendientes")
                        }
                    }
                }
            }

            items(menuItems) { item ->
                VendedorMenuItem(
                    icon = item.icon,
                    title = item.title,
                    subtitle = item.subtitle,
                    color = item.color,
                    badge = item.badge,
                    onClick = item.onClick
                )
            }
        }
    }
}

data class VendedorMenuItemData(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val title: String,
    val subtitle: String,
    val color: Color,
    val badge: String? = null,
    val onClick: () -> Unit
)