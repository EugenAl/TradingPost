package dpr.svich.tradingpost.database

import dpr.svich.tradingpost.model.StockPortfolio
import kotlinx.coroutines.flow.Flow

class Repository(private val stockPortfolioDao: StockPortfolioDao) {

    val allStockPortfolio: Flow<List<StockPortfolio>> = stockPortfolioDao.getAll()
}