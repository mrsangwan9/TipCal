package com.example.tipcal


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcal.ui.theme.TipCalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCal()
                }
            }
        }
    }
}
@Composable
fun TipCal(){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(29.dp))
        var tipmoney by remember { mutableStateOf(0)}
       var amount by remember {mutableStateOf("0") }
 Text(
            text = stringResource(R.string.caltip),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.height(29.dp))
        OutlinedTextField(value =amount,
            onValueChange = { amount = it},
            label ={Text(stringResource(R.string.billamount))},
            modifier= Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
        Spacer(modifier = Modifier.height(29.dp))
        Button(onClick = {tipmoney = Integer.parseInt(amount)*15/100;
        amount=""}) {
            Text(text = "Calculate Tip")
        }
        Spacer(modifier = Modifier.height(29.dp))
        Text(text = "You can gave a Tip of \$$tipmoney",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalTheme {
TipCal()
    }
}