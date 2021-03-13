package org.digio.logreport.models

import org.digio.logreport.extensions.groupByFrequencyOfIPs
import org.digio.logreport.extensions.groupByFrequencyOfUrl
import org.digio.logreport.extensions.takeMostFrequent

data class LogFile(val logRecords: List<LogRecord>) {

    fun uniqueIpAddresses() = logRecords.filter { it.ipAddress.isNotBlank() }.distinctBy { it.ipAddress }.count()

    fun topThreeMostVisitedUrls(): List<String> = logRecords.groupByFrequencyOfUrl().takeMostFrequent(3)

    fun topThreeMostActiveIPs(): List<String> = logRecords.groupByFrequencyOfIPs().takeMostFrequent(3)
}
