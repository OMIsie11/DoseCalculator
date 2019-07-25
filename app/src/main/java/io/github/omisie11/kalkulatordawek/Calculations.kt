package io.github.omisie11.kalkulatordawek

import java.math.BigDecimal
import java.math.RoundingMode

fun calculateDose(lek: Lek, stezenieSubstancji: Double, iloscSyropu: Double, masaCiala: Double): String {
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

        uwaga = "\nPamiętaj, że maksymalna dopuszczalna dawka dobowa ${lek.name}u dla osoby dorosłej " +
                "wynosi $lekDailyMaxMg mg."

        if (lek is Ibuprofen) uwaga += "\nWiększe dawki leku można przyjmować jedynie pod nadzorem i na " +
                "zlecenie lekarza."
    }

    val lekMinMgRounded = BigDecimal(lekMinMg).setScale(2, RoundingMode.HALF_EVEN)
    val lekDailyMinMgRounded = BigDecimal(lekDailyMinMg).setScale(2, RoundingMode.HALF_EVEN)
    val lekMaxMgRounded = BigDecimal(lekMaxMg).setScale(2, RoundingMode.HALF_EVEN)
    val lekDailyMaxMgRounded = BigDecimal(lekDailyMaxMg).setScale(2, RoundingMode.HALF_EVEN)

    val lekMinMl: Double = lekMinMg / stezenie
    val lekDailyMinMl: Double = lekDailyMinMg / stezenie
    val lekMaxMl: Double = lekMaxMg / stezenie
    val lekDailyMaxMl: Double = lekDailyMaxMg / stezenie

    val lekMinMlRounded = BigDecimal(lekMinMl).setScale(2, RoundingMode.HALF_EVEN)
    val lekDailyMinMlRounded = BigDecimal(lekDailyMinMl).setScale(2, RoundingMode.HALF_EVEN)
    val lekMaxMlRounded = BigDecimal(lekMaxMl).setScale(2, RoundingMode.HALF_EVEN)
    val lekDailyMaxMlRounded = BigDecimal(lekDailyMaxMl).setScale(2, RoundingMode.HALF_EVEN)

    val tekst = if (lekDailyMinMl != lekDailyMaxMl) {
        "\nJednorazowa dawka: $lekMinMlRounded-$lekMaxMlRounded ml (odpowiednik $lekMinMgRounded-$lekMaxMgRounded " +
                "mg ${lek.name}u)" +
                "\nMożna podać ${lek.count} takie dawki w ciągu doby." +
                "\nDobowa dawka: $lekDailyMinMlRounded-$lekDailyMaxMlRounded ml (odpowiednik " +
                "$lekDailyMinMgRounded-$lekDailyMaxMgRounded mg ${lek.name}u)"
    } else {
        "\nJednorazowa dawka: $lekMinMlRounded ml (odpowiednik $lekMinMgRounded mg ${lek.name}u)" +
                "\nMożna podać ${lek.count} takie dawki w ciągu doby." +
                "\nDobowa dawka: $lekDailyMinMlRounded ml (odpowiednik $lekDailyMinMgRounded mg" +
                " ${lek.name}u)"
    }

    informacja += tekst
    informacja += uwaga

    return informacja
}