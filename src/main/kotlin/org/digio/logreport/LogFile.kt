package org.digio.logreport

data class LogFile(val logRecords: List<LogRecord>) {
    fun uniqueIpAddresses() = logRecords.filter { it.ipAddress.isNotBlank() }.distinctBy { it.ipAddress }.count()
}