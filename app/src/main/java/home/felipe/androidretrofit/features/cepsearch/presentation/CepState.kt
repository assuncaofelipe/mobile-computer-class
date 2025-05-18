package home.felipe.androidretrofit.features.cepsearch.presentation

import home.felipe.androidretrofit.features.cepsearch.data.model.CepResponse

sealed class CepState {
    data object Idle : CepState()
    data object Loading : CepState()
    data class Success(val data: CepResponse) : CepState()
    data class Error(val message: String) : CepState()
}