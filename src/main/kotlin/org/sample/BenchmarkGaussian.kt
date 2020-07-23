package org.sample

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import org.openjdk.jmh.infra.Blackhole

@Fork(value = 3) @Warmup(iterations = 3)
@State(Scope.Thread)
class BenchmarkGaussian {
    @Param("0", "10", "100", "200", "400", "800", "1600", "3000")
    var varianceGaussian: Int = 0

    @Benchmark
    fun testMethodGaussian(blackhole: Blackhole) {
        val tree = SplayTree<Int>()
        repeat(Utils.OPS) {
            val key = genGaussianDist(1000, varianceGaussian)
            when(Utils.random.nextInt(2)) {
                0 -> tree.add(key)
                1 -> tree.find(key)
            }
            blackhole.consume(tree)
        }
    }

    private fun genGaussianDist(mean: Int, variance: Int): Int {
        return Math.round(mean + Utils.random.nextGaussian() * variance).toInt()
    }
}