package home.felipe.androidretrofit.features.cepsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import home.felipe.androidretrofit.features.cepsearch.data.local.CepEntity
import home.felipe.androidretrofit.features.cepsearch.data.repository.CepHistoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CepHistoryViewModel(private val repository: CepHistoryRepository) : ViewModel() {
    val history: StateFlow<List<CepEntity>> = repository.getHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insert(cep: String) = viewModelScope.launch {
        repository.insertCep(cep)
    }

    fun clear() = viewModelScope.launch {
        repository.clear()
    }
}
