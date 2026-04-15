package com.boarhat.presentation.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
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
import com.boarhat.presentation.viewmodels.AdminViewModel
import com.boarhat.ui.theme.Boar_VerdeOliva

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidosAdminScreen(
    onNavigateBack: () -> Unit,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var selectedImageUri by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pedidos Recibidos", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        // Cambiado a AutoMirrored para corregir warning
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        if (state.pedidos.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No hay pedidos pendientes", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.pedidos) { pedido ->
                    PedidoCard(
                        pedido = pedido,
                        onVerComprobante = { selectedImageUri = pedido.comprobanteUrl }
                    )
                }
            }
        }

        if (selectedImageUri != null) {
            AlertDialog(
                onDismissRequest = { selectedImageUri = null },
                confirmButton = {
                    TextButton(onClick = { selectedImageUri = null }) { Text("Cerrar") }
                },
                title = { Text("Comprobante de Pago") },
                text = {
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = "Comprobante",
                        modifier = Modifier.fillMaxWidth().height(400.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            )
        }
    }
}

@Composable
fun PedidoCard(
    pedido: com.boarhat.domain.entities.Pedido,
    onVerComprobante: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Cambiado a AutoMirrored para corregir warning
                Icon(Icons.AutoMirrored.Filled.ReceiptLong, contentDescription = null, tint = Boar_VerdeOliva)
                Spacer(Modifier.width(8.dp))
                Text("Pedido #${pedido.id}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.weight(1f))
                Text(pedido.estado.name, color = Boar_VerdeOliva, fontWeight = FontWeight.Medium)
            }

            // Cambiado Divider por HorizontalDivider para corregir warning
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 1.dp, color = Color.LightGray)

            Text("Cliente: ${pedido.clienteNombre}", fontWeight = FontWeight.Medium)
            Text("Total pagado: $${pedido.total}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onVerComprobante,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("VER COMPROBANTE", color = Color.Black)
            }
        }
    }
}