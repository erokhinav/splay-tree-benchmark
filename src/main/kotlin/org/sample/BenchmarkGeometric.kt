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
class BenchmarkGeometric {
    @Param("0.05", "0.1", "0.2", "0.4", "0.8")
    var successProbability: Double = 0.0

    @Benchmark
    fun testMethodGeometric(blackhole: Blackhole) {
        val tree = SplayTree<Int>()
        repeat(Utils.OPS) {
            val key = genGeomDist(successProbability)
            when(Utils.random.nextInt(2)) {
                0 -> tree.add(key)
                1 -> tree.find(key)
            }
            blackhole.consume(tree)
        }
    }

    private fun genGeomDist(p: Double): Int {
        return Math.ceil(Math.log(1 - Math.random()) / Math.log(1 - p)).toInt()
    }
}