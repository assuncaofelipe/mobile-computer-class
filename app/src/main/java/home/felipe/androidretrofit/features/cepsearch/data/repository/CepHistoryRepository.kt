package home.felipe.androidretrofit.features.cepsearch.data.repository

import home.felipe.androidretrofit.features.cepsearch.data.local.CepDao
import home.felipe.androidretrofit.features.cepsearch.data.local.CepEntity
import kotlinx.coroutines.flow.Flow

class CepHistoryRepository(private val dao: CepDao) {
    suspend fun insertCep(cep: String) = dao.insertCep(CepEntity(cep))
    fun getHistory(): Flow<List<CepEntity>> = dao.getAllCeps()
    suspend fun clear() = dao.clearHistory()
}
