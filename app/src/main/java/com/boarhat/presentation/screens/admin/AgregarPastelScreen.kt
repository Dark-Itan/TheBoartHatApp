package com.boarhat.presentation.screens.admin

import android.net.Uri
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.boarhat.presentation.viewmodels.AdminViewModel
import java.io.File
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarPastelScreen(
    onPastelGuardado: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    // --- LÓGICA PARA GALERÍA ---
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> imagenUri = uri }

    // --- LÓGICA PARA CÁMARA ---
    val tempUri = remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) imagenUri = tempUri.value
    }

    // Función para crear un archivo temporal para la foto
    fun getTempUri(): Uri {
        val directory = File(context.cacheDir, "images")
        directory.mkdirs()
        val file = File.createTempFile("pastel_", ".jpg", directory)
        val authority = "${context.packageName}.fileprovider"
        return FileProvider.getUriForFile(context, authority, file)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del Producto") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card para la imagen
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { showDialog = true }, // Abrir opciones
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    if (imagenUri == null) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.AddAPhoto, null, Modifier.size(48.dp), Color.Gray)
                            Text("Toca para añadir foto", color = Color.Gray)
                        }
                    } else {
                        AsyncImage(
                            model = imagenUri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            // Campos de texto (puedes reutilizar tus OutlinedTextFields actuales)
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del Pastel") },
                modifier = Modifier.fillMaxWidth()
            )

            // ... (resto de tus campos de Precio y Stock)

            Button(
                onClick = { /* Lógica de guardado en el viewModel usando imagenUri */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("ACTUALIZAR PRODUCTO")
            }
        }
    }

    // --- DIÁLOGO DE SELECCIÓN ---
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Seleccionar imagen") },
            text = { Text("¿Desde dónde quieres subir la foto del pastel?") },
            confirmButton = {
                TextButton(onClick = {
                    val uri = getTempUri()
                    tempUri.value = uri
                    cameraLauncher.launch(uri)
                    showDialog = false
                }) {
                    Text("Cámara")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    galleryLauncher.launch("image/*")
                    showDialog = false
                }) {
                    Text("Galería")
                }
            }
        )
    }
}