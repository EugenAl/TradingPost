package dpr.svich.tradingpost

import android.app.Application
import dpr.svich.tradingpost.database.AppDatabase
import dpr.svich.tradingpost.database.Repository

class TradingApplication: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { Repository(database.stockPortfolioDao())}
}