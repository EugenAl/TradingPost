package dpr.svich.tradingpost.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dpr.svich.tradingpost.model.StockPortfolio
import kotlinx.coroutines.flow.Flow

@Dao
interface StockPortfolioDao {
    @Query("SELECT * FROM stock_portfolio_table")
    fun getAll(): Flow<List<StockPortfolio>>

    @Query("SELECT * FROM stock_portfolio_table WHERE id = :id")
    fun getPortfolioById(id:Int): Flow<StockPortfolio>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sp: StockPortfolio)

    @Delete
    suspend fun delete(sp: StockPortfolio)
}