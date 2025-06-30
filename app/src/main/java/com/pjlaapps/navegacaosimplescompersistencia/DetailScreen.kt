package com.pjlaapps.navegacaosimplescompersistencia
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    dataStoreManager: DataStoreManager,
    index: Int,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var clickedA by remember { mutableStateOf(false) }
    var clickedB by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        dataStoreManager.getButtonStates().collect { states ->
            when (states[index]) {
                2 -> { clickedA = true; clickedB = true }
                1 -> { clickedA = true; clickedB = false } // ou vice-versa
                0 -> { clickedA = false; clickedB = false }
            }
        }
    }

    fun updateState() {
        val newState = when {
            clickedA && clickedB -> 2
            clickedA || clickedB -> 1
            else -> 0
        }
        scope.launch { dataStoreManager.saveButtonState(index, newState) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Item ${index + 1}", style = MaterialTheme.typography.headlineSmall)

        Button(
            onClick = {
                clickedA = !clickedA
                updateState()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(if (clickedA) Color.Green else Color.Red)
        ) {
            Text("Botão A", color = Color.White)
        }

        Button(
            onClick = {
                clickedB = !clickedB
                updateState()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(if (clickedB) Color.Green else Color.Red)
        ) {
            Text("Botão B", color = Color.White)
        }

        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = onBack) {
            Text("Voltar")
        }
    }
}
