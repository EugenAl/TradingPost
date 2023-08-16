package dpr.svich.tradingpost

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dpr.svich.tradingpost.database.AppDatabase
import dpr.svich.tradingpost.database.StockPortfolioDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StockPortfolioDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var stockPortfolioDao: StockPortfolioDao

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        stockPortfolioDao = db.stockPortfolioDao()
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun waiting(){
        assert(true)
    }
}