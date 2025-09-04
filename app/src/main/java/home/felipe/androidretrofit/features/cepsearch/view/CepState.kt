package home.felipe.androidretrofit.features.cepsearch.view

import home.felipe.androidretrofit.features.cepsearch.model.CepResponse

sealed class CepState {
    data object Idle : CepState()
    data object Loading : CepState()
    data class Success(val data: CepResponse) : CepState()
    data class Error(val message: String) : CepState()
}