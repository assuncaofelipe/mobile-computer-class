package home.felipe.androidretrofit.features.cepsearch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

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
