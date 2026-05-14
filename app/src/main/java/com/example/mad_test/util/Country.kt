package com.example.mad_test.util

enum class Country(val code: String, val displayName: String, val flag: String) {
    PAKISTAN("pk", "Pakistan", "🇵🇰"),
    UNITED_STATES("us", "United States", "🇺🇸"),
    UNITED_KINGDOM("gb", "United Kingdom", "🇬🇧"),
    INDIA("in", "India", "🇮🇳"),
    SAUDI_ARABIA("sa", "Saudi Arabia", "🇸🇦"),
    UAE("ae", "United Arab Emirates", "🇦🇪");

    val label: String get() = "$flag  $displayName"

    companion object {
        val DEFAULT = UNITED_STATES
    }
}
