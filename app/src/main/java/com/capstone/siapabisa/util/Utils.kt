package com.capstone.siapabisa.util

import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())

    fun formatDate(dateString: String): String {
        val input = ZonedDateTime.parse(dateString)
        val dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val output = dateTimeFormatter.format(input)
        return output
}

    fun checkUsername(username: String): Boolean {
        return username.length >= 1
    }

    fun checkEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun checkPassword(password: String): Boolean {
        return password.length >= 8
    }