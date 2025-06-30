package com.pjlaapps.navegacaosimplescompersistencia
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ButtonListScreen(
    dataStoreManager: DataStoreManager,
    onItemClick: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val states by dataStoreManager.getButtonStates().collectAsState(initial = List(30) { 0 })

    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = { scope.launch { dataStoreManager.resetAll() } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Resetar Todos")
        }

        Column {
            states.forEachIndexed { index, state ->
                val color = when (state) {
                    2 -> Color.Green
                    1 -> Color.Yellow
                    else -> Color.Red
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .height(50.dp)
                        .background(color)
                        .clickable { onItemClick(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Item ${index + 1}", color = Color.White)
                }
            }
        }
    }
}
