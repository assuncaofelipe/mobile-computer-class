package home.felipe.androidretrofit.features.cepsearch.data.repository

import home.felipe.androidretrofit.features.cepsearch.data.local.CepDao
import home.felipe.androidretrofit.features.cepsearch.data.local.CepEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repositório responsável por gerenciar o histórico de buscas de CEPs.
 *
 * Fornece métodos para inserir um novo CEP no histórico, recuperar todos os registros salvos
 * e limpar o histórico. Utiliza o DAO para acessar o banco de dados local Room.
 *
 * @property dao Instância do CepDao para operações no banco de dados.
 */
class CepHistoryRepository(private val dao: CepDao) {
    suspend fun insertCep(cep: String) = dao.insertCep(CepEntity(cep))
    fun getHistory(): Flow<List<CepEntity>> = dao.getAllCeps()
    suspend fun clear() = dao.clearHistory()
}
