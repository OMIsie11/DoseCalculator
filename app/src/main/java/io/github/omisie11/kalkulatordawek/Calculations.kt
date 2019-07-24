package io.github.omisie11.kalkulatordawek

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