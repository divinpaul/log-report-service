package org.digio.logreport.io

import java.io.File

private const val IP_REGEX = """\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}"""
private const val URL_REGEX = """(?<=GET\s|POST\s|PUT\s|PATCH\s|DELETE\s)(.*)(?=\s+HTTP)"""

class LogFileReader : InputFileReader {
    override fun read(inputFile: File): List<Pair<String, String>> {
        val ipRegexPattern = IP_REGEX.toRegex()
        val urlRegexPattern = URL_REGEX.toRegex()

        return inputFile
            .useLines { line -> removeBlankLines(line).toList() }.map { line ->
                val ipAddress = ipRegexPattern.find(line)?.value ?: ""
                val url = urlRegexPattern.find(line)?.value ?: ""
                ipAddress to url
            }
            .filterNot { it.first.isBlank() || it.second.isBlank() }
    }

    private fun removeBlankLines(lines: Sequence<String>) = lines.filter { it.isNotEmpty() }
}