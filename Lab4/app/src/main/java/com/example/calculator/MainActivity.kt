package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme
import java.text.NumberFormat
import kotlin.math.ceil

private val MauNen = Color(0xFFF4F4F4)
private val MauChinh = Color(0xFF00897B)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {

            CalculatorTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MauNen
                ) {

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MauNen
                    ) { innerPadding ->

                        TipTimeLayout(
                            modifier = Modifier.padding(
                                innerPadding
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TipTimeLayout(
    modifier: Modifier = Modifier
) {

    var amountInput by remember {
        mutableStateOf("")
    }

    var tipInput by remember {
        mutableStateOf("")
    }

    var roundUp by remember {
        mutableStateOf(false)
    }

    val amount =
        amountInput.toDoubleOrNull() ?: 0.0

    val tipPercent =
        tipInput.toDoubleOrNull() ?: 0.0

    val tip =
        calculateTip(
            amount,
            tipPercent,
            roundUp
        )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment =
            Alignment.CenterHorizontally,
        verticalArrangement =
            Arrangement.Center
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = "Tính Tiền Tip",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MauChinh
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                EditNumberField(
                    label = R.string.bill_amount,
                    leadingIcon = R.drawable.money,
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            keyboardType =
                                KeyboardType.Number,
                            imeAction =
                                ImeAction.Next
                        ),
                    value = amountInput,
                    onValueChanged = {
                        amountInput = it
                    },
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                )

                EditNumberField(
                    label =
                        R.string.how_was_the_service,
                    leadingIcon =
                        R.drawable.precent,
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            keyboardType =
                                KeyboardType.Number,
                            imeAction =
                                ImeAction.Done
                        ),
                    value = tipInput,
                    onValueChanged = {
                        tipInput = it
                    },
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                )

                RoundTheTipRow(
                    roundUp = roundUp,
                    onRoundUpChanged = {
                        roundUp = it
                    },
                    modifier = Modifier.padding(
                        bottom = 24.dp
                    )
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            MauChinh.copy(alpha = 0.1f)
                    )
                ) {

                    Text(
                        text =
                            stringResource(
                                R.string.tip_amount,
                                tip
                            ),
                        modifier = Modifier.padding(
                            20.dp
                        ),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MauChinh
                    )
                }
            }
        }
    }
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        singleLine = true,
        leadingIcon = {

            Icon(
                painter =
                    painterResource(
                        id = leadingIcon
                    ),
                contentDescription = null,
                tint = MauChinh
            )
        },
        label = {
            Text(stringResource(label))
        },
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        shape = RoundedCornerShape(14.dp)
    )
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(
            text =
                stringResource(
                    R.string.round_up_tip
                ),
            fontWeight = FontWeight.Medium
        )

        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(
                    Alignment.End
                )
        )
    }
}

private fun calculateTip(
    amount: Double,
    tipPercent: Double = 15.0,
    roundUp: Boolean
): String {

    var tip =
        tipPercent / 100 * amount

    if (roundUp) {
        tip = ceil(tip)
    }

    return NumberFormat
        .getCurrencyInstance()
        .format(tip)
}

@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {

    CalculatorTheme {
        TipTimeLayout()
    }
}