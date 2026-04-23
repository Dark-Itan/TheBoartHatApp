package com.boarhat.core.navigation

import androidx.navigation.NavController
import com.boarhat.features.admin.domain.entities.Pastel

class NavigationActions(
    val navController: NavController
) {
    private var productoSeleccionadoCallback: ((Pastel) -> Unit)? = null

    fun seleccionarProducto(pastel: Pastel) {
        productoSeleccionadoCallback?.invoke(pastel)
    }

    fun setProductoSeleccionadoCallback(callback: (Pastel) -> Unit) {
        productoSeleccionadoCallback = callback
    }

    fun navigateTo(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateAndClearBackStack(route: String) {
        navController.navigate(route) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
    }

    fun goBack() {
        navController.popBackStack()
    }

    // Cliente
    fun navigateToClienteMenu() = navigateTo(Screen.ClienteMenu.route)
    fun navigateToDetallePastel(pastelId: Int) = navigateTo(Screen.DetallePastel.passId(pastelId))
    fun navigateToCarrito() = navigateTo(Screen.Carrito.route)
    fun navigateToPago() = navigateTo(Screen.Pago.route)
    fun navigateToPedidoConfirmado() = navigateTo(Screen.PedidoConfirmado.route)

    // Vendedor
    fun navigateToVendedorMenu() = navigateTo(Screen.VendedorMenu.route)
    fun navigateToTomarPedido() = navigateTo(Screen.TomarPedido.route)
    fun navigateToSeleccionarProducto() = navigateTo(Screen.SeleccionarProducto.route)
    fun navigateToResumenPedido() = navigateTo(Screen.ResumenPedido.route)
    fun navigateToProcesarPagoVendedor(pedidoId: Int) = navigateTo(Screen.ProcesarPagoVendedor.passId(pedidoId))
    fun navigateToTicketPedido(pedidoId: Int) = navigateTo(Screen.TicketPedido.passId(pedidoId))
    fun navigateToListaPedidosVendedor() = navigateTo(Screen.ListaPedidosVendedor.route)
    fun navigateToDetallePedidoVendedor(pedidoId: Int) = navigateTo(Screen.DetallePedidoVendedor.passId(pedidoId))

    // Admin
    fun navigateToAdminDashboard() = navigateTo(Screen.AdminDashboard.route)
    fun navigateToGestionPasteles() = navigateTo(Screen.GestionPasteles.route)
    fun navigateToAgregarPastel() = navigateTo(Screen.AgregarPastel.route)
    fun navigateToEditarPastel(pastelId: Int) = navigateTo(Screen.EditarPastel.passId(pastelId))
    fun navigateToInventario() = navigateTo(Screen.Inventario.route)
    fun navigateToNotificaciones() = navigateTo(Screen.Notificaciones.route)
    fun navigateToReportes() = navigateTo(Screen.Reportes.route)
    fun navigateToPedidosAdmin() = navigateTo(Screen.PedidosAdmin.route)

    // Comunes
    fun navigateToPerfil() = navigateTo(Screen.Perfil.route)
    fun navigateToLogin() = navigateAndClearBackStack(Screen.Login.route)
}