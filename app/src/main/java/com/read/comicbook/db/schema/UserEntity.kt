package com.read.comicbook.db.schema

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users", indices = [Index(value = ["email"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0,
    val email: String,
    val name: String? = null,
    val password: String?=null,
    val phone: String? = null,
    val rated: String? = null,
    val fav: List<String>? = listOf(),
    val thumbnail: String? = null,
    val userDesc: String? = null
)