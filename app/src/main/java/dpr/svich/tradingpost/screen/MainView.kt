package dpr.svich.tradingpost.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import dpr.svich.tradingpost.model.Stock
import dpr.svich.tradingpost.ui.theme.AccentButtons
import dpr.svich.tradingpost.ui.theme.AccentEnd
import dpr.svich.tradingpost.ui.theme.AccentStart
import dpr.svich.tradingpost.ui.theme.BackgroundEnd
import dpr.svich.tradingpost.ui.theme.BackgroundStart
import dpr.svich.tradingpost.ui.theme.TradingPostTheme

/**
 * Main screen witch contain total balance and list of stock portfolios
 */
@Composable
fun MainView(contentPaddingValues: PaddingValues) {
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
                text = "8 530 RUR",
                fontSize = 40.sp,
                fontFamily = FontFamily.Default,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
        val stockPortfolios = listOf("Акции Сбер", "Tinkoff", "Крипта", "Фьючерсы на гречу")
        LazyColumn {
            for (sp in stockPortfolios) {
                item { MainViewStockPortfolioItem(sp) }
            }
        }
    }
}


@Composable
fun MainViewStockPortfolioItem(name: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End,
        ) {
            Text(text = "453 RUR", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "+$5", fontSize = 18.sp, color = Color.Green)
        }
        val stocks =
            listOf(Stock("ПервыйРеспубликанский"), Stock("ГеркулесМолоко"), Stock("Вектор"))
        Column(Modifier.padding(start = 16.dp, bottom = 8.dp)) {
            for (stock in stocks) {
                MainViewStock(stock = stock)
            }
        }
        Divider(color = AccentButtons, thickness = 1.dp)
    }
}

@Composable
fun MainViewStock(stock: Stock) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = stock.name)
        Row {
            Text(text = "300 RUR")
            Text(text = "+3", color = Color.Green)
        }
    }
}

//@Preview
@Composable
fun ItemPreview() {
    TradingPostTheme {
        MainViewStockPortfolioItem("Фьючерсы на гречку")
    }
}