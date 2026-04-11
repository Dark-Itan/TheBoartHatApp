package com.boarhat.presentation.screens.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CarritoScreen(
    onNavigateToPago: () -> Unit,
    onNavigateBack: () -> Unit
) {
    // Paleta de colores de tu diseño
    val colorCrema = Color(0xFFFDF8F5)
    val colorOliva = Color(0xFF8B945E)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorCrema)
            .padding(16.dp)
    ) {
        TextButton(onClick = onNavigateBack) {
            Text("< REGRESAR", color = Color.Gray)
        }

        Text(
            text = "Mi Pedido",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Tarjeta de producto (Basada en tu diseño de iPhone 17-7)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Pastel Tres Leches", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("$450.00", color = colorOliva, fontWeight = FontWeight.SemiBold)
                }

                // Cantidad
                Surface(
                    color = colorCrema,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "10", // Cantidad de tu captura
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Total
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("TOTAL", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("$4,500.00", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = colorOliva)
        }

        Button(
            onClick = onNavigateToPago,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorOliva),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("MÉTODO DE PAGO: TRANSFERENCIA", color = Color.White)
        }
    }
}