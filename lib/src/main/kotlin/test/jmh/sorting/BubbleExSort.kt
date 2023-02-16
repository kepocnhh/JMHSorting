package test.jmh.sorting

import java.time.Duration
import java.time.Instant

internal object BubbleExSort : AbstractSortingAlgorithm(
    name = "Bubble sort (other version)",
    complexity = object : AlgorithmComplexity {
        override val explain = "1 + 2 + ... + (n - 1) = (n^2)/2 - n/2"

        override fun expectedComparisons(size: Int): Long {
            val n = size.toDouble()
            val result = (n * n) / 2 - n / 2
            return result.toLong()
        }

        override fun toString(): String {
            return "O(n^2)"
        }
    }
) {
    override fun <T : Comparable<T>> sort(source: Array<T>): SortingResult<T> {
        val timeStart = Instant.now()
        var comparisons = 0L
        var permutations = 0L
        var lastIndex = source.size - 1
        do {
            val size = lastIndex
            lastIndex = -1
            for (i in 0 until size) {
                comparisons++
                if (source[i] > source[i + 1]) {
                    lastIndex = i
                    permutations++
                    val tmp = source[i]
                    source[i] = source[i + 1]
                    source[i + 1] = tmp
                }
            }
        } while (lastIndex >= 0)
        return SortingResult(
            values = source.toList(),
            duration = Duration.between(timeStart, Instant.now()),
            comparisons = comparisons,
            permutations = permutations
        )
    }
}
