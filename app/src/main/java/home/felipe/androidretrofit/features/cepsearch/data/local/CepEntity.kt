package home.felipe.androidretrofit.features.cepsearch.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ceps")
data class CepEntity(
    @PrimaryKey val cep: String,
    val timestamp: Long = System.currentTimeMillis()
)