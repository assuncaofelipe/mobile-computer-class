package home.felipe.androidretrofit.features.cepsearch.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import home.felipe.androidretrofit.features.cepsearch.viewmodel.CepHistoryViewModel

@Composable
fun CepHistoryScreen(viewModel: CepHistoryViewModel, modifier: Modifier = Modifier) {
    val history by viewModel.history.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {
        Text("Histórico de CEPs", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        if (history.isEmpty()) {
            Text("Nenhum CEP salvo ainda.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(history) { item ->
                    Text("CEP: ${item.cep}")
                    HorizontalDivider()
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.clear() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear history")
        }
    }
}
