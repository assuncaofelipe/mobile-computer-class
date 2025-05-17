package home.felipe.androidretrofit.features.cepsearch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import home.felipe.androidretrofit.common.ui.theme.AndroidTheme

class CepSearchActivity : ComponentActivity() {
    private val viewModel = CepSearchViewModel()

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