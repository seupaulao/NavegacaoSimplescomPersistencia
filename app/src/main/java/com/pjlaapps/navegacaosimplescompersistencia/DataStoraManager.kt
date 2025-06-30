package com.pjlaapps.navegacaosimplescompersistencia

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "button_status")

class DataStoreManager(private val context: Context) {

    private fun keyFor(index: Int) = intPreferencesKey("button_$index")

    suspend fun saveButtonState(index: Int, state: Int) {
        context.dataStore.edit { prefs ->
            prefs[keyFor(index)] = state
        }
    }

    fun getButtonStates(): Flow<List<Int>> {
        return context.dataStore.data.map { prefs ->
            List(30) { index ->
                prefs[keyFor(index)] ?: 0
            }
        }
    }

    suspend fun resetAll() {
        context.dataStore.edit { prefs ->
            for (i in 0 until 30) {
                prefs[keyFor(i)] = 0
            }
        }
    }
}
