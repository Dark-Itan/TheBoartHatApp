package com.boarhat.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.boarhat.domain.entities.Pastel

@Composable
fun PastelCard(
    pastel: Pastel,
    onClick: () -> Unit,
    onAgregarAlCarrito: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            AsyncImage(
                model = pastel.imagenUrl.ifEmpty { "https://picsum.photos/200/200" },
                contentDescription = pastel.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = pastel.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )

                Text(
                    text = "$${pastel.precio}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF8B5CF6)
                )

                if (pastel.stock <= 0) {
                    Text(
                        text = "Agotado",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Red
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onAgregarAlCarrito,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = pastel.stock > 0,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B5CF6)
                    )
                ) {
                    Text("Agregar al Carrito")
                }
            }
        }
    }
}