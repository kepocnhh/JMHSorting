import java.io.File
import java.time.Duration
import java.time.temporal.ChronoUnit

private val NO_FILE = File("/")

enum class BenchmarkMode {
    Throughput,
    AverageTime
}

class JMHArgs(
    /**
     * Timeout for benchmark iteration. After reaching
     * this timeout, JMH will try to interrupt the running
     * tasks. Non-cooperating benchmarks may ignore this
     * timeout. (default: 10 min)
     */
    val timeout: Duration = Duration.of(10, ChronoUnit.MINUTES),

    /**
     * Number of measurement iterations to do.
     * Measurement iterations are counted towards the benchmark score.
     * (default: 1 for SingleShotTime, and 5 for all other modes)
     */
    val iterations: Int = 1,

    /**
     * Minimum time to spend at each measurement iteration.
     * Benchmarks may generally run longer than iteration duration.
     * (default: 10 s)
     */
    val time: Duration = Duration.of(10, ChronoUnit.SECONDS),

    /**
     * How many times to fork a single benchmark.
     * Use 0 to disable forking altogether.
     * Warning: disabling forking may have detrimental impact on benchmark and infrastructure reliability,
     * you might want to use different warmup mode instead.
     * (default: 5)
     */
    val forks: Int = 5,

    /**
     * Number of worker threads to run with.
     * 'max' means the maximum number of hardware threads available on the machine, figured out by JMH itself.
     * (default: 1)
     */
    val threads: Int = 1,

    /**
     * Benchmark mode.
     * Available modes are: [Throughput/thrpt, AverageTime/avgt, SampleTime/sample, SingleShotTime/ss, All/all].
     * (default: Throughput)
     */
    val mode: BenchmarkMode = BenchmarkMode.Throughput,

    /**
     * Override time unit in benchmark results.
     * Available time units are: [m, s, ms, us, ns].
     * (default: SECONDS)
     */
    val timeUnit: TimeUnit = TimeUnit.s,

    /**
     * Redirect human-readable output to a given file.
     */
    val output: File = NO_FILE,

    val warmup: Warmup = Warmup()
) {
    class Warmup(
        /**
         * Number of warmup iterations to do.
         * Warmup iterations are not counted towards the benchmark score.
         * (default: 0 for SingleShotTime, and 5 for all other modes)
         */
        val iterations: Int = 0,

        /**
         * How many warmup forks to make for a single benchmark.
         * All iterations within the warmup fork are not counted towards the benchmark score.
         * Use 0 to disable warmup forks.
         * (default: 0)
         */
        val forks: Int = 0,

        /**
         * Minimum time to spend at each warmup iteration. Benchmarks
         * may generally run longer than iteration duration.
         * (default: 10 s)
         */
        val time: Duration = Duration.of(10, ChronoUnit.SECONDS)
    )

    enum class TimeUnit {
        m,
        s,
        ms,
        us,
        ns,
    }

    fun toArgs(): List<String> {
        return mutableListOf(
            "-to=${timeout.toMillis()}ms",
            "-i=${iterations}",
            "-r=${time.toMillis()}ms",
            "-f=${forks}",
            "-wi=${warmup.iterations}",
            "-w=${warmup.time.toMillis()}ms",
            "-wf=${warmup.forks}",
            "-bm=${mode.name}"
        ).also {
            if (output != NO_FILE) {
                if (output.isDirectory) error("Outpur is directory!")
                it.add("-o=${output.absolutePath}")
            }
            if (threads < 1) error("Error threads!")
            if (threads == Int.MAX_VALUE) {
                it.add("-t=max")
            } else {
                it.add("-t=${threads}")
            }
        }
    }
}
