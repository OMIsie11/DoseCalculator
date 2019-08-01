package io.github.omisie11.dosecalculator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import io.github.omisie11.dosecalculator.model.Ibuprofen
import io.github.omisie11.dosecalculator.model.Paracetamol
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_about.view.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog_info.view.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private val sharedPrefs: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(this) }
    private val resultBottomSheet by lazy { ResultBottomSheetFragment() }
    private lateinit var helpSubstanceBottomSheetDialog: BottomSheetDialog
    private lateinit var helpWeightBottomSheetDialog: BottomSheetDialog
    private lateinit var aboutSheetView: View
    private lateinit var aboutBottomSheet: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bottom_app_bar)

        // Set default selection on chip group
        chip_group_medicines.check(R.id.chip_ibuprofen)
        chip_group_medicines.setOnCheckedChangeListener { group, checkedId ->
            // Set clickable chips, prevent uncheck checked Chip
            for (i in 0 until group.childCount) {
                val chip = group.getChildAt(i)
                chip.isClickable = chip.id != group.checkedChipId
            }
        }

        prepareBottomSheetDialogs()

        edit_text_substance.addTextChangedListener(DoseTextWatcher(edit_text_substance))
        edit_text_medicine.addTextChangedListener(DoseTextWatcher(edit_text_medicine))
        edit_text_mass.addTextChangedListener(MassTextWatcher(edit_text_mass))

        val viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]

        fab_calculate.setOnClickListener {
            handleEditTexFocusOnButtonClick(edit_text_substance, edit_text_medicine, edit_text_mass)
            val lek = if (findViewById<Chip>(chip_group_medicines.checkedChipId) == chip_ibuprofen)
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

        image_substance_help.setOnClickListener { helpSubstanceBottomSheetDialog.show() }
        image_weight_help.setOnClickListener { helpWeightBottomSheetDialog.show() }
        aboutSheetView.text_app_based_on.setOnClickListener { openWebUrl("https://mamaistetoskop.pl/test") }
    }

    override fun onResume() {
        super.onResume()
        text_welcome_greeting.text = getMessageForCurrentTime(this)
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
        // Bottom sheet displayed after click on question mark image near substance input layout
        helpSubstanceBottomSheetDialog = BottomSheetDialog(this)
        val substanceSheetView = layoutInflater.inflate(R.layout.bottom_sheet_dialog_info, null)
        helpSubstanceBottomSheetDialog.setContentView(substanceSheetView)
        // Bottom sheet displayed after click on question mark image near weight input layout
        helpWeightBottomSheetDialog = BottomSheetDialog(this)
        val weightSheetView = layoutInflater.inflate(R.layout.bottom_sheet_dialog_info, null)
        weightSheetView.text_info.text = getString(R.string.help_weight_info)
        helpWeightBottomSheetDialog.setContentView(weightSheetView)
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

    // Return appropriate welcome String dependent of current hour
    private fun getMessageForCurrentTime(context: Context): String =
        when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 1..11 -> context.getString(R.string.good_morning)
            in 12..17 -> context.getString(R.string.good_afternoon)
            in 18..24 -> context.getString(R.string.good_evening)
            else -> context.getString(R.string.good_day)
        }

    companion object {
        const val PREFS_KEY_DARK_MODE = "prefs_key_dark_mode"
    }
}
