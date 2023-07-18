package dpr.svich.tradingpost

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dpr.svich.tradingpost.model.Stock
import dpr.svich.tradingpost.ui.theme.AccentButtons
import dpr.svich.tradingpost.ui.theme.AccentEnd
import dpr.svich.tradingpost.ui.theme.AccentStart
import dpr.svich.tradingpost.ui.theme.BackgroundEnd
import dpr.svich.tradingpost.ui.theme.BackgroundStart
import dpr.svich.tradingpost.ui.theme.DeleteButton
import dpr.svich.tradingpost.ui.theme.Purple40
import dpr.svich.tradingpost.ui.theme.TradingPostTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TradingPostTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    MainView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val inputvalue = remember { mutableStateOf(TextFieldValue()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(BackgroundStart, BackgroundEnd)
                ),
                shape = RectangleShape
            )
    ) {
        Text(
            text = "Trading Post",
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 64.dp),
            fontSize = 60.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.Cursive
        )
        Image(
            painter = painterResource(id = R.drawable.greeteng_icon),
            contentDescription = "trading icon",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .height(128.dp)
        )
        TextField(
            value = inputvalue.value,
            onValueChange = { inputvalue.value = it },
            label = { Text("Владелец", color = Color.White) },
            placeholder = { Text(text = "Введите имя") },
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.White,
                // below line is used to add font
                // size for our text field
                fontSize = 22.sp,

                // below line is use to change font family.
                fontFamily = FontFamily.Monospace
            ),

            keyboardOptions = KeyboardOptions(
                // below line is use for capitalization
                // inside our text field.
                capitalization = KeyboardCapitalization.Words,

                // below line is to enable auto
                // correct in our keyboard.
                autoCorrect = true,

                // below line is used to specify our
                // type of keyboard such as text, number, phone.
                keyboardType = KeyboardType.Text,
                ImeAction.Done
            ),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
        )
        Box(Modifier.fillMaxSize(), contentAlignment = Center) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.linearGradient(colors = listOf(AccentStart, AccentEnd)),
                            shape = CircleShape
                        )
                        .size(64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Adding an Icon "Add" inside the Button
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "content description",
                        tint = Color.White
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile() {
    val openDialogCreateStockPortfolio = remember {
        mutableStateOf(false)
    }
    val inputValueNameStockPortfolio = remember {
        mutableStateOf(TextFieldValue())
    }
    if (openDialogCreateStockPortfolio.value) {
        // Alert dialog for create stock portfolio
        AlertDialog(
            onDismissRequest = { openDialogCreateStockPortfolio.value = false },
            title = { Text(text = "Создать портфель") },
            text = {
                TextField(
                    value = inputValueNameStockPortfolio.value,
                    onValueChange = { inputValueNameStockPortfolio.value = it },
                    label = { Text("Название", color = Color.White) },
                    placeholder = { Text(text = "Введите название") },
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(
                        color = Color.White,
                        // below line is used to add font
                        // size for our text field
                        fontSize = 14.sp,

                        // below line is use to change font family.
                        fontFamily = FontFamily.Default
                    ),

                    keyboardOptions = KeyboardOptions(
                        // below line is use for capitalization
                        // inside our text field.
                        capitalization = KeyboardCapitalization.Sentences,

                        // below line is to enable auto
                        // correct in our keyboard.
                        autoCorrect = true,

                        // below line is used to specify our
                        // type of keyboard such as text, number, phone.
                        keyboardType = KeyboardType.Text,
                        ImeAction.Done
                    ),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            },
            confirmButton = {
                Button(
                    onClick = { openDialogCreateStockPortfolio.value = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentButtons,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Создать", textAlign = TextAlign.Center)
                }
            },
            shape = CutCornerShape(topStart = 16.dp, bottomEnd = 16.dp),
            containerColor = AccentEnd
        )
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
            .padding(8.dp)
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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Котик",
                    modifier = Modifier.padding(bottom = 32.dp),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {

                Text(
                    text = "Портфелей: 4",
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Justify
                )

                Button(
                    onClick = { openDialogCreateStockPortfolio.value = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentButtons,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Settings",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                    Text(text = "Создать", textAlign = TextAlign.Center)
                }
            }
        }
        val stockPortfolios = listOf("Акции Сбер", "Tinkoff", "Крипта", "Фьючерсы на гречу")
        LazyColumn {
            for (sp in stockPortfolios) {
                item { StockPortfolioItem(sp) }
            }
        }
    }
}

@Composable
fun MainView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(BackgroundStart, BackgroundEnd)
                ),
                shape = RectangleShape
            )
            .padding(8.dp)
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
                    .align(CenterHorizontally),
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingPreview() {
    TradingPostTheme {
        MainView()
    }
}

@Composable
fun StockPortfolioItem(name: String = "Шикаладка") {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Text(text = "Содержимое:")
            Row {
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentButtons,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Add",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeleteButton,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Add",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
            }
        }
        val stocks = listOf<Stock>(Stock("SBER"), Stock("MMQ"), Stock("TIFF"))
        Column(Modifier.padding(start = 16.dp, bottom = 8.dp)) {
            for (stock in stocks) {
                Text(text = stock.name)
            }
        }
        Divider(color = AccentButtons, thickness = 1.dp)
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

@Preview
@Composable
fun ItemPreview() {
    TradingPostTheme {
        MainViewStockPortfolioItem("Фьючерсы на гречку")
    }
}