package com.boarhat.presentation.screens.cliente

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PagoTransferenciaScreen(
    onConfirmarPedido: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Column (modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Pago por Transferencia", style = MaterialTheme.typography.titleLarge)
        Text("CLABE: 0123 4567 8901 2345 67")
        Text("Banco: BoarBank")

        Button (onClick = onConfirmarPedido) {
            Text("SUBIR COMPROBANTE Y CONFIRMAR")
        }
    }
}