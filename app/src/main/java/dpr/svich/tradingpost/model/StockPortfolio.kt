package dpr.svich.tradingpost.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_portfolio_table")
data class StockPortfolio(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String
)