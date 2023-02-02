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
@Fork(value = 1, warmups = 1)
@Measurement(iterations = 1, time = 1)
@Warmup(iterations = 0)
@State(Scope.Benchmark)
internal open class SortingAlgorithmBenchmark {
    companion object {
        private const val SALT = "e43f98c5-9923-4cd3-b441-c1e812cf6604"
    }

    @Param(value = ["4096", "16384", "65536"])
    var size: Int = 0

    private var list = listOf<Int>()

    @Setup(Level.Trial)
    fun eachTrial() {
        val bytes = SALT.toByteArray()
        val hashCode = SALT.hashCode()
        list = List(size) { index ->
            val number = hashCode * size + index + 13
            val byte = bytes[number.absoluteValue % bytes.size]
            byte * hashCode / size + 13 - index
        }
    }

    @Benchmark
    fun sortByBubble() {
        BubbleSort.sort(list)
    }

    @Benchmark
    fun sortByBubbleEx() {
        BubbleExSort.sort(list)
    }
}
