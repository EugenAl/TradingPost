package dpr.svich.tradingpost.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dpr.svich.tradingpost.model.Stock
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {

    @Query("SELECT * FROM stock_table")
    fun getAll(): Flow<List<Stock>>

    @Query("SELECT * FROM STOCK_TABLE WHERE portfolio = :portfolioId")
    fun getStocksForPortfolio(portfolioId: Int): Flow<List<Stock>>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    suspend fun insert(s: Stock)

    @Delete
    suspend fun delete(s: Stock)

    @Delete
    suspend fun delete(s: List<Stock>)
}