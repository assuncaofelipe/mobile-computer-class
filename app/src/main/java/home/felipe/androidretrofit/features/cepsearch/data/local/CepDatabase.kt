package home.felipe.androidretrofit.features.cepsearch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Classe abstrata que representa o banco de dados Room para armazenar entidades de CEP.
 *
 * - Define as entidades do banco (CepEntity).
 * - Fornece acesso ao DAO (CepDao) para operações de banco de dados.
 * - Implementa o padrão singleton para garantir uma única instância do banco durante o ciclo de vida do app.
 * - O método getDatabase retorna a instância do banco, criando-a se necessário.
 */
@Database(entities = [CepEntity::class], version = 1)
abstract class CepDatabase : RoomDatabase() {
    abstract fun cepDao(): CepDao

    companion object {
        @Volatile
        private var INSTANCE: CepDatabase? = null

        fun getDatabase(context: Context): CepDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CepDatabase::class.java,
                    "cep_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
