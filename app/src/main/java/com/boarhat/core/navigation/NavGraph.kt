package com.boarhat.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.boarhat.features.admin.presentation.screens.AdminDashboardScreen
import com.boarhat.features.admin.presentation.screens.AgregarPastelScreen
import com.boarhat.features.admin.presentation.screens.EditarPastelScreen
import com.boarhat.features.admin.presentation.screens.GestionPastelesScreen
import com.boarhat.features.admin.presentation.screens.PedidosAdminScreen
import com.boarhat.features.admin.presentation.screens.ReportesScreen
import com.boarhat.features.auth.presentation.screens.LoginScreen
import com.boarhat.features.auth.presentation.screens.RegistroScreen
import com.boarhat.features.cliente.presentation.screens.CarritoScreen
import com.boarhat.features.cliente.presentation.screens.DetallePastelScreen
import com.boarhat.features.cliente.presentation.screens.MenuClienteScreen
import com.boarhat.features.cliente.presentation.screens.PerfilScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        // --- AUTENTICACIÓN ---
        composable("login") {
            LoginScreen(
                onLoginSuccess = { rol: String -> // Se añade :String para evitar el error de inferencia
                    val destino = if (rol == "admin") "admin_dashboard" else "cliente_menu"
                    navController.navigate(destino)
                },
                onNavigateToRegistro = {
                    navController.navigate("registro")
                }
            )
        }

        composable("registro") {
            RegistroScreen(
                onRegistroExitoso = {
                    navController.navigate("login") {
                        popUpTo("registro") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
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
                onPastelGuardado = { navController.popBackStack() },
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

        composable("carrito") {
            CarritoScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToExito = {
                    navController.navigate("cliente_menu") {
                        popUpTo("cliente_menu") { inclusive = true }
                    }
                }
            )
        }

        composable("perfil") {
            PerfilScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}