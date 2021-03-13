package org.digio.logreport.io

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class ConsoleWriterTest {
    private val consoleWriter: ConsoleWriter = ConsoleWriter()
    private val captor: ByteArrayOutputStream = ByteArrayOutputStream()
    private val standardOutput: PrintStream = System.out

    @BeforeEach
    internal fun setUp() {
        System.setOut(PrintStream(captor))
    }

    @AfterEach
    internal fun tearDown() {
        System.setOut(standardOutput)
    }

    @Test
    internal fun `prints output to console`() {
        consoleWriter.write("177.71.128.21, 177.71.128.22, 177.71.128.23")

        assertThat(captor.toString().trim()).isEqualTo("177.71.128.21, 177.71.128.22, 177.71.128.23")
    }
}