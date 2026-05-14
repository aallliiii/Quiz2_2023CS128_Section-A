package com.example.mad_test.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

private val INPUT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
    timeZone = TimeZone.getTimeZone("UTC")
}
private val OUTPUT = SimpleDateFormat("MMM dd, yyyy · HH:mm", Locale.getDefault())

fun formatPublishedAt(raw: String?): String {
    if (raw.isNullOrBlank()) return ""
    return try {
        val date = INPUT.parse(raw) ?: return raw
        OUTPUT.format(date)
    } catch (_: Exception) {
        raw
    }
}
