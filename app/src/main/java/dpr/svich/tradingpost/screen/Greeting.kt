package dpr.svich.tradingpost.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
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
import dpr.svich.tradingpost.R
import dpr.svich.tradingpost.ui.theme.AccentEnd
import dpr.svich.tradingpost.ui.theme.AccentStart
import dpr.svich.tradingpost.ui.theme.BackgroundEnd
import dpr.svich.tradingpost.ui.theme.BackgroundStart

/**
 * Greeting screen with authorization
 */
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
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
