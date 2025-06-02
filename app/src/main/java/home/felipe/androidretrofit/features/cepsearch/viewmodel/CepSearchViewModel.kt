package home.felipe.androidretrofit.features.cepsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import home.felipe.androidretrofit.common.utils.isValidCep
import home.felipe.androidretrofit.features.cepsearch.data.repository.CepRepository
import home.felipe.androidretrofit.features.cepsearch.model.toEntity
import home.felipe.androidretrofit.features.cepsearch.view.CepState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CepSearchViewModel(private val cepRepository: CepRepository) : ViewModel() {

    private val _state = MutableStateFlow<CepState>(CepState.Idle)
    val state: StateFlow<CepState> = _state

    /**
     * Busca informações de um CEP informado.
     *
     * Valida o formato do CEP, atualiza o estado para Loading, faz a requisição ao repositório
     * e atualiza o estado conforme o resultado:
     * - Em caso de sucesso, salva o CEP no histórico e retorna o estado Success com os dados.
     * - Em caso de erro, retorna o estado Error com a mensagem correspondente.
     *
     * @param cep CEP a ser consultado.
     */
    fun buscarCep(cep: String) {
        if (!cep.isValidCep()) {
            _state.value = CepState.Error("CEP inválido. Deve conter 8 dígitos.")
            return
        }

        viewModelScope.launch {
            _state.value = CepState.Loading
            val result = cepRepository.buscarCep(cep)
            _state.value = result.fold(
                onSuccess = {
                    cepRepository.salvarCep(it.toEntity())
                    CepState.Success(it)
                },
                onFailure = {
                    CepState.Error(it.message ?: "Erro desconhecido")
                }
            )
        }
    }
}
