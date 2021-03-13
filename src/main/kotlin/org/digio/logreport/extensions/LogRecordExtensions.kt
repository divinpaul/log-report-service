package org.digio.logreport.extensions

import org.digio.logreport.LogRecord

fun List<LogRecord>.groupByFrequency() = this.filter { it.url.isNotBlank() }.groupingBy { it.url }.eachCount().toList()

fun List<Pair<String, Int>>.takeMostFrequent(top: Int) = this.take(top).map { it.first }