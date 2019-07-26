package io.github.omisie11.kalkulatordawek

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private val sharedPrefs: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        AppCompatDelegate.setDefaultNightMode(
            translateValueToDayNightMode(
                sharedPrefs.getBoolean(PREFS_KEY_DARK_MODE, false)
            )
        )

        radio_ibuprofen.isChecked = true

        edit_text_substance.addTextChangedListener(DoseTextWatcher(edit_text_substance))
        edit_text_medicine.addTextChangedListener(DoseTextWatcher(edit_text_medicine))
        edit_text_mass.addTextChangedListener(MassTextWatcher(edit_text_mass))

        val viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        viewModel.getResult().observe(this, Observer<String> { result ->
            if (result.isBlank()) getString(R.string.results_of_calculations_will_be_shown_here)
            else text_result.text = result
        })

        button_calculate.setOnClickListener {
            val lek = if (findViewById<RadioButton>(radio_group_medicine.checkedRadioButtonId) == radio_ibuprofen)
                Ibuprofen() else Paracetamol()

            when {
                edit_text_substance.text.toString().isBlank() -> edit_text_substance.error =
                    getString(R.string.this_field_cannot_be_blank)
                edit_text_medicine.text.toString().isBlank() -> edit_text_medicine.error =
                    getString(R.string.this_field_cannot_be_blank)
                edit_text_mass.text.toString().isBlank() -> edit_text_mass.error =
                    getString(R.string.this_field_cannot_be_blank)
                !edit_text_substance.validateNumericInput() -> edit_text_substance.error =
                    getString(R.string.incorrect_input)
                !edit_text_medicine.validateNumericInput() -> edit_text_medicine.error =
                    getString(R.string.incorrect_input)
                !edit_text_mass.validateNumericInput() -> edit_text_mass.error =
                    getString(R.string.incorrect_input)
                edit_text_substance.validateNumericInput() && edit_text_medicine.validateNumericInput()
                        && edit_text_medicine.validateNumericInput() -> viewModel.performCalculations(
                    lek, edit_text_substance.text.toString().toDouble(),
                    edit_text_medicine.text.toString().toDouble(), edit_text_mass.text.toString().toDouble()
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_bar_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
            true
        }
        R.id.action_dark_mode -> {
            when (sharedPrefs.getBoolean(PREFS_KEY_DARK_MODE, false)) {
                true -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    with(sharedPrefs.edit()) {
                        putBoolean(PREFS_KEY_DARK_MODE, false)
                        apply()
                    }
                }
                false -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    with(sharedPrefs.edit()) {
                        putBoolean(PREFS_KEY_DARK_MODE, true)
                        apply()
                    }
                }
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun translateValueToDayNightMode(value: Boolean): Int = when (value) {
        true -> AppCompatDelegate.MODE_NIGHT_YES
        false -> AppCompatDelegate.MODE_NIGHT_NO
    }

    companion object {
        const val PREFS_KEY_DARK_MODE = "prefs_key_dark_mode"
    }
}
