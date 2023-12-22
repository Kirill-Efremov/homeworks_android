package ru.itis.homework.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films_test")
data class FilmEntity(
    @PrimaryKey (autoGenerate = true) val id:  Int = 0,
    val title: String,
    val description: String,
    val year: Int,


)