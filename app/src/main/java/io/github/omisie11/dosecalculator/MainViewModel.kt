package io.github.omisie11.dosecalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val calculationsResult = MutableLiveData<String>()

    init {
        calculationsResult.value = ""
    }

    fun getResult(): LiveData<String> = calculationsResult

    fun performCalculations(
        medicine: Medicine,
        substanceConcentration: Double,
        amountOfMedicine: Double,
        bodyWeight: Double,
        calculationsAlert: String
    ) {
        uiScope.launch {
            val result = async(Dispatchers.IO) {
                calculateDose(
                    medicine,
                    substanceConcentration,
                    amountOfMedicine,
                    bodyWeight,
                    calculationsAlert
                )
            }
            calculationsResult.value = result.await()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}