import org.digio.logreport.LogReportService
import org.digio.logreport.io.ConsoleWriter
import org.digio.logreport.io.LogFileReader
import java.io.File

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Please provide the input file name. Refer README for details.")
        return
    }

    LogReportService(LogFileReader(), ConsoleWriter()).generate(File(args[0]))
}