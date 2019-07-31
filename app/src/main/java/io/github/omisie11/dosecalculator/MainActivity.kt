package io.github.omisie11.dosecalculator

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.github.omisie11.dosecalculator.model.CalculationsResult
import io.github.omisie11.dosecalculator.model.Ibuprofen
import io.github.omisie11.dosecalculator.model.Paracetamol
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_about.view.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private val sharedPrefs: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(this) }
    private val resultBottomSheet by lazy { ResultBottomSheetFragment() }
    private lateinit var infoHelpBottomSheetDialog: BottomSheetDialog
    private lateinit var aboutSheetView: View
    private lateinit var aboutBottomSheet: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextAppearance(this, R.style.AppBarTextAppearance)

        // Set default selection on radio group
        radio_ibuprofen.isChecked = true

        prepareBottomSheetDialogs()

        edit_text_substance.addTextChangedListener(DoseTextWatcher(edit_text_substance))
        edit_text_medicine.addTextChangedListener(DoseTextWatcher(edit_text_medicine))
        edit_text_mass.addTextChangedListener(MassTextWatcher(edit_text_mass))

        val viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        viewModel.getResult().observe(this, Observer<CalculationsResult> { result ->
            //if (result.isBlank()) getString(R.string.results_of_calculations_will_be_shown_here)
            //else text_result.text = result
            if (result.medicineName.isBlank()) text_result.text =
                getString(R.string.results_of_calculations_will_be_shown_here)
            else {
                var output = ""
                if (result.isAdultMaxDoseInfoNeeded) output += "Pamiętaj, że maksymalna dopuszczalna dawka dobowa " +
                        "${result.medicineName}u dla osoby dorosłej wynosi ${result.medicineDailyMaxMg} mg."
                if (result.isIbuprofenAlertNeeded) output += "\nWiększe dawki leku można przyjmować jedynie pod " +
                        "nadzorem i na zlecenie lekarza."
                output += if (result.isDailyMinMlEqualDailyMaxMl) {
                    "\nJednorazowa dawka: ${result.medicineMinMl} ml (odpowiednik ${result.medicineMinMg} mg" +
                            " ${result.medicineName}u)" +
                            "\nMożna podać ${result.medicineCount} takie dawki w ciągu doby." +
                            "\nDobowa dawka: ${result.medicineDailyMinMl} ml (odpowiednik " +
                            "${result.medicineDailyMinMg} mg ${result.medicineName}u)"
                } else {
                    "\nJednorazowa dawka: ${result.medicineMinMl}-${result.medicineMaxMl} ml (odpowiednik " +
                            "${result.medicineMinMg}-${result.medicineMaxMg} mg ${result.medicineName}u)" +
                            "\nMożna podać ${result.medicineCount} takie dawki w ciągu doby." +
                            "\nDobowa dawka: ${result.medicineDailyMinMl}-${result.medicineDailyMaxMl} ml " +
                            "(odpowiednik ${result.medicineDailyMinMg}-${result.medicineDailyMaxMg} mg " +
                            "${result.medicineName}u)"
                }
                text_result.text = output
            }
        })

        button_calculate.setOnClickListener {
            handleEditTexFocusOnButtonClick(edit_text_substance, edit_text_medicine, edit_text_mass)
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
                        && edit_text_medicine.validateNumericInput() -> {
                    viewModel.performCalculations(
                        lek,
                        edit_text_substance.text.toString().toDouble(),
                        edit_text_medicine.text.toString().toDouble(),
                        edit_text_mass.text.toString().toDouble()
                    )
                    resultBottomSheet.show(supportFragmentManager, "result_bottom_sheet")
                }
            }
        }

        image_substance_help.setOnClickListener { infoHelpBottomSheetDialog.show() }
        aboutSheetView.text_app_based_on.setOnClickListener { openWebUrl("https://mamaistetoskop.pl/test") }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_bar_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_about -> {
            aboutBottomSheet.show()
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

    private fun prepareBottomSheetDialogs() {
        // Bottom sheet displayed after click on question mark image
        infoHelpBottomSheetDialog = BottomSheetDialog(this)
        val infoSheetView = layoutInflater.inflate(R.layout.bottom_sheet_dialog_info, null)
        infoHelpBottomSheetDialog.setContentView(infoSheetView)
        // Bottom sheet with about app info
        aboutBottomSheet = BottomSheetDialog(this)
        aboutSheetView = layoutInflater.inflate(R.layout.bottom_sheet_about, null)
        aboutBottomSheet.setContentView(aboutSheetView)
    }

    private fun handleEditTexFocusOnButtonClick(editText1: EditText, editText2: EditText, editText3: EditText) {
        when {
            editText1.hasFocus() -> editText1.clearFocus()
            editText2.hasFocus() -> editText2.clearFocus()
            editText3.hasFocus() -> editText3.clearFocus()
        }
    }

    private fun openWebUrl(urlAddress: String) {
        if (urlAddress.isNotEmpty()) startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress)))
    }

    companion object {
        const val PREFS_KEY_DARK_MODE = "prefs_key_dark_mode"
    }
}
