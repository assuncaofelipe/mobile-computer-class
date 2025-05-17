package home.felipe.androidretrofit.features.cepsearch.data

import home.felipe.androidretrofit.features.cepsearch.data.api.CepApi
import home.felipe.androidretrofit.features.cepsearch.data.model.CepResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CepRepository {

    private val api: CepApi = Retrofit.Builder()
        .baseUrl("https://viacep.com.br/ws/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CepApi::class.java)

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
