package io.github.omisie11.dosecalculator

import com.google.common.truth.Truth.assertThat
import io.github.omisie11.dosecalculator.model.CalculationsResult
import io.github.omisie11.dosecalculator.model.Ibuprofen
import io.github.omisie11.dosecalculator.model.Paracetamol
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CalculationsTests {

    @Test
    fun testIbuprofen46mg1ml16kg() {
        val result = runBlocking {
            calculateDose(Ibuprofen(), 46.0, 1.0, 16.0)
        }
        val expected = CalculationsResult(
            "Ibuprofen", 3,
            106.67, 320.00, 160.00, 480.00,
            isAdultMaxDoseInfoNeeded = false, isIbuprofenAlertNeeded = false,
            medicineMinMl = 2.32, medicineDailyMinMl = 6.96, medicineMaxMl = 3.48, medicineDailyMaxMl = 10.43,
            isDailyMinMlEqualDailyMaxMl = false
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testIbuprofen89mg1ml24kg() {
        val result = runBlocking {
            calculateDose(Ibuprofen(), 89.0, 1.0, 24.0)
        }
        val expected = CalculationsResult(
            "Ibuprofen", 3,
            160.00, 480.00, 240.00, 720.00,
            isAdultMaxDoseInfoNeeded = false, isIbuprofenAlertNeeded = false,
            medicineMinMl = 1.80, medicineDailyMinMl = 5.39, medicineMaxMl = 2.70, medicineDailyMaxMl = 8.09,
            isDailyMinMlEqualDailyMaxMl = false
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testIbuprofen132mg3ml56kg() {
        val result = runBlocking {
            calculateDose(
                Ibuprofen(),
                132.0,
                3.0,
                56.0
            )
        }
        val expected = CalculationsResult(
            "Ibuprofen", 3,
            400.00, 1200.00, 400.00, 1200.00,
            isAdultMaxDoseInfoNeeded = true, isIbuprofenAlertNeeded = true,
            medicineMinMl = 9.09, medicineDailyMinMl = 27.27, medicineMaxMl = 9.09, medicineDailyMaxMl = 27.27,
            isDailyMinMlEqualDailyMaxMl = true
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testParacetamol46mg1ml16kg() {
        val result = runBlocking {
            calculateDose(Paracetamol(), 46.0, 1.0, 16.0)
        }
        val expected = CalculationsResult(
            "Paracetamol", 4,
            240.00, 960.00, 240.00, 960.00,
            isAdultMaxDoseInfoNeeded = false, isIbuprofenAlertNeeded = false,
            medicineMinMl = 5.22, medicineDailyMinMl = 20.87, medicineMaxMl = 5.22, medicineDailyMaxMl = 20.87,
            isDailyMinMlEqualDailyMaxMl = true
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testParacetamol89mg1ml24kg() {
        val result = runBlocking {
            calculateDose(Paracetamol(), 89.0, 1.0, 24.0)
        }
        val expected = CalculationsResult(
            "Paracetamol", 4,
            360.00, 1440.00, 360.00, 1440.00,
            isAdultMaxDoseInfoNeeded = false, isIbuprofenAlertNeeded = false,
            medicineMinMl = 4.04, medicineDailyMinMl = 16.18, medicineMaxMl = 4.04, medicineDailyMaxMl = 16.18,
            isDailyMinMlEqualDailyMaxMl = true
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testParacetamol132mg3ml56kg() {
        val result = runBlocking {
            calculateDose(Paracetamol(), 132.0, 3.0, 56.0)
        }
        val expected = CalculationsResult(
            "Paracetamol", 4,
            840.00, 3360.00, 840.00, 3360.00,
            isAdultMaxDoseInfoNeeded = false, isIbuprofenAlertNeeded = false,
            medicineMinMl = 19.09, medicineDailyMinMl = 76.36, medicineMaxMl = 19.09, medicineDailyMaxMl = 76.36,
            isDailyMinMlEqualDailyMaxMl = true
        )
        assertThat(result).isEqualTo(expected)
    }
}