package dpr.svich.tradingpost.adapter

import com.himanshoe.charty.common.ChartData
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.pie.model.PieData
import dpr.svich.tradingpost.model.Stock
import dpr.svich.tradingpost.model.StockPortfolio
import dpr.svich.tradingpost.screen.format
import dpr.svich.tradingpost.ui.theme.AccentEnd
import dpr.svich.tradingpost.ui.theme.AccentStart
import dpr.svich.tradingpost.ui.theme.DeleteButton
import dpr.svich.tradingpost.ui.theme.Pink40
import dpr.svich.tradingpost.ui.theme.Purple40

class ChartAdapter {
    private val entities: MutableList<AdapterEntity> = mutableListOf()
    private val colors = listOf(Pink40, AccentStart, AccentEnd, Purple40, DeleteButton)

    fun addEntity(sp: StockPortfolio, stocks: List<Stock>) {
        entities.add(AdapterEntity(sp, stocks))
    }

    fun loadChart(): ChartDataCollection {
        val list = mutableListOf<PieData>()
        for ((color, e) in entities.withIndex()) {
            list.add(
                PieData(
                    e.stocks.sumOf { it.price * it.count }.toFloat(),
                    e.sp.name,
                    colors[color % colors.size]
                )
            )
        }
        return ChartDataCollection(list)
    }

    fun loadLegend(): List<LabelWrapper> {
        val list = mutableListOf<LabelWrapper>()
        val sumOfPortfolio =
            entities.sumOf { it.stocks.sumOf { stock -> stock.price * stock.count } }
        for (e in entities) {
            val total = e.stocks.sumOf { it.count * it.price }
            val percent = total * 100 / sumOfPortfolio
            list.add(LabelWrapper(
                e.sp.name,
                "${total.format(2)} ₽",
                "${percent.format(2)}%"))
        }
        return list
    }

    data class LabelWrapper(val name: String, val total: String, val totalPercent: String)

    data class AdapterEntity(val sp: StockPortfolio, val stocks: List<Stock>)
}