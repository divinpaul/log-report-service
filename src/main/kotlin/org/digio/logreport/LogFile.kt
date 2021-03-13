package org.digio.logreport

import org.digio.logreport.extensions.groupByFrequency
import org.digio.logreport.extensions.takeMostFrequent

data class LogFile(val logRecords: List<LogRecord>) {

    fun uniqueIpAddresses() = logRecords.filter { it.ipAddress.isNotBlank() }.distinctBy { it.ipAddress }.count()

    fun topThreeMostVisitedUrls(): List<String> = logRecords.groupByFrequency().takeMostFrequent(3)
}