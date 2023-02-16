package test.jmh.sorting

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
internal open class SortingBubbleBenchmark {
    @Param(value = ["8000", "16000", "32000"])
    var size: Int = 0

    private lateinit var lists: Lists<Int>

    @Setup(Level.Trial)
    fun eachTrial() {
        lists = intLists(size)
    }

    @Benchmark
    fun sortShuffledByBubble(hole: Blackhole) {
        val result = BubbleSort.sort(lists.shuffled)
        hole.consume(result)
    }

    @Benchmark
    fun sortSortedByBubble(hole: Blackhole) {
        val result = BubbleSort.sort(lists.sorted)
        hole.consume(result)
    }

    @Benchmark
    fun sortSortedHalfByBubble(hole: Blackhole) {
        val result = BubbleSort.sort(lists.sortedHalf)
        hole.consume(result)
    }

    @Benchmark
    fun sortReversedByBubble(hole: Blackhole) {
        val result = BubbleSort.sort(lists.reversed)
        hole.consume(result)
    }

    @Benchmark
    fun sortEquivalentsByBubble(hole: Blackhole) {
        val result = BubbleSort.sort(lists.equivalents)
        hole.consume(result)
    }
}
