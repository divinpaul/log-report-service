# log-report-service
Parses a log file containing HTTP requests and reports on its contents. The generated report includes:
```
Number of unique IP addresses
The top 3 most visited URLs
The top 3 most active IP addresses
```

### Run Tests
```
$ ./gradlew clean test
```

### Run Application
```
./gradlew run --args='<log file name>'
```
*e.g.*
```
./gradlew run --args='inputFile.log'
``` 
## Approach
* Identified LogFile and LogRecord as the domain models, with a LogFile having zero or more LogRecords.
* Realised that the responsibility to provide answers that are needed to generate the report could sit within a LogFile,
transforming LogFile into a **Rich Domain Model**.
* Once this was identified, it was all about **test-driving** LogFile, adding behavior and layers when required.
* Identified two components that can be expected to change. Followed the below approach to adhere to **Open-Closed Principle**.
    * LogFileReader: Reads from a log file in a fixed format as of now. Added InputFileReader abstraction to make it extensible
     for a new log format.
    * Output writer: Writes to console as of now. Added OutputWriter abstraction to make it extensible.
## Other considerations
* Performance
    * As Log files can be large, used Sequence instead of Iterable to process the records read from the log file. This will
    improve the performance of the chain for large collections by not building intermediate step results.
    * Used `File.useLines()` which reads a file line by line rather than loading the entire file into memory in one go which
    could take up a lot of memory for large files.
