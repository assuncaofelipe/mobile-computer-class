package home.felipe.androidretrofit.features.cepsearch.model

import home.felipe.androidretrofit.features.cepsearch.data.local.CepEntity

data class CepResponse(
    val cep: String?,
    val logradouro: String?,
    val complemento: String?,
    val bairro: String?,
    val localidade: String?,
    val uf: String?,
    val ibge: String?,
    val gia: String?,
    val ddd: String?,
    val siafi: String?
)

fun CepResponse.toEntity(): CepEntity {
    return CepEntity(
        cep = this.cep ?: "",
    )
}