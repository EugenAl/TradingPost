package dpr.svich.tradingpost.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_table")
data class Stock(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val index: String,
    val count: Int,
    val price: Double,
    val portfolio: Int
)
