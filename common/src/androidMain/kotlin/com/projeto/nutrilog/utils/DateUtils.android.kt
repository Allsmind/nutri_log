package com.projeto.nutrilog.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

actual fun getTodayDateString(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return sdf.format(Date())
}

actual fun getOffsetDateString(dateString: String, offsetDays: Int): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val date = sdf.parse(dateString) ?: Date()
    val cal = Calendar.getInstance().apply {
        time = date
        add(Calendar.DAY_OF_YEAR, offsetDays)
    }
    return sdf.format(cal.time)
}
