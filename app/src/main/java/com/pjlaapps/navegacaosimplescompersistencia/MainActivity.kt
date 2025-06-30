package com.pjlaapps.navegacaosimplescompersistencia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStoreManager = DataStoreManager(applicationContext)

        setContent {
            MaterialTheme {
                var currentScreen by remember { mutableStateOf("main") }
                var selectedIndex by remember { mutableStateOf(-1) }

                when (currentScreen) {
                    "main" -> {
                        ButtonListScreen(
                            dataStoreManager = dataStoreManager,
                            onItemClick = {
                                selectedIndex = it
                                currentScreen = "detail"
                            }
                        )
                    }
                    "detail" -> {
                        DetailScreen(
                            dataStoreManager = dataStoreManager,
                            index = selectedIndex,
                            onBack = {
                                currentScreen = "main"
                            }
                        )
                    }
                }
            }
        }
    }
}

