package ru.itis.homework.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.itis.homework.data.db.entity.UserEntity
import ru.itis.homework.data.db.dao.UserDao
import ru.itis.homework.data.db.dao.FilmDao
import ru.itis.homework.data.db.dao.LikeFilmDao
import ru.itis.homework.data.db.dao.RatingFilmDao
import ru.itis.homework.data.db.entity.FilmEntity
import ru.itis.homework.data.db.entity.LikedFilmEntity
import ru.itis.homework.data.db.entity.RatingFilmEntity


@Database(
    entities = [UserEntity::class , FilmEntity::class, RatingFilmEntity::class, LikedFilmEntity::class],
    version = 1
)

abstract class InceptionDatabase : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val filmDao: FilmDao
    abstract val ratingFilmDao: RatingFilmDao
    abstract val likedFilmDao: LikeFilmDao

}