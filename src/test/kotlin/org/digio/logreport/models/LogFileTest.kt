package org.digio.logreport.models

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

    @Nested
    @DisplayName("top three most visited urls contains")
    inner class TopThreeMostVisitedUrls {
        @Test
        internal fun `an empty list when there are no log record entries`() {
            assertThat(LogFile(emptyList()).topThreeMostVisitedUrls()).isEqualTo(emptyList<String>())
        }

        @Test
        internal fun `an empty list when the log record entry has a blank url`() {
            val logRecord = LogRecord("177.71.128.21", "")

            assertThat(LogFile(listOf(logRecord)).topThreeMostVisitedUrls()).isEqualTo(emptyList<String>())
        }

        @Test
        internal fun `one url when there is just one log record entry`() {
            val logRecord = LogRecord("177.71.128.21", "/intra-analytics")

            assertThat(LogFile(listOf(logRecord)).topThreeMostVisitedUrls()).isEqualTo(listOf("/intra-analytics"))
        }

        @Test
        internal fun `one url when the same url is repeated multiple times`() {
            val logRecord1 = LogRecord("177.71.128.21", "/intra-analytics")
            val logRecord2 = LogRecord("177.71.128.23", "/intra-analytics")
            val logRecord3 = LogRecord("177.71.128.25", "/intra-analytics")

            assertThat(LogFile(listOf(logRecord1, logRecord2, logRecord3)).topThreeMostVisitedUrls())
                .isEqualTo(listOf("/intra-analytics"))
        }

        @Test
        internal fun `three urls when there are three distinct urls in log file`() {
            val logRecord1 = LogRecord("177.71.128.21", "/intra-analytics")
            val logRecord2 = LogRecord("177.71.128.22", "/intra-analytics-1")
            val logRecord3 = LogRecord("177.71.128.21", "/intra-analytics-2")

            assertThat(LogFile(listOf(logRecord1, logRecord2, logRecord3)).topThreeMostVisitedUrls())
                .isEqualTo(listOf("/intra-analytics", "/intra-analytics-1", "/intra-analytics-2"))
        }

        @Test
        internal fun `three urls in descending order of frequency when urls repeat in the log file`() {
            val logRecord1 = LogRecord("177.71.128.21", "/intra-analytics-1")
            val logRecord2 = LogRecord("177.71.128.22", "/intra-analytics")
            val logRecord3 = LogRecord("177.71.128.21", "/intra-analytics-1")
            val logRecord4 = LogRecord("177.71.128.21", "/intra-analytics-1")
            val logRecord5 = LogRecord("177.71.128.21", "/intra-analytics")
            val logRecord6 = LogRecord("177.71.128.21", "/intra-analytics-2")

            assertThat(
                LogFile(listOf(logRecord1, logRecord2, logRecord3, logRecord4, logRecord5, logRecord6))
                    .topThreeMostVisitedUrls()
            ).isEqualTo(listOf("/intra-analytics-1", "/intra-analytics", "/intra-analytics-2"))
        }

        @Test
        internal fun `three urls in the order of appearance in the log file when frequency is the same`() {
            val logRecord1 = LogRecord("177.71.128.21", "/intra-analytics-1")
            val logRecord2 = LogRecord("177.71.128.22", "/intra-analytics")
            val logRecord3 = LogRecord("177.71.128.21", "/intra-analytics-1")
            val logRecord4 = LogRecord("177.71.128.21", "/intra-analytics")
            val logRecord5 = LogRecord("177.71.128.21", "/intra-analytics-2")
            val logRecord6 = LogRecord("177.71.128.21", "/intra-analytics-3")

            assertThat(
                LogFile(listOf(logRecord1, logRecord2, logRecord3, logRecord4, logRecord5, logRecord6))
                    .topThreeMostVisitedUrls()
            )
                .isEqualTo(listOf("/intra-analytics-1", "/intra-analytics", "/intra-analytics-2"))
        }

        @Test
        internal fun `three urls in descending order of frequency when there are more than 3 distinct urls in log file`() {
            val logRecord1 = LogRecord("177.71.128.21", "/intra-analytics-1")
            val logRecord2 = LogRecord("177.71.128.22", "/intra-analytics")
            val logRecord3 = LogRecord("177.71.128.21", "/intra-analytics-1")
            val logRecord4 = LogRecord("177.71.128.24", "/intra-analytics")
            val logRecord5 = LogRecord("177.71.128.25", "/intra-analytics-1")
            val logRecord6 = LogRecord("177.71.128.26", "/intra-analytics-3")
            val logRecord7 = LogRecord("177.71.128.27", "/intra-analytics-2")
            val logRecord8 = LogRecord("177.71.128.27", "/intra-analytics-2")

            assertThat(
                LogFile(
                    listOf(
                        logRecord1, logRecord2, logRecord3, logRecord4,
                        logRecord5, logRecord6, logRecord7, logRecord8
                    )
                ).topThreeMostVisitedUrls()
            ).isEqualTo(listOf("/intra-analytics-1", "/intra-analytics", "/intra-analytics-2"))
        }
    }

    @Nested
    @DisplayName("top three most active IP addresses contains")
    inner class TopThreeMostActiveIPs {
        @Test
        internal fun `an empty list when there are no log record entries`() {
            assertThat(LogFile(emptyList()).topThreeMostActiveIPs()).isEqualTo(emptyList<String>())
        }

        @Test
        internal fun `an empty list when the log record entry has a blank ip`() {
            val logRecord = LogRecord("", "/intra-analytics")

            assertThat(LogFile(listOf(logRecord)).topThreeMostActiveIPs()).isEqualTo(emptyList<String>())
        }

        @Test
        internal fun `one ip when there is just one log record entry`() {
            val logRecord = LogRecord("177.71.128.21", "/intra-analytics")

            assertThat(LogFile(listOf(logRecord)).topThreeMostActiveIPs()).isEqualTo(listOf("177.71.128.21"))
        }

        @Test
        internal fun `one ip when the same ip is repeated multiple times`() {
            val logRecord1 = LogRecord("177.71.128.21", "/intra-analytics")
            val logRecord2 = LogRecord("177.71.128.21", "/intra-analytics-1")
            val logRecord3 = LogRecord("177.71.128.21", "/intra-analytics-2")

            assertThat(LogFile(listOf(logRecord1, logRecord2, logRecord3)).topThreeMostActiveIPs())
                .isEqualTo(listOf("177.71.128.21"))
        }

        @Test
        internal fun `three ips when there are three distinct ips in log file`() {
            val logRecord1 = LogRecord("177.71.128.21", "/intra-analytics")
            val logRecord2 = LogRecord("177.71.128.22", "/intra-analytics")
            val logRecord3 = LogRecord("177.71.128.23", "/intra-analytics-2")

            assertThat(LogFile(listOf(logRecord1, logRecord2, logRecord3)).topThreeMostActiveIPs())
                .isEqualTo(listOf("177.71.128.21", "177.71.128.22", "177.71.128.23"))
        }

        @Test
        internal fun `three ips in descending order of frequency when ips repeat in the log file`() {
            val logRecord1 = LogRecord("177.71.128.21", "/intra-analytics-1")
            val logRecord2 = LogRecord("177.71.128.22", "/intra-analytics-1")
            val logRecord3 = LogRecord("177.71.128.21", "/intra-analytics-1")
            val logRecord4 = LogRecord("177.71.128.21", "/intra-analytics-1")
            val logRecord5 = LogRecord("177.71.128.23", "/intra-analytics")
            val logRecord6 = LogRecord("177.71.128.23", "/intra-analytics-2")

            assertThat(
                LogFile(listOf(logRecord1, logRecord2, logRecord3, logRecord4, logRecord5, logRecord6))
                    .topThreeMostActiveIPs()
            ).isEqualTo(listOf("177.71.128.21", "177.71.128.23", "177.71.128.22"))
        }

        @Test
        internal fun `three ips in the order of appearance in the log file when frequency is the same`() {
            val logRecord1 = LogRecord("177.71.128.22", "/intra-analytics-1")
            val logRecord2 = LogRecord("177.71.128.21", "/intra-analytics")
            val logRecord3 = LogRecord("177.71.128.22", "/intra-analytics-1")
            val logRecord4 = LogRecord("177.71.128.21", "/intra-analytics")
            val logRecord5 = LogRecord("177.71.128.23", "/intra-analytics-2")
            val logRecord6 = LogRecord("177.71.128.24", "/intra-analytics-3")

            assertThat(
                LogFile(listOf(logRecord1, logRecord2, logRecord3, logRecord4, logRecord5, logRecord6))
                    .topThreeMostActiveIPs()
            )
                .isEqualTo(listOf("177.71.128.22", "177.71.128.21", "177.71.128.23"))
        }

        @Test
        internal fun `three ips in descending order of frequency when there are more than 3 distinct ips in log file`() {
            val logRecord1 = LogRecord("177.71.128.22", "/intra-analytics-1")
            val logRecord2 = LogRecord("177.71.128.21", "/intra-analytics")
            val logRecord3 = LogRecord("177.71.128.22", "/intra-analytics-1")
            val logRecord4 = LogRecord("177.71.128.22", "/intra-analytics")
            val logRecord5 = LogRecord("177.71.128.21", "/intra-analytics-2")
            val logRecord6 = LogRecord("177.71.128.23", "/intra-analytics-2")
            val logRecord7 = LogRecord("177.71.128.24", "/intra-analytics-3")
            val logRecord8 = LogRecord("177.71.128.24", "/intra-analytics-3")

            assertThat(
                LogFile(
                    listOf(
                        logRecord1, logRecord2, logRecord3, logRecord4, logRecord5, logRecord6, logRecord7, logRecord8
                    )
                ).topThreeMostActiveIPs()
            ).isEqualTo(listOf("177.71.128.22", "177.71.128.21", "177.71.128.24"))
        }
    }
}