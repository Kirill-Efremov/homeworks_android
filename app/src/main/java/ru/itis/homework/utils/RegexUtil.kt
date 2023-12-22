package ru.itis.homework.utils

object RegexUtil {
    private val emailRegex = Regex("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+")
    private val phoneRegex = Regex("^\\+7 \\(9\\d{2}\\)-\\d{3}-\\d{2}-\\d{2}\$")
    fun isValidEmail(email: String): Boolean {
        return emailRegex.matches(email)
    }

    fun isValidPhone(phone: String): Boolean {
        return phoneRegex.matches(phone)
    }
}