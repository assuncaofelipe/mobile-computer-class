package home.felipe.androidretrofit.features.cepsearch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import home.felipe.androidretrofit.common.utils.isValidCep
import home.felipe.androidretrofit.features.cepsearch.data.CepRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CepSearchViewModel : ViewModel() {

    private val repository = CepRepository()

    private val _state = MutableStateFlow<CepState>(CepState.Idle)
    val state: StateFlow<CepState> = _state

    fun buscarCep(cep: String) {
        if (!cep.isValidCep()) {
            _state.value = CepState.Error("CEP inválido. Deve conter 8 dígitos.")
            return
        }

        viewModelScope.launch {
            _state.value = CepState.Loading
            val result = repository.buscarCep(cep)
            _state.value = result.fold(
                onSuccess = { CepState.Success(it) },
                onFailure = { CepState.Error(it.message ?: "Erro desconhecido") }
            )
        }
    }
}