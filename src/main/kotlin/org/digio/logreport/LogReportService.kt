package org.digio.logreport

import org.digio.logreport.io.InputFileReader
import org.digio.logreport.io.OutputWriter
import org.digio.logreport.models.LogFile
import org.digio.logreport.models.LogRecord
import java.io.File

class LogReportService(private val inputFileReader: InputFileReader, private val outputWriter: OutputWriter) {
    fun generate(inputFile: File) {
        val logRecords = inputFileReader.read(inputFile).map { LogRecord(it.first, it.second) }
        val logFile = LogFile(logRecords)
        outputWriter.write(
            """
            Number of unique IP addresses: ${logFile.uniqueIpAddresses()}
            The top 3 most visited URLs: ${logFile.topThreeMostVisitedUrls()}
            The top 3 most active IP addresses: ${logFile.topThreeMostActiveIPs()}
        """.trimIndent()
        )
    }
}