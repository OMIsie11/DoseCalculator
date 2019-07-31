package io.github.omisie11.dosecalculator


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.omisie11.dosecalculator.model.CalculationsResult
import kotlinx.android.synthetic.main.fragment_result_bottom_sheet.*


class ResultBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_result_bottom_sheet, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProviders.of(activity!!)[MainViewModel::class.java]
        viewModel.getResult().observe(viewLifecycleOwner, Observer<CalculationsResult> { result ->

            if (result.isDailyMinMlEqualDailyMaxMl) {
                text_single_dose.text = "${result.medicineMinMl} ml (odpowiednik ${result.medicineMinMg} mg " +
                        "${result.medicineName}u)"
                text_daily_dose.text = "${result.medicineDailyMinMl} ml (odpowiednik " +
                "${result.medicineDailyMinMg} mg ${result.medicineName}u)"
            } else {
                text_single_dose.text = "${result.medicineMinMl}-${result.medicineMaxMl} ml (odpowiednik " +
                "${result.medicineMinMg}-${result.medicineMaxMg} mg ${result.medicineName}u)"
                text_daily_dose.text = "${result.medicineDailyMinMl}-${result.medicineDailyMaxMl} ml " +
                        "(odpowiednik ${result.medicineDailyMinMg}-${result.medicineDailyMaxMg} mg " +
                        "${result.medicineName}u)"
            }

            text_number_of_doses.text = "Można podać ${result.medicineCount} takie dawki w ciągu doby."

            var output = ""
            if (result.isAdultMaxDoseInfoNeeded) output += "Pamiętaj, że maksymalna dopuszczalna dawka dobowa " +
                    "${result.medicineName}u dla osoby dorosłej wynosi ${result.medicineDailyMaxMg} mg."
            if (result.isIbuprofenAlertNeeded) output += "\nWiększe dawki leku można przyjmować jedynie pod " +
                    "nadzorem i na zlecenie lekarza."
            text_additional_info.text = output

            Log.d("star", "result.isAdultMaxDoseInfoNeeded: ${result.isAdultMaxDoseInfoNeeded}")
            Log.d("star", "result.isIbuprofenAlertNeeded: ${result.isIbuprofenAlertNeeded}")

        })
    }
}
//if (result.isBlank()) getString(R.string.results_of_calculations_will_be_shown_here)
//else text_result.text = result
/*
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
}*/