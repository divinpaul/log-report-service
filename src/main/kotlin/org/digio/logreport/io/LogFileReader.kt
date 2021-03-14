package org.digio.logreport.io

import java.io.File

private const val IP_REGEX = """\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}"""
private const val URL_REGEX = """(?<=GET\s|POST\s|PUT\s|PATCH\s|DELETE\s)(.*)(?=\s+HTTP)"""

class LogFileReader : InputFileReader {
    override fun read(inputFile: File) =
        inputFile
            .useLines { line -> removeBlankLines(line).toList() }.map { line ->
                val ipAddress = IP_REGEX.toRegex().find(line)?.value ?: ""
                val url = URL_REGEX.toRegex().find(line)?.value ?: ""
                ipAddress to url
            }
            .filterNot { it.first.isBlank() || it.second.isBlank() }

    private fun removeBlankLines(lines: Sequence<String>) = lines.filter { it.isNotEmpty() }
}