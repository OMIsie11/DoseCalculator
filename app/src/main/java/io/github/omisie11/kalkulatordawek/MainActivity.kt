package io.github.omisie11.kalkulatordawek

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radio_ibuprofen.isChecked = true

        edit_text_substancja.addTextChangedListener(DoseTextWatcher(edit_text_substancja))
        edit_text_syrop.addTextChangedListener(DoseTextWatcher(edit_text_syrop))
        edit_text_masa.addTextChangedListener(MassTextWatcher(edit_text_masa))

        button_licz.setOnClickListener {
            val lek = if (findViewById<RadioButton>(radio_group_lek.checkedRadioButtonId) == radio_ibuprofen)
                Ibuprofen() else Paracetamol()

            if (edit_text_substancja.validateNumericInput() && edit_text_syrop.validateNumericInput()
                && edit_text_syrop.validateNumericInput()
            ) {
                text_wynik.text = wyliczDawke(
                    lek,
                    edit_text_substancja.text.toString().toDouble(),
                    edit_text_syrop.text.toString().toDouble(),
                    edit_text_masa.text.toString().toDouble()
                )
            } else Toast.makeText(this, "Niepoprawne dane", Toast.LENGTH_LONG).show()
        }
    }

    fun wyliczDawke(lek: Lek, stezenieSubstancji: Double, iloscSyropu: Double, masaCiala: Double): String {
        if (stezenieSubstancji == 0.0 || iloscSyropu == 0.0 || masaCiala == 0.0) return "Błędne wartości"

        var informacja: String =
            "Uwaga: poniższe wyliczenia mają jedynie charakter informacyjny i nie stanowią porady lekarskiej."

        val stezenie = stezenieSubstancji / iloscSyropu

        var lekMinMg: Double = lek.singleMin * masaCiala
        var lekDailyMinMg: Double = lek.dailyMin * masaCiala
        var lekMaxMg: Double = lek.singleMax * masaCiala
        var lekDailyMaxMg: Double = lek.dailyMax * masaCiala

        var uwaga = ""

        if (lekDailyMaxMg > lek.max) {
            lekDailyMinMg = lek.max.toDouble()
            lekDailyMaxMg = lek.max.toDouble()
            lekMinMg = (lek.max / lek.count).toDouble()
            lekMaxMg = (lek.max / lek.count).toDouble()

            uwaga = "Pamiętaj, że maksymalna dopuszczalna dawka dobowa ${lek.name}u dla osoby dorosłej " +
                    "wynosi $lekDailyMaxMg mg."

            if (lek is Ibuprofen) uwaga += "\n Większe dawki leku można przyjmować jedynie pod nadzorem i na " +
                    "zlecenie lekarza."
        }

        informacja += uwaga

        //lekMinMg = Math.round(lekMinMg * 100) / 100
        //lekDailyMinMg = Math.round(lek_daily_min_ml * 100) / 100
        //lekMaxMg = Math.round(lek_max_ml * 100) / 100
        //lekDailyMaxMg = Math.round(lek_daily_max_ml * 100) / 100

        var lekMinMl: Double = lekMinMg / stezenie
        var lekDailyMinMl: Double = lekDailyMinMg / stezenie
        var lekMaxMl: Double = lekMaxMg / stezenie
        var lekDailyMaxMl: Double = lekDailyMaxMg / stezenie

        //lek_min_ml = Math.round(lek_min_ml * 100) / 100
        //lek_daily_min_ml = Math.round(lek_daily_min_ml * 100) / 100
        //lek_max_ml = Math.round(lek_max_ml * 100) / 100
        //lek_daily_max_ml = Math.round(lek_daily_max_ml * 100) / 100

        var tekst = if (lekDailyMinMl != lekDailyMaxMl) {
            "Jednorazowa dawka: $lekMinMl-$lekMaxMl ml (odpowiednik $lekMinMg-$lekMaxMg mg " +
                    "${lek.name}u) " +
                    "\nMożna podać ${lek.count} takie dawki w ciągu doby. " +
                    "\nDobowa dawka: $lekDailyMinMl-$lekDailyMaxMl ml (odpowiednik $lekDailyMinMg-$lekDailyMaxMg " +
                    "mg ${lek.name}u)"
        } else {
            "Jednorazowa dawka: $lekMinMl ml (odpowiednik $lekMinMg mg ${lek.name}u) " +
                    "\nMożna podać ${lek.count} takie dawki w ciągu doby. " +
                    "\nDobowa dawka: $lekDailyMinMl ml (odpowiednik $lekDailyMinMg mg" +
                    " ${lek.name}u)"
        }

        informacja += tekst

        return informacja
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
