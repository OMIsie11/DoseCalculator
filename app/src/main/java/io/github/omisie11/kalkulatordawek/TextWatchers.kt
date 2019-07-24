package io.github.omisie11.kalkulatordawek

import android.text.TextWatcher
import android.text.Editable
import android.widget.EditText


class DoseTextWatcher(private val editText: EditText) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        val input = s.toString()
        when {
            input.isBlank() -> editText.error = "To pole nie może być puste"
            input.toDouble() <= 0.0 -> editText.error = "Nieprawidłowa wartość"
        }
    }
}

class MassTextWatcher(private val editText: EditText) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        val input = s.toString()
        when {
            input.isBlank() -> editText.error = "To pole nie może być puste"
            input.toDouble() <= 0.0 -> editText.error = "Nieprawidłowa wartość"
            input.toDouble() > 250 -> editText.error = "Nieprawidłowa wartość"
        }
    }
}