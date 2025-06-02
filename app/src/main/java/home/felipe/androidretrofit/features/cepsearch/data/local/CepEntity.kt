package home.felipe.androidretrofit.features.cepsearch.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade Room que representa um registro de CEP no banco de dados local.
 *
 * @property cep O código do CEP, utilizado como chave primária.
 * @property timestamp Momento em que o registro foi salvo, em milissegundos desde a época Unix.
 */
@Entity(tableName = "ceps")
data class CepEntity(
    @PrimaryKey val cep: String,
    val timestamp: Long = System.currentTimeMillis()
)