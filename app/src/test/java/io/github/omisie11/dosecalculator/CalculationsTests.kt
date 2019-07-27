package io.github.omisie11.dosecalculator

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CalculationsTests {

    private val resultWarning =
        "Uwaga: poniższe wyliczenia mają jedynie charakter informacyjny i nie stanowią porady lekarskiej."

    @Test
    fun testIbuprofen46mg1ml16kg() {
        val result = runBlocking {
            calculateDose(
                Ibuprofen(),
                46.0,
                1.0,
                16.0,
                resultWarning
            )
        }
        val expected =
            "Uwaga: poniższe wyliczenia mają jedynie charakter informacyjny i nie stanowią porady lekarskiej." +
                    "\nJednorazowa dawka: 2.32-3.48 ml (odpowiednik 106.67-160.00 mg Ibuprofenu)" +
                    "\nMożna podać 3 takie dawki w ciągu doby." +
                    "\nDobowa dawka: 6.96-10.43 ml (odpowiednik 320.00-480.00 mg Ibuprofenu)"
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testIbuprofen89mg1ml24kg() {
        val result = runBlocking {
            calculateDose(
                Ibuprofen(),
                89.0,
                1.0,
                24.0,
                resultWarning
            )
        }
        val expected =
            "Uwaga: poniższe wyliczenia mają jedynie charakter informacyjny i nie stanowią porady lekarskiej." +
                    "\nJednorazowa dawka: 1.80-2.70 ml (odpowiednik 160.00-240.00 mg Ibuprofenu)" +
                    "\nMożna podać 3 takie dawki w ciągu doby." +
                    "\nDobowa dawka: 5.39-8.09 ml (odpowiednik 480.00-720.00 mg Ibuprofenu)"
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testIbuprofen132mg3ml56kg() {
        val result = runBlocking {
            calculateDose(
                Ibuprofen(),
                132.0,
                3.0,
                56.0,
                resultWarning
            )
        }
        val expected =
            "Uwaga: poniższe wyliczenia mają jedynie charakter informacyjny i nie stanowią porady lekarskiej." +
                    "\nJednorazowa dawka: 9.09 ml (odpowiednik 400.00 mg Ibuprofenu)" +
                    "\nMożna podać 3 takie dawki w ciągu doby." +
                    "\nDobowa dawka: 27.27 ml (odpowiednik 1200.00 mg Ibuprofenu)" +
                    "\nPamiętaj, że maksymalna dopuszczalna dawka dobowa Ibuprofenu dla osoby dorosłej wynosi " +
                    "1200.0 mg." +
                    "\nWiększe dawki leku można przyjmować jedynie pod nadzorem i na zlecenie lekarza."
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testParacetamol46mg1ml16kg() {
        val result = runBlocking {
            calculateDose(
                Paracetamol(),
                46.0,
                1.0,
                16.0,
                resultWarning
            )
        }
        val expected =
            "Uwaga: poniższe wyliczenia mają jedynie charakter informacyjny i nie stanowią porady lekarskiej." +
                    "\nJednorazowa dawka: 5.22 ml (odpowiednik 240.00 mg Paracetamolu)" +
                    "\nMożna podać 4 takie dawki w ciągu doby." +
                    "\nDobowa dawka: 20.87 ml (odpowiednik 960.00 mg Paracetamolu)"
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testParacetamol89mg1ml24kg() {
        val result = runBlocking {
            calculateDose(
                Paracetamol(),
                89.0,
                1.0,
                24.0,
                resultWarning
            )
        }
        val expected =
            "Uwaga: poniższe wyliczenia mają jedynie charakter informacyjny i nie stanowią porady lekarskiej." +
                    "\nJednorazowa dawka: 4.04 ml (odpowiednik 360.00 mg Paracetamolu)" +
                    "\nMożna podać 4 takie dawki w ciągu doby." +
                    "\nDobowa dawka: 16.18 ml (odpowiednik 1440.00 mg Paracetamolu)"
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun testParacetamol132mg3ml56kg() {
        val result = runBlocking {
            calculateDose(
                Paracetamol(),
                132.0,
                3.0,
                56.0,
                resultWarning
            )
        }
        val expected =
            "Uwaga: poniższe wyliczenia mają jedynie charakter informacyjny i nie stanowią porady lekarskiej." +
                    "\nJednorazowa dawka: 19.09 ml (odpowiednik 840.00 mg Paracetamolu)" +
                    "\nMożna podać 4 takie dawki w ciągu doby." +
                    "\nDobowa dawka: 76.36 ml (odpowiednik 3360.00 mg Paracetamolu)"
        assertThat(result).isEqualTo(expected)
    }
}