package org.digio.logreport.io

import java.io.File


interface InputFileReader {
    fun read(inputFile: File): List<Pair<String, String>>
}