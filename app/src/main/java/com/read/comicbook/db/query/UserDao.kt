package com.read.comicbook.db.query

import androidx.room.*
import com.read.comicbook.db.schema.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    fun getUsers(email: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserById(userId: Long): UserEntity?

    @Insert
    fun insertUser(user: UserEntity)

    @Update
    fun updateUser(user: UserEntity)

    @Delete
    fun deleteUser(user: UserEntity)
}