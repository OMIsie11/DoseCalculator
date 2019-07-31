package io.github.omisie11.dosecalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.omisie11.dosecalculator.model.CalculationsResult
import io.github.omisie11.dosecalculator.model.Medicine

class MainViewModel : ViewModel() {

    private val calculationsResult = MutableLiveData<CalculationsResult>()

    init {
        calculationsResult.value = CalculationsResult(
            "",
            0,
            0.0,
            0.0,
            0.0,
            0.0,
            isAdultMaxDoseInfoNeeded = false,
            isIbuprofenAlertNeeded = false,
            medicineMinMl = 0.0,
            medicineDailyMinMl = 0.0,
            medicineMaxMl = 0.0,
            medicineDailyMaxMl = 0.0,
            isDailyMinMlEqualDailyMaxMl = false
        )
    }

    fun getResult(): LiveData<CalculationsResult> = calculationsResult

    fun performCalculations(
        medicine: Medicine, substanceConcentration: Double,
        amountOfMedicine: Double, bodyWeight: Double
    ) {
        calculationsResult.value = calculateDose(
            medicine, substanceConcentration,
            amountOfMedicine, bodyWeight
        )
    }
}