package test.jmh.sorting

internal interface SortingAlgorithm {
    val name: String
    val complexity: AlgorithmComplexity
    fun <T : Comparable<T>> sort(source: Iterable<T>): SortingResult<T>
}
