package io.github.omisie11.kalkulatordawek

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val calculationsResult = MutableLiveData<String>()

    init {
        calculationsResult.value = ""
    }

    fun getResult(): LiveData<String> = calculationsResult

    fun performCalculations(
        lek: Lek,
        stezenieSubstancji: Double,
        iloscSyropu: Double,
        masaCiala: Double,
        uwagaWyliczenia: String
    ) {
        calculationsResult.value = calculateDose(lek, stezenieSubstancji, iloscSyropu, masaCiala, uwagaWyliczenia)
    }
}