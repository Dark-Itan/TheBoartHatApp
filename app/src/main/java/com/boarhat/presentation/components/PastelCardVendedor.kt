package com.boarhat.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.boarhat.domain.entities.Pastel

@Composable
fun PastelCardVendedor(
    pastel: Pastel,
    onClick: () -> Unit
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
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = pastel.nombre,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1
                )
                Text(
                    text = "$${pastel.precio}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF8B5CF6)
                )
                Text(
                    text = "Stock: ${pastel.stock}",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (pastel.stock <= 0) Color.Red else Color.Gray
                )
            }
        }
    }
}