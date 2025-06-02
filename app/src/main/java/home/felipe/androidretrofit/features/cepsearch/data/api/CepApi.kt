package home.felipe.androidretrofit.features.cepsearch.data.api

import home.felipe.androidretrofit.features.cepsearch.model.CepResponse
import retrofit2.http.GET
import retrofit2.http.Path


fun interface CepApi {
    @GET("{cep}/json/")
    suspend fun setCep(@Path("cep") cep: String): CepResponse
}

