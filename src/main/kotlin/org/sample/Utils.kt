package org.sample

import org.apache.commons.math3.distribution.PoissonDistribution
import java.io.File
import java.util.*

class Utils {
    companion object {
        const val OPS = 10000
        val random = Random()

        fun genScenario(d: Distribution, context: Context): Array<Any> {
            val func: () -> Any = when (d) {
                Distribution.GAUSSIAN -> {{ genGaussianDist(context.mean!!, context.varianceGaussian!!) }}
                Distribution.GEOMETRIC -> {{ genGeomDist(context.successProbability!!) }}
                Distribution.POISSON -> {{
                    val poissonDistrib = PoissonDistribution(context.ratePoisson!!)
                    genPoissonDist(poissonDistrib)
                }}
                Distribution.CUSTOM -> {{
                    val words = readWords(context.wordsFilename!!)
                    genWords(words)
                }}
            }

            return gen(func)
        }

        private fun gen(distrib: () -> Any): Array<Any> {
            var scenario = arrayOf<Any>()
            repeat(OPS) {
                scenario += distrib()
            }

            return scenario
        }

        private fun genGaussianDist(mean: Int, variance: Int): Int {
            return Math.round(mean + random.nextGaussian() * variance).toInt()
        }

        private fun genGeomDist(p: Double): Int {
            return Math.ceil(Math.log(1 - Math.random()) / Math.log(1 - p)).toInt()
        }

        private fun genPoissonDist(poissonDistribution: PoissonDistribution): Int {
            return poissonDistribution.sample()
        }

        private fun readWords(filename: String): List<String> {
            return File(filename).readText(Charsets.UTF_8).split("\\s".toRegex());
        }

        private fun genWords(words: List<String>): String {
            val ind = random.nextInt(words.size)
            return words[ind]
        }
    }
}