package com.boarhat.util // Sin la 's' para que coincida con tu estructura

import android.content.Context
import androidx.biometric.BiometricManager as AndroidBiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class BiometricManager(private val context: Context) {

    fun isBiometricAvailable(): Boolean {
        val manager = AndroidBiometricManager.from(context)
        return manager.canAuthenticate(
            AndroidBiometricManager.Authenticators.BIOMETRIC_STRONG
        ) == AndroidBiometricManager.BIOMETRIC_SUCCESS
    }

    fun promptBiometricAuth(
        activity: FragmentActivity,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError(errString.toString())
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Ingreso Biométrico")
            .setSubtitle("Usa tu huella para entrar a The Boar Hat")
            .setNegativeButtonText("Usar contraseña")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}