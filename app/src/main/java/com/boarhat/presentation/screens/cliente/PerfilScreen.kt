package com.boarhat.presentation.screens.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
// IMPORTANTE: Estos imports quitan el error de EstadoPedido y Pedido
import com.boarhat.domain.entities.EstadoPedido
import com.boarhat.domain.entities.Pedido
import com.boarhat.presentation.viewmodels.ClienteViewModel
import com.boarhat.ui.theme.Boar_FondoCrema
import com.boarhat.ui.theme.Boar_NegroTexto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onNavigateBack: () -> Unit,
    viewModel: ClienteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = Boar_FondoCrema,
        topBar = {
            TopAppBar(
                title = { Text("Mi Historial", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Boar_FondoCrema)
            )
        }
    ) { paddingValues ->
        if (uiState.misPedidos.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.Cake, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                Spacer(Modifier.height(16.dp))
                Text("Aún no tienes pedidos", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text("Tus Próximas Recolecciones", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.height(8.dp))
                }

                items(uiState.misPedidos) { pedido ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(2.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Pedido #${pedido.id}", fontWeight = FontWeight.ExtraBold, color = Color(0xFFAD8D6C))

                                Surface(
                                    color = when(pedido.estado) {
                                        EstadoPedido.PENDIENTE -> Color(0xFFFFF3E0)
                                        EstadoPedido.PREPARANDO -> Color(0xFFE3F2FD)
                                        EstadoPedido.ENTREGADO -> Color(0xFFE8F5E9)
                                        else -> Color.LightGray
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        pedido.estado.name,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // HorizontalDivider arregla el error de 'Divider is deprecated'
                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CalendarMonth, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.Gray)
                                Spacer(Modifier.width(8.dp))
                                Text("Recoger el: ", fontSize = 14.sp)
                                Text(pedido.fechaRecoleccion, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }

                            Spacer(Modifier.height(8.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Payments, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF2E7D32))
                                Spacer(Modifier.width(8.dp))
                                Column {
                                    Text("Anticipo pagado (50%): $${pedido.anticipo50}", fontSize = 12.sp, color = Color(0xFF2E7D32))
                                    Text("Pendiente en tienda: $${pedido.total - pedido.anticipo50}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}