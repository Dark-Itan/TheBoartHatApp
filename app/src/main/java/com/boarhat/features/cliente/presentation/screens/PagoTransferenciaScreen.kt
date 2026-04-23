package com.boarhat.features.cliente.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boarhat.ui.theme.Boar_VerdeOliva

@Composable
fun PagoTransferenciaScreen(
    onBack: () -> Unit,
    onConfirmarPago: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Pago por Transferencia", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("CLABE: 0123 4567 8901 2345 67", fontWeight = FontWeight.Medium)
                Text("Banco: BoarBank")
                Text("Titular: The Boar Hat S.A.")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onConfirmarPago,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Boar_VerdeOliva)
        ) {
            Text("CONFIRMAR PAGO", color = Color.White)
        }

        TextButton(onClick = onBack) {
            Text("Cancelar")
        }
    }
}