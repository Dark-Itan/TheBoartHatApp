package com.boarhat.core.manager

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VibrationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    fun vibrarClick() {
        vibrator?.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    fun vibrarExito() {
        vibrator?.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    fun vibrarError() {
        vibrator?.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    // --- ALERTA TIPO NOTIFICACIÓN: FLASH + VIBRACIÓN SIMULTÁNEA ---
    fun dispararNotificacionPedidoFlash() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val cameraId = cameraManager.cameraIdList[0]

                // Parpadeo rítmico (3 veces)
                repeat(3) {
                    // Enciende Flash y Vibra al mismo tiempo
                    cameraManager.setTorchMode(cameraId, true)
                    vibrator?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))

                    delay(250) // Tiempo encendido

                    // Apaga Flash
                    cameraManager.setTorchMode(cameraId, false)

                    delay(200) // Tiempo de espera para el siguiente pulso
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}