package org.sample

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
class BenchmarkGaussianDeclarative {
    lateinit var scenario: Array<Any>

    @Param("0", "10", "100", "200", "400", "800", "1600", "3000")
    var varianceGaussian: Int = 0

    @Setup
    fun setup() {
        val context = Context()
        context.mean = 1000
        context.varianceGaussian = varianceGaussian
        scenario = Utils.genScenario(Distribution.GAUSSIAN, context)
    }

    @Benchmark
    fun testMethodGaussian(blackhole: Blackhole) {
        val tree = SplayTree<Int>()
        for (key in scenario) {
            if (key is Int) {
                tree.add(key)
            }
            blackhole.consume(tree)
        }
    }
}