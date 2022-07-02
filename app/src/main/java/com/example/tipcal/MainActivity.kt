package com.example.tipcal


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection.Companion.Down
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcal.ui.theme.TipCalTheme
import java.text.NumberFormat

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
fun TipCal() {
    var amountInput by remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }
    val tipamount = tipInput.toDoubleOrNull() ?: 0.0
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    var roundup by remember { mutableStateOf(false) }

    val tip = calculateTip(amount, tipamount,roundup)
    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        EditNumberField(
            value = amountInput,
            onValueChanged = { amountInput = it }
        )
        EditTipField(
            value = tipInput,
            onValueChanged = { tipInput = it }
        )
        RoundUpTip(modifier = Modifier.fillMaxWidth(),
        roundup = roundup,
            onroundup={roundup=it}
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.tip_amount, tip),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun RoundUpTip(modifier: Modifier=Modifier,roundup:Boolean,onroundup:(Boolean)->Unit){
    Row(modifier =modifier,
    horizontalArrangement = Arrangement.SpaceBetween){
        Text(text = "Round up Tip ?", textAlign = TextAlign.Start, fontSize = 20.sp)
        Switch(checked = roundup, onCheckedChange = onroundup,
            colors = SwitchDefaults.colors(
            uncheckedThumbColor = Color.DarkGray
        ))

    }
}
@Composable
fun EditNumberField(
    value: String,
    onValueChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(stringResource(R.string.bill_amount)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(Down) }
        ),
    )
}

@Composable
fun EditTipField(
    value: String,
    onValueChanged: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current

    TextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(stringResource(R.string.how_was_the_service)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )

    )
}

/**
 * Calculates the tip based on the user input and format the tip amount
 * according to the local currency and display it onscreen.
 * Example would be "$10.00".
 */
private fun calculateTip(
    amount: Double,
    tipPercent: Double = 15.0,
    roundup: Boolean
): String {
    var tip = tipPercent / 100 * amount
    if (roundup)
    {  tip = kotlin.math.ceil(tip)}
    return NumberFormat.getCurrencyInstance().format(tip)
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalTheme {
        TipCal()
    }
}