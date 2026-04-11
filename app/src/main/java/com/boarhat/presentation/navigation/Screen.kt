package com.boarhat.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    // Auth
    object Login : Screen("login", "Iniciar Sesión", Icons.Default.Lock)

    // Cliente
    object ClienteMenu : Screen("cliente_menu", "Menú", Icons.Default.RestaurantMenu)
    object DetallePastel : Screen("detalle_pastel/{pastelId}", "Detalle", Icons.Default.Info) {
        fun passId(id: Int) = "detalle_pastel/$id"
    }
    object Carrito : Screen("carrito", "Carrito", Icons.Default.ShoppingCart)
    object Pago : Screen("pago", "Pago", Icons.Default.Payment)
    object PedidoConfirmado : Screen("pedido_confirmado", "Confirmado", Icons.Default.CheckCircle)

    // Vendedor
    object VendedorMenu : Screen("vendedor_menu", "Panel Vendedor", Icons.Default.Store)
    object TomarPedido : Screen("tomar_pedido", "Tomar Pedido", Icons.Default.AddShoppingCart)
    object SeleccionarProducto : Screen("seleccionar_producto", "Seleccionar Producto", Icons.Default.Fastfood)
    object ResumenPedido : Screen("resumen_pedido", "Resumen", Icons.Default.Receipt)
    object ProcesarPagoVendedor : Screen("procesar_pago_vendedor/{pedidoId}", "Procesar Pago", Icons.Default.Payment) {
        fun passId(id: Int) = "procesar_pago_vendedor/$id"
    }
    object TicketPedido : Screen("ticket_pedido/{pedidoId}", "Ticket", Icons.Default.Print) {
        fun passId(id: Int) = "ticket_pedido/$id"
    }
    object ListaPedidosVendedor : Screen("lista_pedidos_vendedor", "Pedidos", Icons.Default.List)
    object DetallePedidoVendedor : Screen("detalle_pedido_vendedor/{pedidoId}", "Detalle Pedido", Icons.Default.ReceiptLong) {
        fun passId(id: Int) = "detalle_pedido_vendedor/$id"
    }

    // Admin
    object AdminDashboard : Screen("admin_dashboard", "Dashboard", Icons.Default.Dashboard)
    object GestionPasteles : Screen("gestion_pasteles", "Gestionar Pasteles", Icons.Default.Edit)
    object AgregarPastel : Screen("agregar_pastel", "Agregar Pastel", Icons.Default.Add)
    object EditarPastel : Screen("editar_pastel/{pastelId}", "Editar Pastel", Icons.Default.Edit) {
        fun passId(id: Int) = "editar_pastel/$id"
    }
    object Inventario : Screen("inventario", "Inventario", Icons.Default.Inventory)
    object Notificaciones : Screen("notificaciones", "Notificaciones", Icons.Default.Notifications)
    object Reportes : Screen("reportes", "Reportes", Icons.Default.BarChart)
    object PedidosAdmin : Screen("pedidos_admin", "Pedidos", Icons.Default.ListAlt)

    // Comunes
    object Perfil : Screen("perfil", "Perfil", Icons.Default.Person)
    object Configuracion : Screen("configuracion", "Configuración", Icons.Default.Settings)
}