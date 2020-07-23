package org.sample

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import org.openjdk.jmh.infra.Blackhole
import org.apache.commons.math3.distribution.PoissonDistribution
import java.io.File
import java.util.*

@Fork(value = 3) @Warmup(iterations = 3)
@State(Scope.Thread)
open class BenchmarkCustom {
    lateinit var random: Random

    var words = parseFile("/Users/victoria/Downloads/Crime_and_Punishment-Fyodor_Dostoyevsky.txt")
    var uniqueWords: Array<String> = HashSet(words).toTypedArray()

    @Benchmark
    fun testCustomDistribution(blackhole: Blackhole) {
        val tree = SplayTree<String>()
        for (word in words) {
            when (random.nextInt(2)) {
                0 -> tree.add(word)
                1 -> blackhole.consume(tree.find(word))

            }
        }
    }

    @Benchmark
    fun testCustomDistributionUnique(blackhole: Blackhole) {
      val n = words.size
        val tree = SplayTree<String>()
        repeat(n) {
            val ind = random.nextInt(uniqueWords.size)
            val key = uniqueWords[ind]
            when(random.nextInt(2)) {
                0 -> tree.add(key)
                1 -> blackhole.consume(tree.find(key))
            }
        }
    }

    private fun parseFile(filename: String): List<String> {
        return File(filename).readText(Charsets.UTF_8).split("\\s".toRegex());
    }
}
