package com.alway.lequ_kotlin

import org.junit.Test

import org.junit.Assert.*
import kotlin.system.measureNanoTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun testListAndSequence() {
        val sequence = generateSequence(1) { it + 1 }.take(50000000)
        val list = sequence.toList()

        println("List Map Sum= "
                + measureNanoTime { list.map { it * 2 }.sum() })
        println("Sequence Map Sum "
                + measureNanoTime { sequence.map { it * 2 }.sum() })

        println("List Map Average "
                + measureNanoTime { list.map { it * 2 }.average() })
        println("Sequence Map Average "
                + measureNanoTime { sequence.map { it * 2 }.average() })
    }

    fun main(args: Array<String>) {
        testListAndSequence()
    }
}
