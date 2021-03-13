package org.digio.logreport.io

class ConsoleWriter : OutputWriter {
    override fun write(output: String) = println(output)
}