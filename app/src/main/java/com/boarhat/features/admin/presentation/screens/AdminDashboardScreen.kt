package com.boarhat.features.admin.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.boarhat.features.admin.presentation.components.AdminMenuItem
import com.boarhat.features.admin.presentation.viewmodels.AdminViewModel
import com.boarhat.ui.theme.* // Importante para los colores

// 1. Estructura de datos para evitar el error en la lista
data class AdminMenuItemData(
    val icon: ImageVector,
    val title: String,
    val subtitle: String,
    val badge: String?,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onNavigateToGestionPasteles: () -> Unit,
    onNavigateToInventario: () -> Unit,
    onNavigateToNotificaciones: () -> Unit,
    onNavigateToReportes: () -> Unit,
    onNavigateToAgregarPastel: () -> Unit,
    onNavigateToPedidosAdmin: () -> Unit,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val pedidosPendientes = state.pedidos.count { it.estado.name == "PENDIENTE" }

    // 2. Definición de los items usando la data class de arriba
    val menuItems = listOf(
        AdminMenuItemData(
            icon = Icons.AutoMirrored.Filled.List,
            title = "Gestionar Pasteles",
            subtitle = "CRUD completo de productos",
            badge = null,
            onClick = onNavigateToGestionPasteles
        ),
        AdminMenuItemData(
            icon = Icons.Default.Inventory,
            title = "Inventario",
            subtitle = "Control de stock y existencias",
            badge = null,
            onClick = onNavigateToInventario
        ),
        AdminMenuItemData(
            icon = Icons.Default.Add,
            title = "Agregar Producto",
            subtitle = "Añadir nuevo pastel al catálogo",
            badge = null,
            onClick = onNavigateToAgregarPastel
        ),
        AdminMenuItemData(
            icon = Icons.Default.Notifications,
            title = "Pedidos",
            subtitle = "Gestión de órdenes",
            badge = pedidosPendientes.takeIf { it > 0 }?.toString(),
            onClick = onNavigateToPedidosAdmin
        ),
        AdminMenuItemData(
            icon = Icons.Default.BarChart,
            title = "Reportes",
            subtitle = "Estadísticas y ventas",
            badge = null,
            onClick = onNavigateToReportes
        )
    )

    Scaffold(
        containerColor = Boar_FondoCrema, // Color de fondo del diseño
        topBar = {
            TopAppBar(
                title = { Text("Panel Admin", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Boar_FondoCrema,
                    titleContentColor = Boar_NegroTexto
                ),
                actions = {
                    IconButton(onClick = { /* Logout */ }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, tint = Boar_MarronArcilla)
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                // Tarjeta de resumen estilo iPhone
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Boar_NaranjaSuave),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Bienvenido", fontWeight = FontWeight.Bold, color = Boar_NegroTexto)
                        Text("Productos en catálogo: ${state.pasteles.size}", fontSize = 14.sp)
                    }
                }
            }

            items(menuItems) { item ->
                AdminMenuItem(
                    icon = item.icon,
                    title = item.title,
                    subtitle = item.subtitle,
                    badge = item.badge,
                    onClick = item.onClick
                )
            }
        }
    }
}