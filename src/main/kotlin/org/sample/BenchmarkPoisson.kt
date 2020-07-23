package org.sample

import org.apache.commons.math3.distribution.PoissonDistribution
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import org.openjdk.jmh.infra.Blackhole

@Fork(value = 3) @Warmup(iterations = 3)
@State(Scope.Thread)
class BenchmarkPoisson {
    lateinit var poissonDistribution: PoissonDistribution

    @Param("0.1", "1", "10", "100", "1000", "10000", "100000")
    var ratePoisson: Double = 0.0

    @Setup
    fun setup() {
        poissonDistribution = PoissonDistribution(ratePoisson)
    }

    @Benchmark
    fun testMethodPoisson(blackhole: Blackhole) {
        val tree = SplayTree<Int>()
        repeat(Utils.OPS) {
            val key = genPoissonDist()
            when(Utils.random.nextInt(2)) {
                0 -> tree.add(key)
                1 -> tree.find(key)
            }
            blackhole.consume(tree)
        }
    }

    private fun genPoissonDist(): Int {
        return poissonDistribution.sample()
    }
}