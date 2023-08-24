package dpr.svich.tradingpost.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.End
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dpr.svich.tradingpost.model.Stock
import dpr.svich.tradingpost.model.StockPortfolio
import dpr.svich.tradingpost.ui.theme.AccentButtons
import dpr.svich.tradingpost.ui.theme.AccentEnd
import dpr.svich.tradingpost.ui.theme.AccentStart
import dpr.svich.tradingpost.ui.theme.BackgroundEnd
import dpr.svich.tradingpost.ui.theme.BackgroundStart
import dpr.svich.tradingpost.ui.theme.DeleteButton
import dpr.svich.tradingpost.viewModel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioEdit(
    stockPortfolioId: String,
    contentPaddingValues: PaddingValues,
    model: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory)
) {

    val stockPortfolio = model.getStockPortfolio(stockPortfolioId.toInt()).observeAsState()
    val stocks = model.loadStocks(stockPortfolioId.toInt()).observeAsState()

    val openDialogDeleteStock = remember {
        mutableStateOf(false)
    }
    val deletedStock = remember {
        mutableStateOf(0)
    }

    val inputValueStockIndex = remember {
        mutableStateOf(TextFieldValue())
    }
    val inputValueStockPrice = remember {
        mutableStateOf(TextFieldValue())
    }
    val inputValueStockCount = remember {
        mutableStateOf(TextFieldValue())
    }

    if (openDialogDeleteStock.value) {
        val stock = stocks.value?.let { it.find { stock -> stock.id == deletedStock.value } }
        if (stock == null) {
            openDialogDeleteStock.value = false
        } else {
            AlertDialog(
                onDismissRequest = { openDialogDeleteStock.value = false },
                title = { Text(text = "Удаление акций") },
                text = { Text(text = "Действительно удалить ${stock.count} акций ${stock.index}?") },
                confirmButton = {
                    Button(
                        onClick = {
                            model.deleteStock(stock)
                            openDialogDeleteStock.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DeleteButton,
                            contentColor = Color.Red
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(text = "Удалить", textAlign = TextAlign.Center)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialogDeleteStock.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentButtons,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(text = "Отмена", textAlign = TextAlign.Center)
                    }
                },
                shape = CutCornerShape(topStart = 16.dp, bottomEnd = 16.dp),
                containerColor = AccentEnd
            )
        }
    }

    // max length of Index field in stock
    val indexSize = 4
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPaddingValues)
            .background(
                Brush.verticalGradient(colors = listOf(BackgroundStart, BackgroundEnd)),
                shape = RectangleShape
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stockPortfolio.value?.name ?: "Unknown", fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp)
        )
        /**
         * Add stock block
         */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(colors = listOf(AccentEnd, Color.Transparent)),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(4.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = inputValueStockIndex.value,
                    onValueChange = {
                        if (it.text.length <= indexSize) inputValueStockIndex.value = it
                    },
                    label = { Text("Индекс", color = AccentStart) },
                    placeholder = { Text("Введите индекс", color = Color.White) },
                    modifier = Modifier
                        .padding(all = 0.dp)
                        .defaultMinSize(24.dp),
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
                        capitalization = KeyboardCapitalization.Characters,

                        // below line is to enable auto
                        // correct in our keyboard.
                        autoCorrect = false,

                        // below line is used to specify our
                        // type of keyboard such as text, number, phone.
                        keyboardType = KeyboardType.Text,
                        ImeAction.Next
                    ),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )//index
                Spacer(modifier = Modifier.width(16.dp))
                TextField(
                    value = inputValueStockPrice.value,
                    onValueChange = { inputValueStockPrice.value = it },
                    label = { Text("Цена", color = AccentStart) },
                    placeholder = { Text("Введите цену", color = Color.White) },
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .defaultMinSize(24.dp),
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
                        capitalization = KeyboardCapitalization.None,

                        // below line is to enable auto
                        // correct in our keyboard.
                        autoCorrect = false,

                        // below line is used to specify our
                        // type of keyboard such as text, number, phone.
                        keyboardType = KeyboardType.Decimal,
                        ImeAction.Next
                    ),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )//prise
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                TextField(
                    value = inputValueStockCount.value,
                    onValueChange = { inputValueStockCount.value = it },
                    label = { Text("Количество", color = AccentStart) },
                    placeholder = { Text("Введите кол-во", color = Color.White) },
                    modifier = Modifier
                        .padding(all = 0.dp)
                        .defaultMinSize(24.dp),
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
                        capitalization = KeyboardCapitalization.Characters,

                        // below line is to enable auto
                        // correct in our keyboard.
                        autoCorrect = false,

                        // below line is used to specify our
                        // type of keyboard such as text, number, phone.
                        keyboardType = KeyboardType.Decimal,
                        ImeAction.Done
                    ),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )//count
                Button(
                    onClick = {
                        if (inputValueStockIndex.value.text.isNotEmpty() and
                            inputValueStockCount.value.text.isNotEmpty() and
                            inputValueStockPrice.value.text.isNotEmpty()
                        ) {
                            try {
                                model.addStock(
                                    inputValueStockIndex.value.text,
                                    inputValueStockCount.value.text.toInt(),
                                    inputValueStockPrice.value.text.toDouble(),
                                    stockPortfolioId.toInt()
                                )
                                inputValueStockIndex.value = TextFieldValue()
                                inputValueStockCount.value = TextFieldValue()
                                inputValueStockPrice.value = TextFieldValue()
                            } catch (e: Exception) {
                                Log.e("Add Stock", "Something went wrong")
                                e.printStackTrace()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentButtons,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "buy",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                    Text(text = "Купить")
                }
            }
        }

        /**
         * List of stocks
         */
        LazyColumn {
            stocks.value?.let { list ->
                items(list.size) {
                    StockItem(list[it]) {
                        //model.deleteStock(list[it])
                        deletedStock.value = list[it].id
                        openDialogDeleteStock.value = true
                    }
                }
            }
        }
    }
}

@Composable
fun StockItem(s: Stock, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.width(200.dp)) {
            Text(s.index, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(
                text = "${s.count} шт.◈${s.price} ₽",
                fontSize = 12.sp,
                fontWeight = FontWeight.Thin
            )
            Divider(color = AccentButtons, thickness = 1.dp)
        }

        Row(Modifier.padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = (s.count * s.price).toString() + "₽",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete stock",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        }
    }
}