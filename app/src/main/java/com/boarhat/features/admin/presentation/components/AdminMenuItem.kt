package com.boarhat.features.admin.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun AdminMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    badge: String? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = title,
                    tint = Color(0xFF8B5CF6),
                    modifier = Modifier.size(32.dp)
                )

                Column {
                    Text(title, style = MaterialTheme.typography.titleMedium)
                    Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }

            if (badge != null) {
                Badge(
                    containerColor = Color(0xFF8B5CF6),
                    contentColor = Color.White
                ) {
                    Text(badge)
                }
            } else {
                Icon(Icons.Default.ChevronRight, contentDescription = "Ir", tint = Color.Gray)
            }
        }
    }
}