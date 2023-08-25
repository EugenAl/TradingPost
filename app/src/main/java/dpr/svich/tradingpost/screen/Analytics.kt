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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himanshoe.charty.common.ChartData
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.config.ChartyLabelTextConfig
import com.himanshoe.charty.pie.PieChart
import com.himanshoe.charty.pie.config.PieChartConfig
import com.himanshoe.charty.pie.model.PieData
import dpr.svich.tradingpost.ui.theme.AccentButtons
import dpr.svich.tradingpost.ui.theme.AccentEnd
import dpr.svich.tradingpost.ui.theme.AccentStart
import dpr.svich.tradingpost.ui.theme.BackgroundEnd
import dpr.svich.tradingpost.ui.theme.BackgroundStart
import dpr.svich.tradingpost.ui.theme.Pink40


/**
 * Analytics screen with charts and statistic
 */
@Composable
fun Analytics(contentPaddingValues: PaddingValues) {
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
                dataCollection = ChartDataCollection(
                    listOf(
                        PieData(
                            2746f,
                            "Пенькофф",
                            Pink40
                        ),
                        PieData(
                            16452f,
                            "Фьючерсы на гречку",
                            AccentStart
                        ),
                        PieData(
                            857.34f,
                            "Крипта",
                            AccentEnd
                        )
                    )
                ),
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
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Пенькоф", Modifier.width(100.dp))
                Text(text = "${2746.0.format(2)} ₽")
                Text(text = "${(2746*100/(2746+16452+857.34)).format(2)}%")
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Фьючерсы на гречку", Modifier.width(100.dp))
                Text(text = "${16452.74.format(2)} ₽")
                Text(text = "${(16452*100/(2746+16452+857.34)).format(2)}%")
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Крипта", Modifier.width(100.dp))
                Text(text = "${857.34.format(2)} ₽")
                Text(text = "${(857.34*100/(2746+16452+857.34)).format(2)}%")
            }
        }
    }
}
