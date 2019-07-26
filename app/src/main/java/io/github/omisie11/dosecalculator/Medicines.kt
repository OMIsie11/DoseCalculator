package io.github.omisie11.dosecalculator

open class Medicine {
    open val name = "Medicine"
    open val singleMin: Double = 0.0
    open val singleMax: Double = 0.0
    open val dailyMin: Double = 0.0
    open val dailyMax: Double = 0.0
    open val count: Int = 0
    open val max: Int = 0
}

class Ibuprofen : Medicine() {
    override val name = "Ibuprofen"
    override val singleMin: Double = 20.0 / 3
    override val singleMax: Double = 30.0 / 3
    override val dailyMin: Double = 20.0
    override val dailyMax: Double = 30.0
    override val count: Int = 3
    override val max: Int = 1200
}

class Paracetamol : Medicine() {
    override val name = "Paracetamol"
    override val singleMin: Double = 15.0
    override val singleMax: Double = 15.0
    override val dailyMin: Double = 60.0
    override val dailyMax: Double = 60.0
    override val count: Int = 4
    override val max: Int = 4000
}
