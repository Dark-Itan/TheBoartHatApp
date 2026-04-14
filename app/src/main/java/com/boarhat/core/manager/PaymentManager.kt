package com.boarhat.core.manager

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun autenticarBiometrico(): Boolean {
        // SECURITY WARNING: This is a stub that always returns true.
        // In production, replace with a real BiometricPrompt implementation
        // to enforce biometric authentication before processing payments.
        // See: https://developer.android.com/training/sign-in/biometric-auth
        return true
    }
}
