package dpr.svich.tradingpost.database

import androidx.annotation.WorkerThread
import dpr.svich.tradingpost.model.Stock
import dpr.svich.tradingpost.model.StockPortfolio
import kotlinx.coroutines.flow.Flow

class Repository(private val stockPortfolioDao: StockPortfolioDao, private val stockDao: StockDao) {

    /**
     * API for processing stock portfolios
     */
    val allStockPortfolio: Flow<List<StockPortfolio>> = stockPortfolioDao.getAll()

    @WorkerThread
    suspend fun insertStockPortfolio(sp: StockPortfolio) {
        stockPortfolioDao.insert(sp)
    }

    @WorkerThread
    suspend fun deleteStockPortfolio(sp: StockPortfolio) {
        stockPortfolioDao.delete(sp)
    }

    fun getPortfolioById(id: Int) = stockPortfolioDao.getPortfolioById(id)

    /**
     * API for processing stocks
     */
    fun getStocksFromPortfolio(portfolioId: Int) = stockDao.getStocksForPortfolio(portfolioId)

    @WorkerThread
    suspend fun insertStock(s: Stock){
        stockDao.insert(s)
    }

    @WorkerThread
    suspend fun deleteStock(s: Stock){
        stockDao.delete(s)
    }
}