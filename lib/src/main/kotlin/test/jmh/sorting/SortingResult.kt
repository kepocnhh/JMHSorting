package test.jmh.sorting

import java.time.Duration

internal data class SortingResult<T : Comparable<T>>(
    val values: List<T>,
    val duration: Duration,
    val comparisons: Long,
    val permutations: Long
)
