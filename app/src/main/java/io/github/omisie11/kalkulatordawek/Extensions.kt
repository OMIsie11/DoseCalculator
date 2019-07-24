package io.github.omisie11.kalkulatordawek

import android.widget.EditText

fun EditText.validateNumericInput(): Boolean {
    val input: String = this.text.toString()
    return when {
        input.isBlank() -> false
        input.toDouble() > 0.0 -> true
        else -> false
    }
}