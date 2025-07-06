@file:OptIn(ExperimentalMaterial3Api::class)

package home.felipe.androidretrofit

import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import home.felipe.androidretrofit.features.cepsearch.data.local.CepDao
import home.felipe.androidretrofit.features.cepsearch.data.local.CepDatabase
import home.felipe.androidretrofit.features.cepsearch.data.local.CepEntity
import home.felipe.androidretrofit.features.cepsearch.data.repository.CepHistoryRepository
import home.felipe.androidretrofit.features.cepsearch.data.repository.CepRepository
import home.felipe.androidretrofit.features.cepsearch.view.CepSearchScreen
import home.felipe.androidretrofit.features.cepsearch.viewmodel.CepHistoryViewModel
import home.felipe.androidretrofit.features.cepsearch.viewmodel.CepSearchViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Activity principal que inicializa os ViewModels e define o conteúdo da tela usando Compose.
 */
class MainActivity : ComponentActivity() {
    /**
     * Função chamada quando a Activity é criada.
     *
     * @param savedInstanceState Estado salvo da Activity, se houver.
     */
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        val db = CepDatabase.getDatabase(
            applicationContext
        )
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

/**
 * Composable principal que gerencia as abas (Consultar e Histórico) e exibe a tela correspondente.
 *
 * @param historyViewModel ViewModel que fornece o histórico de CEPs.
 * @param searchViewModel ViewModel que gerencia as buscas de CEP.
 */
@Composable
fun AppContent(
    historyViewModel: CepHistoryViewModel,
    searchViewModel: CepSearchViewModel
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val history by historyViewModel.history.collectAsState()

    Scaffold(
        topBar = { AppTopBar { selectedTab = it } }
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

                1 -> CepHistoryScreen(history = history)
            }
        }
    }
}

/**
 * Composable que exibe a barra superior com as opções de navegação entre abas.
 *
 * @param onTabSelected Função de callback chamada ao selecionar uma aba, passando o índice selecionado.
 */
@Composable
fun AppTopBar(onTabSelected: (Int) -> Unit) {
    TopAppBar(
        title = { Text("Consulta de CEP") },
        actions = {
            Row {
                TextButton(onClick = { onTabSelected(0) }) { Text("Consultar") }
                TextButton(onClick = { onTabSelected(1) }) { Text("Histórico") }
            }
        }
    )
}

/**
 * Composable que exibe a lista de histórico de CEPs salvos e permite exportar como arquivo TXT.
 *
 * @param history Lista de objetos CepEntity representando o histórico.
 */
@Composable
fun CepHistoryScreen(history: List<CepEntity>) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val toast = remember { Toast.makeText(context, "", Toast.LENGTH_LONG) }

    Column(modifier = Modifier.fillMaxSize()) {
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

/**
 * Preview do AppContent no modo de design, usando ViewModels fictícios.
 */
@Preview(showBackground = true)
@Composable
fun AppContentPreview() {
    AndroidTheme {
        AppContent(
            historyViewModel = previewHistoryViewModel(),
            searchViewModel = CepSearchViewModel(
                CepRepository(
                    dao = object : CepDao {
                        override suspend fun insertCep(cep: CepEntity) {
                            TODO()
                        }

                        override fun getAllCeps() =
                           flowOf(emptyList<CepEntity>())

                        override suspend fun clearHistory() {
                            TODO()
                        }
                    }
                )
            )
        )
    }
}

/**
 * Preview do CepHistoryScreen no modo de design, com dados fictícios.
 */
@Preview(showBackground = true)
@Composable
fun CepHistoryScreenPreview() {
    AndroidTheme {
        CepHistoryScreen(
            history = listOf(
                CepEntity(
                    cep = "01001-000",
                    timestamp = System.currentTimeMillis()
                )
            )
        )
    }
}

/**
 * Cria um CepHistoryViewModel fictício para uso nos previews.
 *
 * @return CepHistoryViewModel mockado.
 */
fun previewHistoryViewModel() = CepHistoryViewModel(
    CepHistoryRepository(
        dao = object : CepDao {
            override suspend fun insertCep(cep: CepEntity) {
                TODO()
            }

            override fun getAllCeps() =
                flowOf(emptyList<CepEntity>())

            override suspend fun clearHistory() {
                TODO()
            }
        }
    )
)