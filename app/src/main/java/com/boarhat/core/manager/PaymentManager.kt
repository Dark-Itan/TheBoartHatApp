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
        // Simulamos que la huella fue exitosa para que la app compile y corra
        return true
    }
}