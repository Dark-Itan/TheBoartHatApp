package com.boarhat.presentation.screens.vendedor

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
fun PuntoVentaScreen(
    onFinalizarVenta: (Double, Double, Double) -> Unit,
    onNavigateBack: () -> Unit
) {
    // Estado para el texto del monto recibido
    var montoRecibido by remember { mutableStateOf("") }

    // Datos de prueba basados en tu diseño
    val totalVenta = 450.0
    val colorNaranjaFondo = Color(0xFFFFE0B2) // El color naranja de tu captura
    val colorVerdeBoar = Color(0xFF8B945E)   // Tu color verde oliva

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF8F5)) // Fondo crema
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón Regresar
        TextButton(
            onClick = onNavigateBack,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("< REGRESAR", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Título del producto seleccionado
        Text(
            text = "Pastel Tres Leches",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Cuadro de "A PAGAR" (Idéntico a tu diseño iPhone 17-4)
        Surface(
            color = colorNaranjaFondo,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().height(140.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("A PAGAR", fontSize = 16.sp, color = Color.DarkGray)
                Text("$${totalVenta}0", fontSize = 42.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de entrada de dinero
        Text(
            "MONTO RECIBIDO",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelLarge
        )
        OutlinedTextField(
            value = montoRecibido,
            onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) montoRecibido = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("$ 0.00") },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorVerdeBoar,
                unfocusedBorderColor = Color.LightGray
            )
        )

        // Lógica de cálculo inmediata
        val pago = montoRecibido.toDoubleOrNull() ?: 0.0
        val cambio = if (pago >= totalVenta) pago - totalVenta else 0.0

        if (pago >= totalVenta) {
            Text(
                text = "CAMBIO: $${String.format("%.2f", cambio)}",
                modifier = Modifier.padding(top = 24.dp),
                color = colorVerdeBoar,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botón Finalizar Cobro (Blanco con borde como en tu diseño)
        Button(
            onClick = { onFinalizarVenta(totalVenta, pago, cambio) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
            shape = RoundedCornerShape(12.dp),
            enabled = pago >= totalVenta
        ) {
            Text("FINALIZAR COBRO", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}