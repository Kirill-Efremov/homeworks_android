package ru.itis.homework.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.homework.data.db.entity.FilmEntity
import ru.itis.homework.data.db.entity.UserEntity

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFilm(filmData: FilmEntity)

    @Query("DELETE FROM films_test WHERE id = :id")
    fun deleteFilm(id: Int)

    @Query("SELECT * FROM films_test ORDER BY year DESC")
    fun getAllFilms(): List<FilmEntity>?

    @Query("SELECT * FROM films_test WHERE id = :filmId")
    fun getFilmInfoById(filmId: Int): FilmEntity

    @Query("SELECT * FROM films_test WHERE year = :year AND title = :title "  )
    fun getFilmByYearAndTitle(year: Int, title:String): FilmEntity?

    @Query("SELECT * FROM films_test WHERE id IN (:filmIds)")
    suspend fun getFilmsByIds(filmIds: List<Int>): List<FilmEntity>










//
//    @Query("UPDATE user_test SET email = :email WHERE id = :userId")
//    fun updateEmail(userId: Int, email: String)
//
//    @Query("UPDATE user_test SET phone_number = :newPhone WHERE id = :userId")
//    fun updatePhone(userId: Int, newPhone: String)
//
//    @Query("UPDATE user_test SET password = :newPassword WHERE id = :userId")
//    fun updatePassword(userId: Int, newPassword: String)
//
//
//    @Query("SELECT * FROM user_test WHERE id = :userId")
//    fun getUserInfoById(userId: Int): UserEntity?
//
//    @Query("SELECT * FROM user_test WHERE email=:email")
}