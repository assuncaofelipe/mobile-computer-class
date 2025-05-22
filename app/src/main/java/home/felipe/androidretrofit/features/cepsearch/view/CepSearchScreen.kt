package home.felipe.androidretrofit.features.cepsearch.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import home.felipe.androidretrofit.features.cepsearch.viewmodel.CepSearchViewModel

@Composable
fun CepSearchScreen(
    viewModel: CepSearchViewModel,
    modifier: Modifier
) {
    val state by viewModel.state.collectAsState()
    var inputCep by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = inputCep,
            onValueChange = { inputCep = it },
            label = { Text("Digite o CEP") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.buscarCep(inputCep) }) {
            Text("Buscar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is CepState.Idle -> Text("Digite um CEP para iniciar.")
            is CepState.Loading -> CircularProgressIndicator()
            is CepState.Error -> Text((state as CepState.Error).message, color = MaterialTheme.colorScheme.error)
            is CepState.Success -> {
                val data = (state as CepState.Success).data
                Text("Logradouro: ${data.logradouro ?: "-"}")
                Text("Bairro: ${data.bairro ?: "-"}")
                Text("Cidade: ${data.localidade ?: "-"} - ${data.uf ?: "-"}")
            }
        }
    }
}