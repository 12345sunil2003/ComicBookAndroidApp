package com.read.comicbook.db.schema

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val postId: Long = 0,
    val email: String?=null,
    val title: String?=null,
    val desc: String?=null,
    val thumbnail: String?=null,
    val imgList: List<String>?=null,
    val category: String?=null,
    val isPremium: Boolean?=null,
    val price: String?=null,
    val rating: String?=null,
    val createdDate: Long?=null
)