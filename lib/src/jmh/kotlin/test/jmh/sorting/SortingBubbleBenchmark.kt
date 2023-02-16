package test.jmh.sorting

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
internal open class SortingBubbleBenchmark {
    @Param(value = ["10000", "20000", "40000"])
    var size: Int = 0

    private lateinit var lists: Lists<Int>

    @Setup(Level.Trial)
    fun eachTrial() {
        lists = intLists(size)
    }

    @Benchmark
    fun sortShuffledByBubble() {
        BubbleSort.sort(lists.shuffled)
    }

    @Benchmark
    fun sortSortedByBubble() {
        BubbleSort.sort(lists.sorted)
    }

    @Benchmark
    fun sortSortedHalfByBubble() {
        BubbleSort.sort(lists.sortedHalf)
    }

    @Benchmark
    fun sortReversedByBubble() {
        BubbleSort.sort(lists.reversed)
    }

    @Benchmark
    fun sortEquivalentsByBubble() {
        BubbleSort.sort(lists.equivalents)
    }
}
