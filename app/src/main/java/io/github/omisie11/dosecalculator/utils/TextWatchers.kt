package io.github.omisie11.dosecalculator.utils

import android.text.TextWatcher
import android.text.Editable
import android.widget.EditText
import io.github.omisie11.dosecalculator.R


class DoseTextWatcher(private val editText: EditText) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        if (s.toString().isNotEmpty()) {
            val input = s.toString()
            when {
                input.toDouble() <= 0.0 -> editText.error = editText.resources.getString(R.string.incorrect_input)
            }
        }
    }
}

class MassTextWatcher(private val editText: EditText) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        if (s.toString().isNotEmpty()) {
            val input = s.toString()
            when {
                input.toDouble() <= 0.0 -> editText.error = editText.resources.getString(R.string.incorrect_input)
                input.toDouble() > 250 -> editText.error = editText.resources.getString(R.string.incorrect_input)
            }
        }
    }
}