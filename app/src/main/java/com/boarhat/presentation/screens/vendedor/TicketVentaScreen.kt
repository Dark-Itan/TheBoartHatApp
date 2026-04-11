package com.boarhat.presentation.screens.vendedor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boarhat.ui.theme.*

@Composable
fun TicketVentaScreen(
    total: Float,
    pago: Float,
    cambio: Float,
    onFinalizar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Boar_FondoCrema)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.CheckCircle, null, tint = Boar_VerdeOliva, modifier = Modifier.size(80.dp))

        Text("VENTA EXITOSA", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Boar_NegroTexto)

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Total:", color = Color.Gray)
                    Text("$${total}0", fontWeight = FontWeight.Bold)
                }
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Efectivo:", color = Color.Gray)
                    Text("$${pago}0")
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Boar_FondoCrema)
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Cambio:", fontWeight = FontWeight.Bold)
                    Text("$${cambio}0", fontWeight = FontWeight.ExtraBold, color = Boar_VerdeOliva)
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onFinalizar,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Boar_VerdeOliva),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("NUEVA VENTA", fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}