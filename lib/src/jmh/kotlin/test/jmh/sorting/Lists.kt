package test.jmh.sorting

import kotlin.math.absoluteValue

internal data class Lists<T>(
    val shuffled: List<T>,
    val sorted: List<T>,
    val sortedHalf: List<T>,
    val reversed: List<T>,
    val equivalents: List<T>
)

private const val SALT = "e43f98c5-9923-4cd3-b441-c1e812cf6604"

internal fun intLists(size: Int, salt: String = SALT): Lists<Int> {
    val bytes = salt.toByteArray()
    val hashCode = salt.hashCode()
    val shuffled = List(size) { index ->
        val number = hashCode * size + index + 13
        val byte = bytes[number.absoluteValue % bytes.size]
        byte * hashCode / size + 13 - index
    }
    val sorted = shuffled.sorted()
    return Lists(
        shuffled = shuffled,
        sorted = sorted,
        sortedHalf = shuffled.let { list ->
            list.subList(0, list.size / 2) + list.subList(list.size / 2 + 1, list.size - 1).sorted()
        },
        reversed = sorted.reversed(),
        equivalents = List(size) { 42 },
    )
}
