package com.boarhat.features.admin.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.boarhat.features.admin.presentation.viewmodels.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPastelScreen(
    pastelId: Int,
    onNavigateBack: () -> Unit,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val pastelOriginal = uiState.pasteles.find { it.id == pastelId }

    // Estados de los campos de texto
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // Llenar datos automáticamente al cargar
    LaunchedEffect(pastelOriginal) {
        pastelOriginal?.let {
            nombre = it.nombre
            precio = it.precio.toString()
            stock = it.stock.toString()
            descripcion = it.descripcion
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Producto", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        if (pastelOriginal == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFAD8D6C))
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth(), minLines = 3)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val editado = pastelOriginal.copy(
                            nombre = nombre,
                            precio = precio.toDoubleOrNull() ?: 0.0,
                            stock = stock.toIntOrNull() ?: 0,
                            descripcion = descripcion
                        )
                        viewModel.actualizarPastel(editado) { success ->
                            if (success) onNavigateBack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAD8D6C))
                ) {
                    Text("GUARDAR CAMBIOS", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}