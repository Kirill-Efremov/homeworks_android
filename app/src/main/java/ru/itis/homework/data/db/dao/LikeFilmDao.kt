package ru.itis.homework.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.homework.data.db.entity.FilmEntity
import ru.itis.homework.data.db.entity.LikedFilmEntity
import ru.itis.homework.data.db.entity.RatingFilmEntity


@Dao
interface LikeFilmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLikedFilm(likedFilmEntity: LikedFilmEntity)

    @Query("DELETE  FROM liked_film  WHERE film_id = :filmId AND user_id = :userId")
    fun deleteLikedFilm(userId: Int, filmId: Int)

    @Query("SELECT film_id FROM liked_film WHERE  user_id = :userId")
    suspend fun getLikedFilmsByUser(userId: Int): List<Int>?

    @Query("SELECT film_id FROM liked_film WHERE  film_id = :filmId")
    suspend fun getLikedFilmsByFilm(filmId: Int): Int?


    @Query("SELECT * FROM liked_film WHERE  film_id = :filmId  AND user_id = :userId  ")
    fun getLikedFilmsByFilmUser(filmId: Int, userId: Int): LikedFilmEntity?


}

