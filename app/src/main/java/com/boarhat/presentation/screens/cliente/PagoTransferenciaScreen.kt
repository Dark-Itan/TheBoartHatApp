package com.boarhat.presentation.screens.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boarhat.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagoTransferenciaScreen(
    onBack: () -> Unit,
    onConfirmarPago: () -> Unit
) {
    Scaffold(
        containerColor = Boar_FondoCrema, // Fondo de la marca
        topBar = {
            TopAppBar(
                title = { Text("Finalizar Pago", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Boar_NegroTexto)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Boar_FondoCrema,
                    titleContentColor = Boar_NegroTexto
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Realiza tu transferencia",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Boar_NegroTexto,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Tarjeta con los datos bancarios estilizada
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    DatoBancario(titulo = "Banco", valor = "BoarBank")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Boar_FondoCrema)
                    DatoBancario(titulo = "CLABE", valor = "0123 4567 8901 2345 67", tieneCopy = true)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Boar_FondoCrema)
                    DatoBancario(titulo = "Beneficiario", valor = "The Boar Hat S.A.")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Una vez realizada la transferencia, sube tu comprobante y confirma para procesar tu pedido.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f)) // Empuja el botón hacia abajo

            // Botón estilizado de la marca
            Button(
                onClick = onConfirmarPago,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Boar_VerdeOliva),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "SUBIR COMPROBANTE Y CONFIRMAR",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// Componente auxiliar para los datos
@Composable
fun DatoBancario(titulo: String, valor: String, tieneCopy: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = titulo, fontSize = 12.sp, color = Color.Gray)
            Text(text = valor, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Boar_NegroTexto)
        }
        if (tieneCopy) {
            IconButton(onClick = { /* Lógica de copiar */ }) {
                Icon(Icons.Default.ContentCopy, contentDescription = "Copiar", tint = Boar_VerdeOliva, modifier = Modifier.size(20.dp))
            }
        }
    }
}