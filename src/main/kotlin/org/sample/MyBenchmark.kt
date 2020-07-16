package org.sample

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import org.openjdk.jmh.infra.Blackhole
import java.util.*

@Fork(value = 3) @Warmup(iterations = 3)
@State(Scope.Thread)
open class MyBenchmark {
    lateinit var random: Random

    @Param("0", "10", "100", "200", "400", "800", "1600", "3000")
    var variance: Int = 0

    @Setup
    fun setup() {
        random = Random()
    }

    @Benchmark
    fun testMethod(blackhole: Blackhole) {
        val tree: SplayTree? = SplayTree(Node(4))
        val ops = 10000
        repeat(ops) {
            val key = genDist(1000, variance)
            when(random.nextInt(2)) {
                0 -> tree?.add(key)
                1 -> tree?.find(key)
            }
            blackhole.consume(tree)
        }
    }

    private fun genDist(mean: Int, variance: Int): Int {
        return Math.round(mean + random.nextGaussian() * variance).toInt()
    }
}
