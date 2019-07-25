package io.github.omisie11.kalkulatordawek

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        radio_ibuprofen.isChecked = true

        edit_text_substancja.addTextChangedListener(DoseTextWatcher(edit_text_substancja))
        edit_text_syrop.addTextChangedListener(DoseTextWatcher(edit_text_syrop))
        edit_text_masa.addTextChangedListener(MassTextWatcher(edit_text_masa))

        val viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        viewModel.getResult().observe(this, Observer<String> { result ->
            text_wynik.text = result
        })

        button_licz.setOnClickListener {
            val lek = if (findViewById<RadioButton>(radio_group_lek.checkedRadioButtonId) == radio_ibuprofen)
                Ibuprofen() else Paracetamol()

            if (edit_text_substancja.validateNumericInput() && edit_text_syrop.validateNumericInput()
                && edit_text_syrop.validateNumericInput()
            ) {
                //text_wynik.text = calculateDose(lek, edit_text_substancja.text.toString().toDouble(),
                //    edit_text_syrop.text.toString().toDouble(), edit_text_masa.text.toString().toDouble())
                viewModel.performCalculations(
                    lek, edit_text_substancja.text.toString().toDouble(),
                    edit_text_syrop.text.toString().toDouble(), edit_text_masa.text.toString().toDouble()
                )
            } else Toast.makeText(this, "Niepoprawne dane", Toast.LENGTH_LONG).show()
        }
    }

    private fun translateValueToDayNightMode(value: Boolean): Int = when (value) {
        true -> AppCompatDelegate.MODE_NIGHT_YES
        false -> AppCompatDelegate.MODE_NIGHT_NO
    }
}
