package dpr.svich.tradingpost.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.himanshoe.charty.common.ChartData
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.config.ChartyLabelTextConfig
import com.himanshoe.charty.pie.PieChart
import com.himanshoe.charty.pie.config.PieChartConfig
import com.himanshoe.charty.pie.model.PieData
import dpr.svich.tradingpost.adapter.ChartAdapter
import dpr.svich.tradingpost.ui.theme.AccentButtons
import dpr.svich.tradingpost.ui.theme.AccentEnd
import dpr.svich.tradingpost.ui.theme.AccentStart
import dpr.svich.tradingpost.ui.theme.BackgroundEnd
import dpr.svich.tradingpost.ui.theme.BackgroundStart
import dpr.svich.tradingpost.ui.theme.Pink40
import dpr.svich.tradingpost.viewModel.MainViewViewModel


/**
 * Analytics screen with charts and statistic
 */
@Composable
fun Analytics(
    contentPaddingValues: PaddingValues,
    model: MainViewViewModel = viewModel(factory = MainViewViewModel.Factory)
) {
    var adapter by remember{
        mutableStateOf(ChartAdapter())
    }
    val stockPortfolios = model.stockPortfolioList.observeAsState()
    stockPortfolios.value?.also { adapter = ChartAdapter() }
    stockPortfolios.value?.forEach{
        val stocks = model.loadStocks(it.id).observeAsState()
        stocks.value?.let{stocks ->
            adapter.addEntity(it, stocks)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(BackgroundStart, BackgroundEnd)
                ),
                shape = RectangleShape
            )
            .padding(contentPaddingValues)
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                //.height(250.dp)
                .border(
                    width = 1.dp,
                    color = AccentButtons,
                    shape = RoundedCornerShape(16.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Капитализация портфелей", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            PieChart(
                dataCollection = adapter.loadChart(),
                pieChartConfig = PieChartConfig(donut = true, showLabel = true)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                //.height(250.dp)
                .border(
                    width = 1.dp,
                    color = AccentButtons,
                    shape = RoundedCornerShape(16.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for(row in adapter.loadLegend()){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = row.name, Modifier.width(100.dp))
                    Text(text = row.total)
                    Text(text = row.totalPercent)
                }
            }
        }
    }
}
