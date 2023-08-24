package dpr.svich.tradingpost.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.runtime.currentCompositionLocalContext
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
import androidx.navigation.NavController
import dpr.svich.tradingpost.Router
import dpr.svich.tradingpost.model.Stock
import dpr.svich.tradingpost.model.StockPortfolio
import dpr.svich.tradingpost.ui.theme.AccentButtons
import dpr.svich.tradingpost.ui.theme.AccentEnd
import dpr.svich.tradingpost.ui.theme.AccentStart
import dpr.svich.tradingpost.ui.theme.BackgroundEnd
import dpr.svich.tradingpost.ui.theme.BackgroundStart
import dpr.svich.tradingpost.ui.theme.DeleteButton
import dpr.svich.tradingpost.viewModel.ProfileViewModel

/**
 * Profile screen which contain username, preferences button and stock portfolios button
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    navController: NavController,
    contentPaddingValues: PaddingValues,
    model: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory)
) {
    val openDialogCreateStockPortfolio = remember {
        mutableStateOf(false)
    }

    val openDialogDeleteStockPortfolio = remember {
        mutableStateOf(false)
    }

    val deletePortfolio = remember {
        mutableStateOf(0)
    }

    val inputValueNameStockPortfolio = remember {
        mutableStateOf(TextFieldValue())
    }

    val stockPortfolios = model.stockPortfolioList.observeAsState()

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
                    onClick = {
                        if (inputValueNameStockPortfolio.value.text.isNotEmpty()) {
                            openDialogCreateStockPortfolio.value = false
                            model.addStockPortfolio(inputValueNameStockPortfolio.value.text, "dscr")
                            inputValueNameStockPortfolio.value = TextFieldValue("")
                        }
                    },
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

    if (openDialogDeleteStockPortfolio.value) {
        val portfolio =
            stockPortfolios.value?.let {
                it.find { portfolio -> portfolio.id == deletePortfolio.value }
            }
        if(portfolio == null){
            openDialogDeleteStockPortfolio.value = false
        } else {
            AlertDialog(
                onDismissRequest = { openDialogDeleteStockPortfolio.value = false },
                title = { Text(text = "Удалить портфель") },
                text = { Text(text = "Действительно удалить ${portfolio.name}?") },
                confirmButton = {
                    Button(
                        onClick = {
                            model.deleteStockPortfolio(portfolio)
                            openDialogDeleteStockPortfolio.value = false
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
                            openDialogDeleteStockPortfolio.value = false
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
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Портфелей: ${stockPortfolios.value?.size}",
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
        LazyColumn {
            stockPortfolios.value?.let { list ->
                items(list.size) {
                    StockPortfolioItem(
                        list[it],
                        onDeleteClick = {
                            Log.d("Profile View", "Delete ${list[it].name}")
                            deletePortfolio.value = list[it].id
                            openDialogDeleteStockPortfolio.value = true
                        },
                        onClick = {
                            Log.d("StockPortfolioItem", "Click! ${list[it].name}")
                            navController.navigate(
                                Router.PORTFOLIO_EDIT_SCREEN.replace(
                                    "{id}",
                                    list[it].id.toString()
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StockPortfolioItem(
    stockPortfolio: StockPortfolio,
    onDeleteClick: () -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = stockPortfolio.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Содержимое:")
            Row {
                Button(
                    onClick = onClick,
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
                    onClick = onDeleteClick,
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
        Divider(color = AccentButtons, thickness = 1.dp)
    }
}
