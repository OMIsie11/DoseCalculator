package io.github.omisie11.dosecalculator

import com.google.common.truth.Truth.assertThat
import io.github.omisie11.dosecalculator.utils.roundToTwoDecimalPoints
import org.junit.Test

class NumbersRoundingTests {

    @Test
    fun doubleRoundToTwoDecimalPointsTest() {
        val double1 = 12.6783
        val double2 = 6.1
        val double3 = 99.00212121
        val double4 = 123.895273590
        val double5 = 12.499
        val double6 = 0.001
        val double7 = 1.1
        val double8 = 10.999
        val double9 = 66.501

        assertThat(double1.roundToTwoDecimalPoints()).isEqualTo(12.68)
        assertThat(double2.roundToTwoDecimalPoints()).isEqualTo(6.1)
        assertThat(double3.roundToTwoDecimalPoints()).isEqualTo(99.00)
        assertThat(double4.roundToTwoDecimalPoints()).isEqualTo(123.90)
        assertThat(double5.roundToTwoDecimalPoints()).isEqualTo(12.50)
        assertThat(double6.roundToTwoDecimalPoints()).isEqualTo(0.00)
        assertThat(double7.roundToTwoDecimalPoints()).isEqualTo(1.1)
        assertThat(double8.roundToTwoDecimalPoints()).isEqualTo(11.00)
        assertThat(double9.roundToTwoDecimalPoints()).isEqualTo(66.50)
    }
}