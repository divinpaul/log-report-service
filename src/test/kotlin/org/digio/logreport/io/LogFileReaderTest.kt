package org.digio.logreport.io

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

internal class LogFileReaderTest {
    private val logFileReader: LogFileReader = LogFileReader()

    @Test
    internal fun `reads from log file and returns a list of ip to url association`() {
        val inputFile = File(LogFileReaderTest::class.java.getResource("/inputFile.log").file)
        assertThat(logFileReader.read(inputFile)).isEqualTo(
            listOf(
                Pair("177.71.128.21", "/intranet-analytics/"),
                Pair("168.41.191.40", "http://example.net/faq/")
            )
        )
    }

    @Test
    internal fun `returns empty list when the input file is empty`() {
        val inputFile = File(LogFileReaderTest::class.java.getResource("/emptyInputFile.log").file)
        assertThat(logFileReader.read(inputFile)).isEqualTo(emptyList<Pair<String, String>>())
    }

    @Test
    internal fun `returns empty list when there are no matches`() {
        val inputFile = File(LogFileReaderTest::class.java.getResource("/inputFileWithoutMatches.log").file)
        assertThat(logFileReader.read(inputFile)).isEqualTo(emptyList<Pair<String, String>>())
    }

    @Test
    internal fun `ignores log record entries without an ip address`() {
        val inputFile = File(LogFileReaderTest::class.java.getResource("/inputFileWithoutIPAddress.log").file)
        assertThat(logFileReader.read(inputFile)).isEqualTo(
            listOf(
                Pair("168.41.191.40", "http://example.net/faq/")
            )
        )
    }

    @Test
    internal fun `ignores log record entries with a malformed ip address`() {
        val inputFile = File(LogFileReaderTest::class.java.getResource("/inputFileWithMalformedIPAddress.log").file)
        assertThat(logFileReader.read(inputFile)).isEqualTo(
            listOf(
                Pair("168.41.191.40", "http://example.net/faq/")
            )
        )
    }

    @Test
    internal fun `ignores log record entries without a url`() {
        val inputFile = File(LogFileReaderTest::class.java.getResource("/inputFileWithoutURL.log").file)
        assertThat(logFileReader.read(inputFile)).isEqualTo(
            listOf(
                Pair("168.41.191.40", "http://example.net/faq/")
            )
        )
    }

    @Test
    internal fun `ignores newlines in the input log file`() {
        val inputFileWithNewLines = File(LogFileReaderTest::class.java.getResource("/inputFileWithNewLines.log").file)
        assertThat(logFileReader.read(inputFileWithNewLines)).isEqualTo(
            listOf(
                Pair("177.71.128.21", "/intranet-analytics/"),
                Pair("168.41.191.40", "http://example.net/faq/")
            )
        )
    }

    @Test
    internal fun `ignores whitespaces in the input log file`() {
        val inputFileWithNewLines =
            File(LogFileReaderTest::class.java.getResource("/inputFileWithWhiteSpaces.log").file)
        assertThat(logFileReader.read(inputFileWithNewLines)).isEqualTo(
            listOf(
                Pair("177.71.128.21", "/intranet-analytics/"),
                Pair("168.41.191.40", "http://example.net/faq/")
            )
        )
    }
}