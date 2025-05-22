@file:OptIn(ExperimentalMaterial3Api::class)

package home.felipe.androidretrofit

import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import home.felipe.androidretrofit.common.utils.theme.AndroidTheme
import home.felipe.androidretrofit.features.cepsearch.data.local.CepDatabase
import home.felipe.androidretrofit.features.cepsearch.data.repository.CepHistoryRepository
import home.felipe.androidretrofit.features.cepsearch.data.repository.CepRepository
import home.felipe.androidretrofit.features.cepsearch.view.CepSearchScreen
import home.felipe.androidretrofit.features.cepsearch.viewmodel.CepHistoryViewModel
import home.felipe.androidretrofit.features.cepsearch.viewmodel.CepSearchViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = CepDatabase.getDatabase(applicationContext)
        val dao = db.cepDao()

        val cepRepository = CepRepository(dao)
        val cepSearchViewModel = CepSearchViewModel(cepRepository)
        val cepHistoryViewModel = CepHistoryViewModel(CepHistoryRepository(dao))

        setContent {
            AndroidTheme {
                AppContent(
                    historyViewModel = cepHistoryViewModel,
                    searchViewModel = cepSearchViewModel
                )
            }
        }
    }

}

@Composable
fun AppContent(
    historyViewModel: CepHistoryViewModel,
    searchViewModel: CepSearchViewModel
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val history by historyViewModel.history.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consulta de CEP") },
                actions = {
                    Row {
                        TextButton(onClick = { selectedTab = 0 }) { Text("Consultar") }
                        TextButton(onClick = { selectedTab = 1 }) { Text("Histórico") }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            when (selectedTab) {
                0 -> CepSearchScreen(
                    modifier = Modifier.fillMaxWidth(),
                    viewModel = searchViewModel
                )

                1 -> Column(modifier = Modifier.fillMaxSize()) {
                    Text("Histórico de CEPs", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (history.isEmpty()) {
                        Text("Nenhum CEP salvo ainda.")
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            history.forEach {
                                Text(
                                    "CEP: ${it.cep} - " + SimpleDateFormat(
                                        "dd/MM/yyyy HH:mm",
                                        Locale.getDefault()
                                    ).format(Date(it.timestamp))
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        val context = LocalContext.current
                        val scope = rememberCoroutineScope()
                        val toast = Toast.makeText(context, "", Toast.LENGTH_LONG )

                        Button(onClick = {
                            scope.launch {
                                val text = history.joinToString("\n") {
                                    "CEP: ${it.cep} - " + SimpleDateFormat(
                                        "dd/MM/yyyy HH:mm", Locale.getDefault()
                                    ).format(Date(it.timestamp))
                                }

                                val file = File(
                                    context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                                    "historico_ceps.txt"
                                )

                                try {
                                    FileOutputStream(file).use { stream ->
                                        stream.write(text.toByteArray())
                                    }
                                    toast.setText("Histórico exportado para ${file.absolutePath}")
                                } catch (e: Exception) {
                                    toast.setText("Erro ao exportar: ${e.message}")
                                }
                                toast.show()
                            }
                        }) {
                            Text("Exportar como TXT")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidTheme {
        Text("Hello Android!")
    }
}