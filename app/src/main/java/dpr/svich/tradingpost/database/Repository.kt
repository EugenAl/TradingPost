package dpr.svich.tradingpost.database

import androidx.annotation.WorkerThread
import dpr.svich.tradingpost.model.StockPortfolio
import kotlinx.coroutines.flow.Flow

class Repository(private val stockPortfolioDao: StockPortfolioDao) {

    val allStockPortfolio: Flow<List<StockPortfolio>> = stockPortfolioDao.getAll()

    @WorkerThread
    suspend fun insertStockPortfolio(sp: StockPortfolio){
        stockPortfolioDao.insert(sp)
    }

    @WorkerThread
    suspend fun deleteStockPortfolio(sp: StockPortfolio){
        stockPortfolioDao.delete(sp)
    }
}