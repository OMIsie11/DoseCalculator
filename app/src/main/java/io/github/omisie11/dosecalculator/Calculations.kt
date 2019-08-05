package io.github.omisie11.dosecalculator

import io.github.omisie11.dosecalculator.model.CalculationsResult
import io.github.omisie11.dosecalculator.model.Ibuprofen
import io.github.omisie11.dosecalculator.model.Medicine
import io.github.omisie11.dosecalculator.utils.roundToTwoDecimalPoints


fun calculateDose(
    medicine: Medicine, substanceConcentration: Double,
    amountOfMedicine: Double, bodyWeight: Double
): CalculationsResult {

    val concentration = substanceConcentration / amountOfMedicine

    var medicineMinMg: Double = medicine.singleMin * bodyWeight
    var medicineDailyMinMg: Double = medicine.dailyMin * bodyWeight
    var medicineMaxMg: Double = medicine.singleMax * bodyWeight
    var medicineDailyMaxMg: Double = medicine.dailyMax * bodyWeight

    var isAdultMaxDoseInfoNeeded = false
    var isIbuprofenAlertNeeded = false

    if (medicineDailyMaxMg > medicine.max) {
        medicineDailyMinMg = medicine.max.toDouble()
        medicineDailyMaxMg = medicine.max.toDouble()
        medicineMinMg = (medicine.max / medicine.count).toDouble()
        medicineMaxMg = (medicine.max / medicine.count).toDouble()

        // There will be need to add additional information about daily dose for adults
        isAdultMaxDoseInfoNeeded = true

        // If medicine is Ibuprofen, there will be need to add additional information to output for user
        if (medicine is Ibuprofen) isIbuprofenAlertNeeded = true
    }

    val medicineMinMl: Double = medicineMinMg / concentration
    val medicineDailyMinMl: Double = medicineDailyMinMg / concentration
    val medicineMaxMl: Double = medicineMaxMg / concentration
    val medicineDailyMaxMl: Double = medicineDailyMaxMg / concentration

    // Variable determines which pattern will be used as output information for user
    val isDailyMinMlEqualDailyMaxMl = medicineDailyMinMl == medicineDailyMaxMl

    return CalculationsResult(
        medicine.name,
        medicine.count,
        medicineMinMg.roundToTwoDecimalPoints(),
        medicineDailyMinMg.roundToTwoDecimalPoints(),
        medicineMaxMg.roundToTwoDecimalPoints(),
        medicineDailyMaxMg.roundToTwoDecimalPoints(),
        isAdultMaxDoseInfoNeeded,
        isIbuprofenAlertNeeded,
        medicineMinMl.roundToTwoDecimalPoints(),
        medicineDailyMinMl.roundToTwoDecimalPoints(),
        medicineMaxMl.roundToTwoDecimalPoints(),
        medicineDailyMaxMl.roundToTwoDecimalPoints(),
        isDailyMinMlEqualDailyMaxMl
    )
}
