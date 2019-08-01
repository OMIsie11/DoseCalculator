package io.github.omisie11.dosecalculator


import android.os.Bundle
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
                text_single_dose.text = getString(
                    R.string.single_dose_value_template,
                    result.medicineMinMl.toString(),
                    result.medicineMinMg.toString(),
                    result.medicineName
                )
                text_daily_dose.text = getString(
                    R.string.daily_dose_value_template,
                    result.medicineDailyMinMl.toString(),
                    result.medicineDailyMinMg.toString(),
                    result.medicineName
                )
            } else {
                text_single_dose.text = getString(
                    R.string.single_dose_section_template,
                    result.medicineMinMl.toString(), result.medicineMaxMl.toString(),
                    result.medicineMinMg.toString(), result.medicineMaxMg.toString(),
                    result.medicineName
                )
                text_daily_dose.text = getString(
                    R.string.daily_dose_section_template,
                    result.medicineDailyMinMl.toString(), result.medicineDailyMaxMl.toString(),
                    result.medicineDailyMinMg.toString(), result.medicineDailyMaxMg.toString(),
                    result.medicineName
                )
            }
            text_number_of_doses.text = getString(R.string.output_number_of_doses, result.medicineCount.toString())

            var output = ""
            if (result.isAdultMaxDoseInfoNeeded) output += getString(
                R.string.calc_output_daily_dose_adult,
                result.medicineName,
                result.medicineDailyMaxMg.toString()
            )
            if (result.isIbuprofenAlertNeeded) output += getString(R.string.calc_output_ibuprofen)
            text_additional_info.text = output
        })

        button_close.setOnClickListener { dismiss() }
    }
}
