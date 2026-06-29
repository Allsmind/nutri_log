package com.projeto.nutrilog.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun getTodayDateString(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return sdf.format(Date())
}
