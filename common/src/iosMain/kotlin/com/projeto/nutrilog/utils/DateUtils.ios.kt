package com.projeto.nutrilog.utils

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter

actual fun getTodayDateString(): String {
    val formatter = NSDateFormatter().apply {
        dateFormat = "yyyy-MM-dd"
    }
    return formatter.stringFromDate(NSDate())
}
