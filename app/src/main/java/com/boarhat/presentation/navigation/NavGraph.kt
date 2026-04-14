package com.boarhat.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.boarhat.presentation.screens.admin.*
import com.boarhat.presentation.screens.auth.LoginScreen
import com.boarhat.presentation.screens.cliente.*
import com.boarhat.presentation.screens.vendedor.*

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val currentRole = remember { mutableStateOf("") }

    NavHost(navController = navController, startDestination = "login") {

        // --- LOGIN ---
        composable("login") {
            LoginScreen { rol ->
                currentRole.value = rol
                val destino = when (rol) {
                    "admin" -> "admin_dashboard"
                    "vendedor" -> "vendedor_menu"
                    else -> "cliente_menu"
                }
                navController.navigate(destino) {
                    popUpTo("login") { inclusive = true }
                }
            }
        }

        // --- ADMINISTRADOR (role-guarded) ---
        composable("admin_dashboard") {
            if (currentRole.value != "admin") {
                navController.navigate("login") { popUpTo(0) { inclusive = true } }
                return@composable
            }
            AdminDashboardScreen(
                onNavigateToGestionPasteles = { navController.navigate("agregar_pastel") },
                onNavigateToInventario = { /* Implementar Pantalla */ },
                onNavigateToNotificaciones = { /* Implementar Pantalla */ },
                onNavigateToReportes = { /* Implementar Pantalla */ },
                onNavigateToAgregarPastel = { navController.navigate("agregar_pastel") },
                onNavigateToPedidosAdmin = { /* Implementar Pantalla */ }
            )
        }

        composable("agregar_pastel") {
            AgregarPastelScreen(
                onPastelGuardado = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // --- VENDEDOR (role-guarded) ---
        composable("vendedor_menu") {
            if (currentRole.value != "vendedor") {
                navController.navigate("login") { popUpTo(0) { inclusive = true } }
                return@composable
            }
            MenuVendedorScreen(
                onNavigateToTomarPedido = { navController.navigate("punto_venta") },
                onNavigateToListaPedidos = { /* Implementar */ },
                onNavigateToNotificaciones = { /* Implementar */ },
                onNavigateToPerfil = { navController.navigate("perfil") }
            )
        }

        composable("punto_venta") {
            PuntoVentaScreen(
                onFinalizarVenta = { total, pago, cambio ->
                    navController.navigate("ticket_venta/$total/$pago/$cambio")
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "ticket_venta/{total}/{pago}/{cambio}",
            arguments = listOf(
                navArgument("total") { type = NavType.FloatType },
                navArgument("pago") { type = NavType.FloatType },
                navArgument("cambio") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            TicketVentaScreen(
                total = backStackEntry.arguments?.getFloat("total") ?: 0f,
                pago = backStackEntry.arguments?.getFloat("pago") ?: 0f,
                cambio = backStackEntry.arguments?.getFloat("cambio") ?: 0f,
                onFinalizar = {
                    navController.navigate("vendedor_menu") {
                        popUpTo("vendedor_menu") { inclusive = true }
                    }
                }
            )
        }

        // --- CLIENTE ---
        composable("cliente_menu") {
            MenuClienteScreen(
                onNavigateToDetalle = { id -> navController.navigate("detalle_pastel/$id") },
                onNavigateToCarrito = { navController.navigate("carrito") },
                onNavigateToPerfil = { navController.navigate("perfil") }
            )
        }

        composable("carrito") {
            CarritoScreen(
                onNavigateToPago = { navController.navigate("pago_transferencia") },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("pago_transferencia") {
            PagoTransferenciaScreen(
                onConfirmarPedido = {
                    navController.navigate("cliente_menu") {
                        popUpTo("cliente_menu") { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("perfil") {
            // Aquí puedes crear una pantalla de perfil simple o reutilizar una
            Text("Pantalla de Perfil en construcción")
        }
    }
}
