package dpr.svich.tradingpost.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dpr.svich.tradingpost.model.StockPortfolio

@Database(entities = [StockPortfolio::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun stockPortfolioDao(): StockPortfolioDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}