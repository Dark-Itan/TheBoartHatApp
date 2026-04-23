package com.boarhat.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.boarhat.ui.theme.* // Importa tus colores

// Definimos un esquema oscuro básico (opcional)
private val DarkColorScheme = darkColorScheme(
    primary = Boar_VerdeOliva,
    secondary = Boar_MarronArcilla,
    background = Color.Black,
    surface = Color(0xFF121212)
)

// Esquema de luz basado exactamente en tus capturas
private val LightColorScheme = lightColorScheme(
    primary = Boar_VerdeOliva,       // Botones de pago y confirmación
    secondary = Boar_MarronArcilla,  // Botón de Ingresar
    background = Boar_FondoCrema,    // El fondo de las pantallas
    surface = Boar_BlancoCard,       // El fondo de las tarjetitas
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Boar_NegroTexto,
    onSurface = Boar_NegroTexto
)

@Composable
fun TheBoarHatTheme(
    content: @Composable () -> Unit
) {
    // Forzamos LightColorScheme para que se vea como tu diseño siempre
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}