package com.capstone.siapabisa.util

    fun checkUsername(username: String): Boolean {
        return username.length >= 1
    }

    fun checkEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun checkPassword(password: String): Boolean {
        return password.length >= 8
    }