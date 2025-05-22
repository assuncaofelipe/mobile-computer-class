package home.felipe.androidretrofit.features.cepsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import home.felipe.androidretrofit.features.cepsearch.data.repository.CepHistoryRepository

class CepHistoryViewModelFactory(
    private val repository: CepHistoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CepHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CepHistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
