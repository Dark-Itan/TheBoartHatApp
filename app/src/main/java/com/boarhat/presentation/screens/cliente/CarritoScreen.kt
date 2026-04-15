package com.boarhat.presentation.screens.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.boarhat.presentation.viewmodels.ClienteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    onNavigateBack: () -> Unit,
    onNavigateToExito: () -> Unit,
    viewModel: ClienteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (uiState.carrito.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Tu carrito está vacío", color = Color.Gray)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f).padding(16.dp)) {
                    items(uiState.carrito) { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(item.nombre, fontWeight = FontWeight.Bold)
                                Text("${item.cantidad} x $${item.precioUnitario}", fontSize = 14.sp)
                            }
                            Text("$${item.subtotal}", fontWeight = FontWeight.Bold)
                        }
                        HorizontalDivider()
                    }
                }

                // Datos del Cliente
                Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Datos de Entrega", fontWeight = FontWeight.Bold)
                        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Tu Nombre") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth())
                    }
                }

                // Total y Confirmar
                Surface(tonalElevation = 8.dp) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text("$${uiState.totalCarrito}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFAD8D6C))
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                if (nombre.isNotBlank() && telefono.isNotBlank()) {
                                    viewModel.crearPedido(nombre, telefono) { success, id ->
                                        if (success) onNavigateToExito()
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAD8D6C)),
                            enabled = nombre.isNotBlank() && telefono.isNotBlank()
                        ) {
                            Text("CONFIRMAR PEDIDO")
                        }
                    }
                }
            }
        }
    }
}