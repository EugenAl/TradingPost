package dpr.svich.tradingpost.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dpr.svich.tradingpost.model.Stock
import dpr.svich.tradingpost.ui.theme.AccentButtons
import dpr.svich.tradingpost.ui.theme.AccentEnd
import dpr.svich.tradingpost.ui.theme.AccentStart
import dpr.svich.tradingpost.ui.theme.BackgroundEnd
import dpr.svich.tradingpost.ui.theme.BackgroundStart
import dpr.svich.tradingpost.ui.theme.TradingPostTheme
import dpr.svich.tradingpost.viewModel.MainViewViewModel
import dpr.svich.tradingpost.viewModel.ProfileViewModel

/**
 * Main screen witch contain total balance and list of stock portfolios
 */
@Composable
fun MainView(contentPaddingValues: PaddingValues,
    model: MainViewViewModel = viewModel(factory = MainViewViewModel.Factory)) {
    var totalCost = 0.0
    val totalCostView = remember {
        mutableStateOf(0.0)
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(colors = listOf(AccentStart, AccentEnd)),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(8.dp),
        ) {
            Text(
                text = "${totalCostView.value.format(2)} ₽",
                fontSize = 40.sp,
                fontFamily = FontFamily.Default,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
        val stockPortfolios = model.stockPortfolioList.observeAsState()
        LazyColumn{
            stockPortfolios.value?.let{ list ->
                items(list.size){
                    val stocks = model.loadStocks(list[it].id).observeAsState()
                    stocks.value?.let{stocks ->
                        MainViewStockPortfolioItem(name = list[it].name, stocks)
                        totalCost+=stocks.sumOf { stock -> stock.price*stock.count }
                        totalCostView.value = totalCost
                    }
                }
                totalCost = 0.0
            }
        }
    }
}


@Composable
fun MainViewStockPortfolioItem(name: String, stocks:List<Stock>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End,
        ) {
            val totalCostOfPortfolio = stocks.sumOf { it.count * it.price }
            Text(text = "${totalCostOfPortfolio.format(2)} ₽",
                fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
        for(s in stocks){
            MainViewStock(stock = s)
        }
        Divider(color = AccentButtons, thickness = 1.dp)
    }
}

@Composable
fun MainViewStock(stock: Stock) {
    Column(modifier = Modifier.padding(vertical = 1.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.width(200.dp)) {
                Text(stock.index, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(
                    text = "${stock.count} шт.◈${stock.price} ₽",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Thin
                )
            }
            Column(Modifier.padding(horizontal = 4.dp)) {
                Text(
                    text = (stock.count * stock.price).toString() + "₽",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)