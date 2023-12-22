package ru.itis.homework.model

data class UserModel(
    val id: Int,
    val name: String,
    val phoneNumber: String,
    val emailAddress: String,
    val password: String,
    val deleteDate: Long

)
