package com.boarhat.features.auth.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "boarhat_prefs")

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object Keys {
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_PASSWORD = stringPreferencesKey("user_password")
        val USER_TOKEN = stringPreferencesKey("user_token")
    }

    suspend fun saveCredentials(email: String, password: String, token: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.USER_EMAIL] = email
            prefs[Keys.USER_PASSWORD] = password
            prefs[Keys.USER_TOKEN] = token
        }
    }

    fun getCredentials(): Flow<Triple<String?, String?, String?>> = context.dataStore.data.map { prefs ->
        Triple(
            prefs[Keys.USER_EMAIL],
            prefs[Keys.USER_PASSWORD],
            prefs[Keys.USER_TOKEN]
        )
    }

    suspend fun clearCredentials() {
        context.dataStore.edit { prefs ->
            prefs.remove(Keys.USER_EMAIL)
            prefs.remove(Keys.USER_PASSWORD)
            prefs.remove(Keys.USER_TOKEN)
        }
    }
}