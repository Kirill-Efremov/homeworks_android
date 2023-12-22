package ru.itis.homework.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.homework.data.db.entity.UserEntity
import java.util.Date


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(userData: UserEntity)

    @Query("DELETE FROM user_test WHERE id = :id")
    fun deleteUserByIdQuery(id: Int)

    @Query("UPDATE user_test SET deletion_date = :deletionDate WHERE id = :userId")
    suspend fun updateDeleteDate(userId: Int, deletionDate: Long)

    @Query("SELECT * FROM user_test")
    fun getAllUsers(): List<UserEntity>?

    @Query("SELECT * FROM user_test WHERE email=:email AND password=:password")
    suspend fun getUser(email: String, password: String): UserEntity?

    @Query("SELECT * FROM user_test WHERE id = :userId")
    fun getUserInfoById(userId: Int): UserEntity?

    @Query("SELECT * FROM user_test WHERE email=:email")
    fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM user_test WHERE phone_number=:phone")
    fun getUserByPhone(phone: String): UserEntity?

    @Query("UPDATE user_test SET deletion_date = :currentTimeMillis WHERE id = :userId")
    fun setDeletionDate(userId: Int, currentTimeMillis: Long)

    @Query("UPDATE user_test SET email = :email WHERE id = :userId")
    fun updateEmail(userId: Int, email: String)

    @Query("UPDATE user_test SET phone_number = :newPhone WHERE id = :userId")
    fun updatePhone(userId: Int, newPhone: String)

    @Query("UPDATE user_test SET password = :newPassword WHERE id = :userId")
    fun updatePassword(userId: Int, newPassword: String)


}