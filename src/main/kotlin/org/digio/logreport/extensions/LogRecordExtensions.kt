package org.digio.logreport.extensions

import org.digio.logreport.LogRecord

fun List<LogRecord>.groupByFrequencyOfUrl() =
    this.filter { it.url.isNotBlank() }.groupingBy { it.url }.eachCount().toList()

fun List<LogRecord>.groupByFrequencyOfIPs() =
    this.filter { it.ipAddress.isNotBlank() }.groupingBy { it.ipAddress }.eachCount().toList()

fun List<Pair<String, Int>>.takeMostFrequent(top: Int): List<String> =
    this.sortedByDescending { it.second }.take(top).map { it.first }