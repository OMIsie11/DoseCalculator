package io.github.omisie11.kalkulatordawek

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_ibuprofen ->
                    if (checked) {
                        Toast.makeText(this, "Ibuprofen", Toast.LENGTH_SHORT).show()
                    }
                R.id.radio_paracetamol ->
                    if (checked) {
                        Toast.makeText(this, "Paracetamol", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
