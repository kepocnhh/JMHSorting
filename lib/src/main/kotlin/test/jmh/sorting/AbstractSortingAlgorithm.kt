package test.jmh.sorting

import java.time.Duration
import java.time.Instant

internal abstract class AbstractSortingAlgorithm(
    override val name: String,
    override val complexity: AlgorithmComplexity
) : SortingAlgorithm {
    protected abstract fun <T : Comparable<T>> sort(source: Array<T>): SortingResult<T>

    private fun <T : Comparable<T>> sort(source: Collection<T>): SortingResult<T> {
        if (source.size > 1) return sort(source.toTypedArray<Comparable<T>>() as Array<T>)
        val timeStart = Instant.now()
        return SortingResult(
            values = source.toList(),
            duration = Duration.between(timeStart, Instant.now()),
            comparisons = 0,
            permutations = 0
        )
    }

    override fun <T : Comparable<T>> sort(source: Iterable<T>): SortingResult<T> {
        return if (source is Collection) sort(source)
        else sort(source.toMutableList())
    }
}
