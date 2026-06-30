package com.projeto.nutrilog.utils

// ponytail: expect declarations for native date retrieval and offset math to avoid datetime library dependencies.
expect fun getTodayDateString(): String
expect fun getOffsetDateString(dateString: String, offsetDays: Int): String
