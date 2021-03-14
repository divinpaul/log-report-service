package integrationtest

import org.assertj.core.api.Assertions.assertThat
import org.digio.logreport.LogReportService
import org.digio.logreport.io.ConsoleWriter
import org.digio.logreport.io.LogFileReader
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

internal class LogReportServiceTest {
    private val captor: ByteArrayOutputStream = ByteArrayOutputStream()
    private val standardOutput: PrintStream = System.out
    private lateinit var logReportService: LogReportService

    @BeforeEach
    internal fun setUp() {
        logReportService = LogReportService(LogFileReader(), ConsoleWriter())
        System.setOut(PrintStream(captor))
    }

    @AfterEach
    internal fun tearDown() {
        System.setOut(standardOutput)
    }

    @Test
    internal fun `generates report from input log file`() {
        logReportService.generate(
            File(LogReportServiceTest::class.java.getResource("/integrationtest/inputFile.log").file)
        )

        assertThat(captor.toString().trim()).isEqualTo(
            """
                Number of unique IP addresses: 11
                The top 3 most visited URLs: [/docs/manage-websites/, /intranet-analytics/, http://example.net/faq/]
                The top 3 most active IP addresses: [168.41.191.40, 177.71.128.21, 50.112.00.11]
        """.trimIndent()
        )
    }
}