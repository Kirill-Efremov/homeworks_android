package ru.itis.homework.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.homework.data.db.entity.FilmEntity
import ru.itis.homework.data.db.entity.RatingFilmEntity

@Dao
interface RatingFilmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRatingFilm(ratingFilmEntity: RatingFilmEntity)

    @Query("UPDATE  rating_film SET rating = :rating  WHERE film_id = :filmId AND user_id = :userId")
    fun updateRatingFilm(userId: Int, filmId: Int, rating: Double)

    @Query("SELECT AVG(rating) FROM rating_film WHERE film_id = :filmId")
    suspend fun getFilmRating(filmId: Int): Double?
    @Query("SELECT AVG(rating) FROM rating_film WHERE film_id = :filmId AND user_id = :userId")
    suspend fun getFilmRatingFromUser(filmId: Int,userId: Int): Double?



}