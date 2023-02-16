package test.jmh.sorting

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1, warmups = 0)
@Measurement(iterations = 1, time = 1)
@Warmup(iterations = 0)
@State(Scope.Benchmark)
internal open class SortingBubbleBenchmark {
//    @Param(value = ["4096"])
    @Param(value = ["16384"])
//    @Param(value = ["4096", "16384", "65536"])
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
