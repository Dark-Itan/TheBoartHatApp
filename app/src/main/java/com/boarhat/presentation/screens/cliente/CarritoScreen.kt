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
    var fechaRecoleccion by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("Mi Carrito") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f).padding(16.dp)) {
                items(uiState.carrito) { item ->
                    ListItem(
                        headlineContent = { Text(item.nombre) },
                        supportingContent = { Text("${item.cantidad} unidades - ${item.detallesAdicionales}") },
                        trailingContent = { Text("$${item.subtotal}") }
                    )
                    HorizontalDivider()
                }
            }

            // SECCIÓN DE PAGO 50%
            Card(modifier = Modifier.fillMaxWidth().padding(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Text("Total del pedido:")
                        Text("$${uiState.totalCarrito}")
                    }
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Text("Anticipo requerido (50%):", fontWeight = FontWeight.Bold)
                        Text("$${uiState.totalCarrito / 2}", fontWeight = FontWeight.Bold, color = Color.Red)
                    }
                    Text("Paga el 50% ahora para procesar tu pedido y el resto al recoger.", fontSize = 12.sp)
                }
            }

            // Datos de recolección
            Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = fechaRecoleccion, onValueChange = { fechaRecoleccion = it }, label = { Text("¿Cuándo pasas por él? (Fecha y Hora)") }, modifier = Modifier.fillMaxWidth())
            }

            Button(
                onClick = { viewModel.crearPedido(nombre, telefono, fechaRecoleccion) { success -> if(success) onNavigateToExito() } },
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAD8D6C)),
                enabled = nombre.isNotBlank() && fechaRecoleccion.isNotBlank()
            ) {
                Text("PAGAR ANTICIPO Y FINALIZAR")
            }
        }
    }
}