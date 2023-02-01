package test.jmh.sorting

internal interface AlgorithmComplexity {
    val explain: String
    fun expectedComparisons(size: Int): Long
}
