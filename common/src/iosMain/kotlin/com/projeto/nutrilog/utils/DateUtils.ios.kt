package com.projeto.nutrilog.utils

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay

actual fun getTodayDateString(): String {
    val formatter = NSDateFormatter().apply {
        dateFormat = "yyyy-MM-dd"
    }
    return formatter.stringFromDate(NSDate())
}

actual fun getOffsetDateString(dateString: String, offsetDays: Int): String {
    val formatter = NSDateFormatter().apply {
        dateFormat = "yyyy-MM-dd"
    }
    val date = formatter.dateFromString(dateString) ?: NSDate()
    val calendar = NSCalendar.currentCalendar
    val newDate = calendar.dateByAddingUnit(
        NSCalendarUnitDay,
        offsetDays.toLong(),
        date,
        options = 0L
    ) ?: date
    return formatter.stringFromDate(newDate)
}
