package ru.itis.homework.utils


object PasswordEncoder {
    fun  matches( password : String, hashPassword: String): Boolean{
        val encodedPassword = encoder(password)
        return encodedPassword == hashPassword
    }

    fun encoder(password: String): String {
        var hash: Long = 0
        for (element in password) {
            hash = (hash * 31) + element.toLong()
        }
        return hash.toString(16)
    }
}