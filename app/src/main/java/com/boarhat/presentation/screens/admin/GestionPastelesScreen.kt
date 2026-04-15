package com.boarhat.presentation.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Versión no deprecada
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.boarhat.presentation.viewmodels.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionPastelesScreen(
    onNavigateBack: () -> Unit,
    onNavigateToEditar: (Int) -> Unit,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var pastelIdAEliminar by remember { mutableStateOf<Int?>(null) }
    var nombrePastelAEliminar by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Inventario", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        // Usando la versión AutoMirrored para evitar el warning
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.pasteles) { pastel ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        // CORRECCIÓN: Alignment.CenterVertically para Row
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = pastel.imagenUrl,
                            contentDescription = null,
                            modifier = Modifier.size(65.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                            Text(pastel.nombre, fontWeight = FontWeight.Bold)
                            Text("$${pastel.precio}", color = Color.Gray)
                        }

                        IconButton(onClick = { onNavigateToEditar(pastel.id) }) {
                            Icon(Icons.Default.Edit, "Editar", tint = Color(0xFF4CAF50))
                        }

                        IconButton(onClick = {
                            pastelIdAEliminar = pastel.id
                            nombrePastelAEliminar = pastel.nombre
                        }) {
                            Icon(Icons.Default.Delete, "Eliminar", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }

    if (pastelIdAEliminar != null) {
        AlertDialog(
            onDismissRequest = { pastelIdAEliminar = null },
            title = { Text("Eliminar Pastel") },
            text = { Text("¿Seguro que quieres borrar $nombrePastelAEliminar?") },
            confirmButton = {
                TextButton(onClick = {
                    pastelIdAEliminar?.let { id ->
                        viewModel.eliminarPastel(id) { success ->
                            if (success) { /* Opcional: Toast */ }
                        }
                    }
                    pastelIdAEliminar = null
                }) { Text("Eliminar", color = Color.Red) }
            },
            dismissButton = { TextButton(onClick = { pastelIdAEliminar = null }) { Text("Cancelar") } }
        )
    }
}