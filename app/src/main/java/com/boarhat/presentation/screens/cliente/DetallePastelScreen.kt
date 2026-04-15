package com.boarhat.presentation.screens.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.boarhat.presentation.viewmodels.ClienteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallePastelScreen(
    pastelId: Int,
    onNavigateBack: () -> Unit,
    onNavigateToCarrito: () -> Unit,
    viewModel: ClienteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    // Buscamos el pastel en la lista del estado
    val pastel = uiState.pasteles.find { it.id == pastelId }

    // Estado local para la cantidad que el cliente quiere pedir
    var cantidad by remember { mutableIntStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Pastel") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToCarrito) {
                        BadgedBox(badge = {
                            if (uiState.carrito.isNotEmpty()) {
                                Badge { Text(uiState.carrito.size.toString()) }
                            }
                        }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (pastel == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFAD8D6C))
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Imagen del Pastel
                AsyncImage(
                    model = pastel.imagenUrl,
                    contentDescription = pastel.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = pastel.nombre,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "$${pastel.precio}",
                            fontSize = 22.sp,
                            color = Color(0xFFAD8D6C),
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // --- INDICADOR DE STOCK (Lo que el admin controla) ---
                    val hayStock = pastel.stock > 0
                    Surface(
                        color = if (hayStock) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (hayStock) "Disponible: ${pastel.stock} unidades" else "AGOTADO",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            color = if (hayStock) Color(0xFF2E7D32) else Color.Red,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Descripción", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = pastel.descripcion, color = Color.Gray, fontSize = 16.sp)

                    Spacer(modifier = Modifier.height(24.dp))

                    if (hayStock) {
                        // Selector de cantidad
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedButton(
                                onClick = { if (cantidad > 1) cantidad-- },
                                shape = RoundedCornerShape(8.dp)
                            ) { Text("-") }

                            Text(
                                text = cantidad.toString(),
                                modifier = Modifier.padding(horizontal = 24.dp),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            OutlinedButton(
                                onClick = { if (cantidad < pastel.stock) cantidad++ },
                                shape = RoundedCornerShape(8.dp)
                            ) { Text("+") }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botón Agregar
                        Button(
                            onClick = {
                                viewModel.agregarAlCarrito(pastel, cantidad)
                                onNavigateToCarrito()
                            },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAD8D6C))
                        ) {
                            Text("AGREGAR AL CARRITO", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    } else {
                        // Botón deshabilitado si no hay stock
                        Button(
                            onClick = { },
                            enabled = false,
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("SIN EXISTENCIAS", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}