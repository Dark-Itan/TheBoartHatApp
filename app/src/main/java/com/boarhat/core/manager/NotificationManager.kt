package com.boarhat.core.manager

import android.app.NotificationChannel
import android.app.NotificationManager as AndroidNotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as AndroidNotificationManager
    private val CHANNEL_ID = "pedidos_channel"

    init {
        crearCanalNotificacion()
    }

    private fun crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notificaciones de Pedidos",
                AndroidNotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones de nuevos pedidos"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun notificarNuevoPedido(clienteNombre: String, total: Double) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("¡Nuevo Pedido!")
            .setContentText("$clienteNombre - Total: $$total")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}