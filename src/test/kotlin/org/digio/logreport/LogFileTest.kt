package org.digio.logreport

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class LogFileTest {
    @Nested
    @DisplayName("number of unique ip addresses is")
    inner class UniqueIpAddresses {
        @Test
        internal fun `zero when log records are empty`() {
            assertThat(LogFile(emptyList()).uniqueIpAddresses()).isEqualTo(0)
        }

        @Test
        internal fun `zero when log record entry has blank ip address`() {
            assertThat(LogFile(listOf(LogRecord("", "/intra-analytics"))).uniqueIpAddresses()).isEqualTo(0)
        }

        @Test
        internal fun `one when there is just one log record entry`() {
            val logRecord = LogRecord("177.71.128.21", "/intra-analytics")
            assertThat(LogFile(listOf(logRecord)).uniqueIpAddresses()).isEqualTo(1)
        }

        @Test
        internal fun `one when the same log record entry is repeated multiple times`() {
            val logRecord1 = LogRecord("177.71.128.21", "/intra-analytics")
            val logRecord2 = LogRecord("177.71.128.21", "/intra-analytics")
            val logRecord3 = LogRecord("177.71.128.21", "/intra-analytics")
            assertThat(LogFile(listOf(logRecord1, logRecord2, logRecord3)).uniqueIpAddresses()).isEqualTo(1)
        }

        @Test
        internal fun `two when there are two unique log record entries`() {
            val logRecord1 = LogRecord("177.71.128.21", "/intra-analytics")
            val logRecord2 = LogRecord("177.71.128.22", "/intra-analytics")
            assertThat(LogFile(listOf(logRecord1, logRecord2)).uniqueIpAddresses()).isEqualTo(2)
        }

        @Test
        internal fun `two when there are two unique log record entries among multiple entries`() {
            val logRecord1 = LogRecord("177.71.128.21", "/intra-analytics")
            val logRecord2 = LogRecord("177.71.128.22", "/intra-analytics")
            val logRecord3 = LogRecord("177.71.128.21", "/intra-analytics")
            val logRecord4 = LogRecord("177.71.128.22", "/intra-analytics")
            assertThat(LogFile(listOf(logRecord1, logRecord2, logRecord3, logRecord4)).uniqueIpAddresses()).isEqualTo(2)
        }
    }
}