package com.boarhat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.boarhat.presentation.screens.admin.*
import com.boarhat.presentation.screens.auth.LoginScreen
import com.boarhat.presentation.screens.cliente.*

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        // --- LOGIN ---
        composable("login") {
            LoginScreen { rol ->
                val destino = if (rol == "admin") "admin_dashboard" else "cliente_menu"
                navController.navigate(destino)
            }
        }

        // --- ADMINISTRADOR ---
        composable("admin_dashboard") {
            AdminDashboardScreen(
                onNavigateToGestionPasteles = { navController.navigate("gestion_pasteles") },
                onNavigateToInventario = { navController.navigate("gestion_pasteles") },
                onNavigateToAgregarPastel = { navController.navigate("agregar_pastel") },
                onNavigateToNotificaciones = { /* Implementar luego */ },
                onNavigateToReportes = { navController.navigate("reportes_admin") },
                onNavigateToPedidosAdmin = { navController.navigate("pedidos_admin") }
            )
        }

        composable("gestion_pasteles") {
            GestionPastelesScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEditar = { id ->
                    navController.navigate("editar_pastel/$id")
                }
            )
        }

        composable(
            route = "editar_pastel/{pastelId}",
            arguments = listOf(navArgument("pastelId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("pastelId") ?: 0
            EditarPastelScreen(
                pastelId = id,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("agregar_pastel") {
            AgregarPastelScreen(
                onPastelGuardado = {
                    navController.popBackStack()
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("pedidos_admin") {
            PedidosAdminScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable("reportes_admin") {
            ReportesScreen(onNavigateBack = { navController.popBackStack() })
        }

        // --- CLIENTE ---
        composable("cliente_menu") {
            MenuClienteScreen(
                onNavigateToDetalle = { id -> navController.navigate("detalle_pastel/$id") },
                onNavigateToCarrito = { navController.navigate("carrito") },
                onNavigateToPerfil = { navController.navigate("perfil") }
            )
        }

        // RUTA DETALLE (Esta es nueva y necesaria para el Cliente)
        composable(
            route = "detalle_pastel/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DetallePastelScreen(
                pastelId = id,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCarrito = { navController.navigate("carrito") }
            )
        }

        // CARRITO (Aquí corregimos el error de la imagen)
        composable("carrito") {
            CarritoScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToExito = {
                    // Al confirmar, vamos a una pantalla de éxito o volvemos al menú
                    navController.navigate("cliente_menu") {
                        popUpTo("cliente_menu") { inclusive = true }
                    }
                }
            )
        }

        composable("perfil") { PerfilScreen() }
    }
}