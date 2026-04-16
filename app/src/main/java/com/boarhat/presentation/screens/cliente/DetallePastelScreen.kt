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
import androidx.compose.ui.draw.clip
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
    val pastel = uiState.pasteles.find { it.id == pastelId }

    var cantidad by remember { mutableIntStateOf(1) }
    var notas by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del Pedido") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "") }
                }
            )
        }
    ) { padding ->
        pastel?.let { p ->
            Column(modifier = Modifier.padding(padding).padding(20.dp).verticalScroll(rememberScrollState())) {
                AsyncImage(model = p.imagenUrl, contentDescription = null, modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(12.dp)), contentScale = ContentScale.Crop)

                Spacer(modifier = Modifier.height(16.dp))
                Text(p.nombre, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Stock disponible: ${p.stock}", color = if(p.stock > 3) Color.Gray else Color.Red)

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = notas,
                    onValueChange = { notas = it },
                    label = { Text("Instrucciones especiales (detalles, nombres, etc.)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (cantidad > 1) cantidad-- }) { Text("-", fontSize = 24.sp) }
                        Text("$cantidad", modifier = Modifier.padding(horizontal = 16.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { if (cantidad < p.stock) cantidad++ }) { Text("+", fontSize = 24.sp) }
                    }
                    Text("Subtotal: $${p.precio * cantidad}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFAD8D6C))
                }

                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        viewModel.agregarAlCarrito(p, cantidad, notas)
                        onNavigateToCarrito()
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAD8D6C)),
                    enabled = p.stock > 0
                ) {
                    Text("AGREGAR AL CARRITO")
                }
            }
        }
    }
}