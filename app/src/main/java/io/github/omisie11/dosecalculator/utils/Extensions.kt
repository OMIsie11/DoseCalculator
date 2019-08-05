package io.github.omisie11.dosecalculator.utils

import android.widget.EditText
import java.math.BigDecimal
import java.math.RoundingMode

fun EditText.validateNumericInput(): Boolean {
    val input: String = this.text.toString()
    return when {
        input.isBlank() -> false
        input.toDouble() > 0.0 -> true
        else -> false
    }
}

fun Double.roundToTwoDecimalPoints() = BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN).toDouble()