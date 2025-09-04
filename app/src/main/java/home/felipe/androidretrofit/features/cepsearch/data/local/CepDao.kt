package home.felipe.androidretrofit.features.cepsearch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCep(cep: CepEntity)

    @Query("SELECT * FROM ceps ORDER BY timestamp DESC")
    fun getAllCeps(): Flow<List<CepEntity>>

    @Query("DELETE FROM ceps")
    suspend fun clearHistory()
}