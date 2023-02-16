package test.jmh.sorting

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
internal open class SortingBubbleExBenchmark {
    @Param(value = ["8000", "16000", "32000"])
    var size: Int = 0

    private lateinit var lists: Lists<Int>

    @Setup(Level.Trial)
    fun eachTrial() {
        lists = intLists(size)
    }

    @Benchmark
    fun sortShuffledByBubbleEx(hole: Blackhole) {
        val result = BubbleExSort.sort(lists.shuffled)
        hole.consume(result)
    }

    @Benchmark
    fun sortSortedByBubbleEx(hole: Blackhole) {
        val result = BubbleExSort.sort(lists.sorted)
        hole.consume(result)
    }

    @Benchmark
    fun sortSortedHalfByBubbleEx(hole: Blackhole) {
        val result = BubbleExSort.sort(lists.sortedHalf)
        hole.consume(result)
    }

    @Benchmark
    fun sortReversedByBubbleEx(hole: Blackhole) {
        val result = BubbleExSort.sort(lists.reversed)
        hole.consume(result)
    }

    @Benchmark
    fun sortEquivalentsByBubbleEx(hole: Blackhole) {
        val result = BubbleExSort.sort(lists.equivalents)
        hole.consume(result)
    }
}
