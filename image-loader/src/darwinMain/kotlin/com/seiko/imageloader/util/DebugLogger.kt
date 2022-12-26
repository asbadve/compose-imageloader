package com.seiko.imageloader.util

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter

actual class DebugLogger actual constructor() : Logger {

    var crashAssert = false

    private val dateFormatter = NSDateFormatter().apply {
        dateFormat = "MM-dd HH:mm:ss.SSS"
    }

    private val tagMap: HashMap<LogPriority, String> = hashMapOf(
        LogPriority.VERBOSE to "💜 VERBOSE",
        LogPriority.DEBUG to "💚 DEBUG",
        LogPriority.INFO to "💙 INFO",
        LogPriority.WARN to "💛 WARN",
        LogPriority.ERROR to "❤️ ERROR",
        LogPriority.ASSERT to "💞 ASSERT"
    )

    override fun log(priority: LogPriority, tag: String, data: Any?, throwable: Throwable?, message: String) {
        val fullMessage = buildString {
            if (data != null) {
                append("data:")
                append(data.parseString())
                append('\n')
            }
            append(message)
        }
        if (priority == LogPriority.ASSERT) {
            assert(crashAssert) { buildLog(priority, tag, throwable, fullMessage) }
        } else {
            println(buildLog(priority, tag, throwable, fullMessage))
        }
    }

    private fun buildLog(priority: LogPriority, tag: String, throwable: Throwable?, message: String): String {
        val baseLogString = "${getCurrentTime()} ${tagMap[priority]} $tag - $message"
        return if (throwable != null) {
            "$baseLogString\n${throwable.stackTraceToString()}"
        } else {
            baseLogString
        }
    }

    private fun getCurrentTime() = dateFormatter.stringFromDate(NSDate())
}
