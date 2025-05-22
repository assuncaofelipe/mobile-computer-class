package home.felipe.androidretrofit.features.cepsearch.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import home.felipe.androidretrofit.common.utils.theme.AndroidTheme
import home.felipe.androidretrofit.features.cepsearch.data.local.CepDatabase
import home.felipe.androidretrofit.features.cepsearch.data.repository.CepRepository
import home.felipe.androidretrofit.features.cepsearch.viewmodel.CepSearchViewModel

class CepSearchActivity : ComponentActivity() {
    private val viewModel = CepSearchViewModel(
        CepRepository(CepDatabase.getDatabase(this).cepDao())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTheme {
                CepSearchScreen(
                    modifier = Modifier.padding(),
                    viewModel = viewModel
                )
            }
        }
    }
}