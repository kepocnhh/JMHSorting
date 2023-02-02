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
import kotlin.math.absoluteValue

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1, warmups = 0)
@Measurement(iterations = 1, time = 1)
@Warmup(iterations = 0)
@State(Scope.Benchmark)
internal open class SortingAlgorithmBenchmark {
    companion object {
        private const val SALT = "e43f98c5-9923-4cd3-b441-c1e812cf6604"
    }

//    @Param(value = ["4096"])
    @Param(value = ["16384"])
//    @Param(value = ["4096", "16384", "65536"])
    var size: Int = 0

    private var listShuffled = listOf<Int>()
    private var listSorted = listOf<Int>()
    private var listSortedHalf = listOf<Int>()
    private var listReversed = listOf<Int>()
    private val listEquivalents = List(size) { 42 }

    @Setup(Level.Trial)
    fun eachTrial() {
        val bytes = SALT.toByteArray()
        val hashCode = SALT.hashCode()
        listShuffled = List(size) { index ->
            val number = hashCode * size + index + 13
            val byte = bytes[number.absoluteValue % bytes.size]
            byte * hashCode / size + 13 - index
        }
        listSorted = listShuffled.sorted()
        listSortedHalf = listShuffled.let { list ->
            list.subList(0, list.size / 2) + list.subList(list.size / 2 + 1, list.size - 1).sorted()
        }
        listReversed = listSorted.reversed()
    }

    @Benchmark
    fun sortShuffledByBubble() {
        BubbleSort.sort(listShuffled)
    }

    @Benchmark
    fun sortSortedByBubble() {
        BubbleSort.sort(listSorted)
    }

    @Benchmark
    fun sortSortedHalfByBubble() {
        BubbleSort.sort(listSortedHalf)
    }

    @Benchmark
    fun sortReversedByBubble() {
        BubbleSort.sort(listReversed)
    }

    @Benchmark
    fun sortEquivalentsByBubble() {
        BubbleSort.sort(listEquivalents)
    }

    @Benchmark
    fun sortShuffledByBubbleEx() {
        BubbleExSort.sort(listShuffled)
    }

    @Benchmark
    fun sortSortedByBubbleEx() {
        BubbleExSort.sort(listSorted)
    }

    @Benchmark
    fun sortSortedHalfByBubbleEx() {
        BubbleExSort.sort(listSortedHalf)
    }

    @Benchmark
    fun sortReversedByBubbleEx() {
        BubbleExSort.sort(listReversed)
    }

    @Benchmark
    fun sortEquivalentsByBubbleEx() {
        BubbleExSort.sort(listEquivalents)
    }
}
