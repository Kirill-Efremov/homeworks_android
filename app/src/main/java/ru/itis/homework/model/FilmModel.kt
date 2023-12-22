package ru.itis.homework.model

import android.media.Rating

data class FilmModel (
    val id: Int,
    val title: String,
    val description: String,
    val year: Int,

): ItemModel()

