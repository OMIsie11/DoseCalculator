package io.github.omisie11.dosecalculator

import java.math.BigDecimal
import java.math.RoundingMode

fun calculateDose(
    medicine: Medicine, substanceConcentration: Double, amountOfMedicine: Double, bodyWeight: Double,
    calculationsAlert: String
): String {
    if (substanceConcentration == 0.0 || amountOfMedicine == 0.0 || bodyWeight == 0.0) return "Błędne wartości"

    var result: String = calculationsAlert

    val concentration = substanceConcentration / amountOfMedicine

    var medicineMinMg: Double = medicine.singleMin * bodyWeight
    var medicineDailyMinMg: Double = medicine.dailyMin * bodyWeight
    var medicineMaxMg: Double = medicine.singleMax * bodyWeight
    var medicineDailyMaxMg: Double = medicine.dailyMax * bodyWeight

    var alertAdditional = ""

    if (medicineDailyMaxMg > medicine.max) {
        medicineDailyMinMg = medicine.max.toDouble()
        medicineDailyMaxMg = medicine.max.toDouble()
        medicineMinMg = (medicine.max / medicine.count).toDouble()
        medicineMaxMg = (medicine.max / medicine.count).toDouble()

        alertAdditional = "\nPamiętaj, że maksymalna dopuszczalna dawka dobowa ${medicine.name}u dla osoby dorosłej " +
                "wynosi $medicineDailyMaxMg mg."

        if (medicine is Ibuprofen) alertAdditional += "\nWiększe dawki leku można przyjmować jedynie pod nadzorem" +
                " i na zlecenie lekarza."
    }

    val medicineMinMgRounded = BigDecimal(medicineMinMg).setScale(2, RoundingMode.HALF_EVEN)
    val medicineDailyMinMgRounded = BigDecimal(medicineDailyMinMg).setScale(2, RoundingMode.HALF_EVEN)
    val medicineMaxMgRounded = BigDecimal(medicineMaxMg).setScale(2, RoundingMode.HALF_EVEN)
    val medicineDailyMaxMgRounded = BigDecimal(medicineDailyMaxMg).setScale(2, RoundingMode.HALF_EVEN)

    val medicineMinMl: Double = medicineMinMg / concentration
    val medicineDailyMinMl: Double = medicineDailyMinMg / concentration
    val medicineMaxMl: Double = medicineMaxMg / concentration
    val medicineDailyMaxMl: Double = medicineDailyMaxMg / concentration

    val medicineMinMlRounded = BigDecimal(medicineMinMl).setScale(2, RoundingMode.HALF_EVEN)
    val medicineDailyMinMlRounded = BigDecimal(medicineDailyMinMl).setScale(2, RoundingMode.HALF_EVEN)
    val medicineMaxMlRounded = BigDecimal(medicineMaxMl).setScale(2, RoundingMode.HALF_EVEN)
    val medicineDailyMaxMlRounded = BigDecimal(medicineDailyMaxMl).setScale(2, RoundingMode.HALF_EVEN)

    val doseString = if (medicineDailyMinMl != medicineDailyMaxMl) {
        "\nJednorazowa dawka: $medicineMinMlRounded-$medicineMaxMlRounded ml (odpowiednik $medicineMinMgRounded-" +
                "$medicineMaxMgRounded mg ${medicine.name}u)" +
                "\nMożna podać ${medicine.count} takie dawki w ciągu doby." +
                "\nDobowa dawka: $medicineDailyMinMlRounded-$medicineDailyMaxMlRounded ml (odpowiednik " +
                "$medicineDailyMinMgRounded-$medicineDailyMaxMgRounded mg ${medicine.name}u)"
    } else {
        "\nJednorazowa dawka: $medicineMinMlRounded ml (odpowiednik $medicineMinMgRounded mg ${medicine.name}u)" +
                "\nMożna podać ${medicine.count} takie dawki w ciągu doby." +
                "\nDobowa dawka: $medicineDailyMinMlRounded ml (odpowiednik $medicineDailyMinMgRounded mg" +
                " ${medicine.name}u)"
    }

    result += doseString
    result += alertAdditional

    return result
}

data class CalculationsResult(
    val medicineMinMg: Double,
    val medicineDailyMinMg: Double,
    val medicineMaxMg: Double,
    val medicineDailyMaxMg: Double,
    var isAdultMaxDoseInfoNeeded: Boolean,
    var isIbuprofenAlertNeeded: Boolean,
    val medicineMinMl: Double,
    val medicineDailyMinMl: Double,
    val medicineMaxMl: Double,
    val medicineDailyMaxMl: Double,
    var isDailyMinMlEqualDailyMaxMl: Boolean
)

// ToDo: Test this function
fun roundDoubleToTwoDecimalPoints(doubleToRound: Double): Double =
    BigDecimal(doubleToRound).setScale(2, RoundingMode.HALF_EVEN).toDouble()

// ToDo: Test new implementation
fun calculateDoseNew(
    medicine: Medicine, substanceConcentration: Double,
    amountOfMedicine: Double, bodyWeight: Double
): CalculationsResult {

    val concentration = substanceConcentration / amountOfMedicine

    var medicineMinMg: Double = medicine.singleMin * bodyWeight
    var medicineDailyMinMg: Double = medicine.dailyMin * bodyWeight
    var medicineMaxMg: Double = medicine.singleMax * bodyWeight
    var medicineDailyMaxMg: Double = medicine.dailyMax * bodyWeight

    var isAdultMaxDoseInfoNeeded: Boolean = false
    var isIbuprofenAlertNeeded: Boolean = false

    if (medicineDailyMaxMg > medicine.max) {
        medicineDailyMinMg = medicine.max.toDouble()
        medicineDailyMaxMg = medicine.max.toDouble()
        medicineMinMg = (medicine.max / medicine.count).toDouble()
        medicineMaxMg = (medicine.max / medicine.count).toDouble()

        isAdultMaxDoseInfoNeeded = true

        if (medicine is Ibuprofen) isIbuprofenAlertNeeded = true
    }

    val medicineMinMl: Double = medicineMinMg / concentration
    val medicineDailyMinMl: Double = medicineDailyMinMg / concentration
    val medicineMaxMl: Double = medicineMaxMg / concentration
    val medicineDailyMaxMl: Double = medicineDailyMaxMg / concentration

    val isDailyMinMlEqualDailyMaxMl = medicineDailyMinMl == medicineDailyMaxMl

    return CalculationsResult(
        medicineMinMg, medicineDailyMinMg, medicineMaxMg,
        medicineDailyMaxMg, isAdultMaxDoseInfoNeeded, isIbuprofenAlertNeeded,
        medicineMinMl, medicineDailyMinMl, medicineMaxMl, medicineDailyMaxMl,
        isDailyMinMlEqualDailyMaxMl
    )
}