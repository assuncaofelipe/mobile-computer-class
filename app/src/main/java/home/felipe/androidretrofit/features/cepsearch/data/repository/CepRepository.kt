package home.felipe.androidretrofit.features.cepsearch.data.repository

import home.felipe.androidretrofit.features.cepsearch.data.api.CepApi
import home.felipe.androidretrofit.features.cepsearch.data.local.CepDao
import home.felipe.androidretrofit.features.cepsearch.data.local.CepEntity
import home.felipe.androidretrofit.features.cepsearch.model.CepResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CepRepository (private val dao: CepDao) {

    /**
     * Instância do Retrofit configurada para acessar a API de CEPs.
     * Utiliza o GsonConverterFactory para converter as respostas JSON em objetos Kotlin.
     */
    private val api: CepApi = Retrofit.Builder()
        .baseUrl("https://viacep.com.br/ws/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CepApi::class.java)

    suspend fun salvarCep(cep: CepEntity) {
        dao.insertCep(cep)
    }

    fun obterHistorico(): Flow<List<CepEntity>> {
        return dao.getAllCeps()
    }


    /**
     * Busca informações de um CEP utilizando a API externa.
     *
     * @param cep O código do CEP a ser consultado.
     * @return Um [Result] contendo [CepResponse] se encontrado, ou uma exceção em caso de erro ou CEP inexistente.
     */
    suspend fun buscarCep(cep: String): Result<CepResponse> = try {
        val response = api.setCep(cep)
        if (response.cep != null) {
            Result.success(response)
        } else {
            Result.failure(Exception("CEP não encontrado"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
