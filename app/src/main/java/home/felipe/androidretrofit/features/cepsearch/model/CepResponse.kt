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

/**
 * Converte um objeto [CepResponse] em uma entidade [CepEntity] para persistência no banco de dados local.
 *
 * Utiliza o valor do campo `cep` ou uma string vazia caso seja nulo.
 *
 * @return Uma instância de [CepEntity] correspondente ao CEP informado.
 */
fun CepResponse.toEntity(): CepEntity {
    return CepEntity(
        cep = this.cep ?: "",
    )
}