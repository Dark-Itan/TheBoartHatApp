package com.boarhat.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.boarhat.presentation.navigation.Screen

@Composable
fun ClienteBottomBar(
    currentRoute: String,
    onNavigateToMenu: () -> Unit,
    onNavigateToCarrito: () -> Unit,
    onNavigateToPerfil: () -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.RestaurantMenu, contentDescription = "Menú") },
            label = { Text("Menú") },
            selected = currentRoute == Screen.ClienteMenu.route,
            onClick = onNavigateToMenu,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF8B5CF6),
                selectedTextColor = Color(0xFF8B5CF6),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito") },
            label = { Text("Carrito") },
            selected = currentRoute == Screen.Carrito.route,
            onClick = onNavigateToCarrito,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF8B5CF6),
                selectedTextColor = Color(0xFF8B5CF6),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = currentRoute == Screen.Perfil.route,
            onClick = onNavigateToPerfil,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF8B5CF6),
                selectedTextColor = Color(0xFF8B5CF6),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
    }
}