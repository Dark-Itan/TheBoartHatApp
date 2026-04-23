package com.boarhat.features.admin.presentation.screens

import android.Manifest
import android.content.Context
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.boarhat.features.admin.domain.entities.Pastel
import com.boarhat.features.admin.presentation.viewmodels.AdminViewModel
import com.boarhat.ui.theme.Boar_MarronArcilla
import java.io.File

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
    var descripcion by remember { mutableStateOf("") }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    // Launcher para Galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imagenUri = uri }

    // Launcher para Cámara
    val tempUri = remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) imagenUri = tempUri.value
    }

    // --- NUEVO: Launcher para Permisos ---
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val uri = getTempUri(context)
            tempUri.value = uri
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Se requiere permiso de cámara para tomar fotos", Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Publicar Pastel", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { showDialog = true },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    if (imagenUri == null) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.AddAPhoto, null, Modifier.size(48.dp), Color.Gray)
                            Text("Añadir foto del pastel", color = Color.Gray)
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

            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre del Pastel") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio ($)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Unidades disponibles") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth(), minLines = 3)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (nombre.isNotBlank() && precio.isNotBlank()) {
                        val nuevo = Pastel(
                            id = (0..99999).random(),
                            nombre = nombre,
                            precio = precio.toDoubleOrNull() ?: 0.0,
                            stock = stock.toIntOrNull() ?: 0,
                            descripcion = descripcion,
                            imagenUrl = imagenUri.toString(),
                            categoria = "Pasteles",
                            ingredientes = emptyList()
                        )
                        viewModel.agregarPastel(nuevo) { success ->
                            if (success) {
                                Toast.makeText(context, "¡Publicado con éxito!", Toast.LENGTH_SHORT).show()
                                onPastelGuardado()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Completa los campos obligatorios", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Boar_MarronArcilla),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("PUBLICAR AHORA", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Seleccionar Imagen") },
            text = { Text("¿Desde dónde quieres subir la foto?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    // Pedimos permiso antes de intentar abrir cámara
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                }) { Text("Cámara") }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    galleryLauncher.launch("image/*")
                }) { Text("Galería") }
            }
        )
    }
}

// Función fuera del Composable para generar la URI
fun getTempUri(context: Context): Uri {
    val directory = File(context.cacheDir, "images").apply { mkdirs() }
    val file = File.createTempFile("pastel_", ".jpg", directory)
    return FileProvider.getUriForFile(context, "com.boarhat.fileprovider", file)
}