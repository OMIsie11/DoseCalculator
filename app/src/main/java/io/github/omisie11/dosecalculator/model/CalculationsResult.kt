package io.github.omisie11.dosecalculator.model

/**
 * Result of calculations used to construct output info for user.
 * @property isAdultMaxDoseInfoNeeded informs if there is need to add information about daily dose for adult person.
 * @property isIbuprofenAlertNeeded informs if there is need to add information specific to this medicine.
 * @property isDailyMinMlEqualDailyMaxMl determines which output pattern will be used as general message about doses.
 */
data class CalculationsResult(
    val medicineName: String,
    val medicineCount: Int,
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