package com.oocode

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

private const val TEST_CSV_FILEPATH = "/Users/ape03/Documents/IvanElectricCarChargerKotlin/src/main/kotlin/com/oocode/csvTestData.csv"

class Test {

    private fun getWeatherDataFromTestFile(): String {
        return File(TEST_CSV_FILEPATH).readText(Charsets.UTF_8)
    }

    @Test
    fun mainTest() {
        val newOut = ByteArrayOutputStream()
        System.setOut(PrintStream(newOut))
        main()
        System.out.flush()
        assertThat(newOut.toString(), equalTo("Best times to plug in:\n" +
                "Mon, 14 Apr 2025 12:00:00 GMT\n" +
                "Mon, 14 Apr 2025 12:30:00 GMT\n" +
                "Mon, 14 Apr 2025 13:00:00 GMT\n"))
    }

    @Test
    fun top3Test() {
        val testWeatherData = getWeatherDataFromTestFile()
        val expected = getTopThreeWind(testWeatherData)
        assertThat(expected, equalTo("Best times to plug in:\n" +
                "Mon, 14 Apr 2025 11:30:00 GMT\n" +
                "Mon, 14 Apr 2025 12:00:00 GMT\n" +
                "Mon, 14 Apr 2025 12:30:00 GMT"))
    }


    private var oldOut: PrintStream? = null

    @BeforeEach
    fun rememberRealSystemOut() {
        oldOut = System.out
    }

    @AfterEach
    fun restoreSystemOut() {
        System.setOut(oldOut)
    }
}